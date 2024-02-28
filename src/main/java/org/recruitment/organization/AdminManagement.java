package org.recruitment.organization;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.util.CommonLogger;
import org.util.ConnectionClass;
import org.util.Constants;
import org.util.Gender;

public class AdminManagement {
	
	Logger logger = CommonLogger.getCommon().getLogger(AdminManagement.class);

	public String addPanelist(String name, String email, Gender gender, String position, int departmentId, int orgId) throws SQLException {
		
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		
		PreparedStatement checkPreparedStatement = connection.prepareStatement(Constants.isPanelistExists);
		checkPreparedStatement.setString(1, email);
		checkPreparedStatement.setInt(2, orgId);
		ResultSet panelists = checkPreparedStatement.executeQuery();
		if (panelists.next()) {
			return "Panelist already exists";
		}
		
		PreparedStatement preparedStatement = connection.prepareStatement(Constants.addPanelist);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, email);
		preparedStatement.setString(3, gender.getValue());
		preparedStatement.setInt(4, departmentId);
		preparedStatement.setInt(5, orgId);
		preparedStatement.setString(6, position);
        int affectedRows = preparedStatement.executeUpdate();
		
		if(affectedRows > 0) {
			logger.info("Panelist added");
			return "Panelist added";
		}
		logger.info("Failed to add panelist");
		return "Failed to add panelist";
		
	}
	
	public boolean removePanelist(int panelistId) throws SQLException {
		
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement(Constants.removePanelist);
		preparedStatement.setInt(1, panelistId);
		int affectedRows = preparedStatement.executeUpdate();
		logger.info("Panelist removed");
		return affectedRows > 0;
		
	}
	
	public JSONObject getPanelists(int orgId) throws SQLException {
	    
		ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    
	    PreparedStatement preparedStatement = connection.prepareStatement(Constants.getPanelist);
	    preparedStatement.setInt(1, orgId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    JSONObject jsonObject = new JSONObject();
	    
	    while (resultSet.next()) {
	        
	    	int id = resultSet.getInt("Panelist_Id");
	        JSONArray detailsArray = new JSONArray();
	        detailsArray.put(id);
	        detailsArray.put(resultSet.getString("Name"));
	        detailsArray.put(resultSet.getString("Email"));
	        detailsArray.put(resultSet.getString("Gender"));
	        detailsArray.put(resultSet.getString("Position"));
	        detailsArray.put(resultSet.getString("Title"));
	       
			try {
				jsonObject.put("panelist_" + id, detailsArray);
			} 
			catch (JSONException e) {
				e.printStackTrace();
			}
	    }
	    
	    return jsonObject;
	}
	
	public JSONObject getPanelistsWithDepartment(int orgId, int departmentId) throws SQLException {
	   
		ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    
	    PreparedStatement preparedStatement = connection.prepareStatement(Constants.getPanelistWithDepartment);
	    preparedStatement.setInt(1, orgId);
	    preparedStatement.setInt(2, departmentId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    JSONObject jsonObject = new JSONObject();
	    
	    while (resultSet.next()) {
	        
	    	int id = resultSet.getInt("Panelist_Id");
	    	JSONArray detailsArray = new JSONArray();
	        detailsArray.put(id);
	        detailsArray.put(resultSet.getString("Name"));
	        detailsArray.put(resultSet.getString("Email"));
	        detailsArray.put(resultSet.getString("Gender"));
	        detailsArray.put(resultSet.getString("Position"));
	        detailsArray.put(resultSet.getString("Title"));
	       
	        try {
				jsonObject.put("panelist_" + id, detailsArray);
			} 
	        catch (JSONException e) {
				e.printStackTrace();
			}
	    }
	    
	    return jsonObject;
	}
	
	public JSONObject getPanelistWithName(int panelistId) throws SQLException {
	   
		ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	   
	    PreparedStatement preparedStatement = connection.prepareStatement(Constants.getPanelistWithName);
	    preparedStatement.setInt(1, panelistId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    JSONObject jsonObject = new JSONObject();
	    
	    while (resultSet.next()) {
	        
	    	int id = resultSet.getInt("Panelist_Id");
	    	JSONArray detailsArray = new JSONArray();
	        detailsArray.put(id);
	        detailsArray.put(resultSet.getString("Name"));
	        detailsArray.put(resultSet.getString("Email"));
	        detailsArray.put(resultSet.getString("Gender"));
	        detailsArray.put(resultSet.getString("Position"));
	        detailsArray.put(resultSet.getString("Title"));
	       
	        try {
				jsonObject.put("panelist_" + id, detailsArray);
			} 
	        catch (JSONException e) {
				e.printStackTrace();
			}
	    }
	    
	    return jsonObject;
	}
	
	public boolean editPanelist(int panelistId, JSONObject panelistDetails) throws JSONException, SQLException {
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();	
		
		String name = panelistDetails.getString("name");
		String email = panelistDetails.getString("email");
		String position = panelistDetails.getString("position");
				
		PreparedStatement preparedStatement = connection.prepareStatement(Constants.editPanelist);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, email);
		preparedStatement.setString(3, position);
		preparedStatement.setInt(4, panelistId);
		int result = preparedStatement.executeUpdate();
		logger.info("Panelist details was updated");
		return result > 0;
	}

	public String addDepartment(String title, String description, int orgId) throws SQLException {
		
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		
		PreparedStatement checkPreparedStatement = connection.prepareStatement(Constants.isDepartmentExists);
		checkPreparedStatement.setString(1, title);
		checkPreparedStatement.setInt(2, orgId);
		ResultSet departments = checkPreparedStatement.executeQuery();
		if (departments.next()) {
			return "Department already exists";
		}
		
		PreparedStatement preparedStatement = connection.prepareStatement(Constants.addDepartment);
		preparedStatement.setString(1, title);
		preparedStatement.setInt(2, orgId);
		preparedStatement.setString(3, description);
        int affectedRows = preparedStatement.executeUpdate();
		
		if(affectedRows > 0) {
			logger.info("Department added");
			return "Department added";
		}
		logger.info("Failed to add department");
		return "Failed to add department";
		
	}
	
	public boolean removeDepartments(int departmentId) throws SQLException {
		
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement(Constants.removeDepartment);
		preparedStatement.setInt(1, departmentId);
		int affectedRows = preparedStatement.executeUpdate();
		logger.info("Department removed");
		return affectedRows > 0;
		
	}
	
	public JSONObject getDepartments(int orgId) throws SQLException {
	    ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    
	    PreparedStatement preparedStatement = connection.prepareStatement(Constants.getDepartments);
	    preparedStatement.setInt(1, orgId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    JSONObject jsonObject = new JSONObject();
	    
	    while (resultSet.next()) {
	        
	    	int id = resultSet.getInt("Department_Id");
	    	JSONArray detailsArray = new JSONArray();
	        detailsArray.put(id);
	        detailsArray.put(resultSet.getString("Title"));
	        detailsArray.put(resultSet.getString("Description"));
	        
	        try {
				jsonObject.put("department_" + id, detailsArray);
			} 
	        catch (JSONException e) {
				e.printStackTrace();
			}
	    }
	    
	    return jsonObject;
	}
	
	public JSONObject getOpenings(int orgId) throws SQLException {
		ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    
	    PreparedStatement preparedStatement = connection.prepareStatement(Constants.getOpenings);
	    preparedStatement.setInt(1, orgId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    JSONObject jsonObject = new JSONObject();
	    
	    while (resultSet.next()) {
	        
	    	int id = resultSet.getInt("Opening_Id");
	    	JSONArray detailsArray = new JSONArray();
	        detailsArray.put(id);
	        detailsArray.put(resultSet.getInt("Panelist.Panelist_Id"));
	        detailsArray.put(resultSet.getString("Description"));
	        detailsArray.put(resultSet.getInt("Experience"));
	        detailsArray.put(resultSet.getString("Qualification"));
	        detailsArray.put(resultSet.getString("Departments"));
	        detailsArray.put(resultSet.getString("EmploymentType"));
	        detailsArray.put(resultSet.getString("SalaryRange"));
	        detailsArray.put(resultSet.getString("Panelist.Name"));
	        detailsArray.put(resultSet.getString("Start_Date"));
	        detailsArray.put(resultSet.getString("End_Date"));
	        
	        
	        try {
				jsonObject.put("opening_" + id, detailsArray);
			} 
	        catch (JSONException e) {
				e.printStackTrace();
			}
	    }
	    
	    return jsonObject;
	}
	
	public JSONObject getOpeningsWithDepartment(int departmentId) throws SQLException {
		ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    
	    PreparedStatement preparedStatement = connection.prepareStatement(Constants.getOpeningsWithDepartment);
	    preparedStatement.setInt(1, departmentId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    JSONObject jsonObject = new JSONObject();
	    
	    while (resultSet.next()) {
	        
	    	int id = resultSet.getInt("Opening_Id");
	    	JSONArray detailsArray = new JSONArray();
	        detailsArray.put(id);
	        detailsArray.put(resultSet.getInt("Panelist.Panelist_Id"));
	        detailsArray.put(resultSet.getString("Description"));
	        detailsArray.put(resultSet.getInt("Experience"));
	        detailsArray.put(resultSet.getString("Qualification"));
	        detailsArray.put(resultSet.getString("Departments"));
	        detailsArray.put(resultSet.getString("EmploymentType"));
	        detailsArray.put(resultSet.getString("SalaryRange"));
	        detailsArray.put(resultSet.getString("Panelist.Name"));
	        detailsArray.put(resultSet.getString("Start_Date"));
	        detailsArray.put(resultSet.getString("End_Date"));
	        
	        
	        try {
				jsonObject.put("opening_" + id, detailsArray);
			} 
	        catch (JSONException e) {
				e.printStackTrace();
			}
	    }
	    
	    return jsonObject;
	}
	
	public JSONObject getCurrentOpenings(int orgId) throws SQLException {
		ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    LocalDate today = LocalDate.now();
		Date sqlDate = Date.valueOf(today);
	    
	    PreparedStatement preparedStatement = connection.prepareStatement(Constants.getCurrentOpenings);
	    preparedStatement.setInt(1, orgId);
	    preparedStatement.setDate(2, sqlDate);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    JSONObject jsonObject = new JSONObject();
	    
	    while (resultSet.next()) {
	        
	    	int id = resultSet.getInt("Opening_Id");
	    	JSONArray detailsArray = new JSONArray();
	        detailsArray.put(id);
	        detailsArray.put(resultSet.getInt("Panelist.Panelist_Id"));
	        detailsArray.put(resultSet.getString("Description"));
	        detailsArray.put(resultSet.getInt("Experience"));
	        detailsArray.put(resultSet.getString("Qualification"));
	        detailsArray.put(resultSet.getString("Departments"));
	        detailsArray.put(resultSet.getString("EmploymentType"));
	        detailsArray.put(resultSet.getString("SalaryRange"));
	        detailsArray.put(resultSet.getString("Panelist.Name"));
	        detailsArray.put(resultSet.getString("Start_Date"));
	        detailsArray.put(resultSet.getString("End_Date"));
	        
	        
	        try {
				jsonObject.put("opening_" + id, detailsArray);
			} 
	        catch (JSONException e) {
				e.printStackTrace();
			}
	    }
	    
	    return jsonObject;
	}
	
	public JSONObject getOpeningGraphWithDepartments(int org_id) throws SQLException, JSONException {
	    ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    JSONObject json = new JSONObject();
	    
	    PreparedStatement preparedStatement = connection.prepareStatement(Constants.getOpeningGraphWithDepartments);
	    preparedStatement.setInt(1, org_id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while(resultSet.next()) {
	        json.put(resultSet.getString("DepartmentTitle"), resultSet.getInt("Count"));
	    }
	    return json;
	}
	
	public JSONObject getOpeningsGraphByMonth(int org_id) throws SQLException, JSONException {
	    ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    JSONObject json = new JSONObject();
	   
	    PreparedStatement preparedStatement = connection.prepareStatement(Constants.getOpeningGraphByMonth);
	    preparedStatement.setInt(1, org_id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while(resultSet.next()) {
	        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(Calendar.MONTH, resultSet.getInt("Month") - 1);
	        String monthName = sdf.format(calendar.getTime());
	        
	        json.put(monthName, resultSet.getInt("Count"));
	    }
	    return json;
	}

	public JSONObject getApplicantsStatusGraph(int org_Id) throws SQLException, JSONException {
		
	    ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    JSONObject json = new JSONObject();
	    
	    PreparedStatement preparedStatement = connection.prepareStatement(Constants.getApplicantsStatusGraph);
	    preparedStatement.setInt(1, org_Id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while(resultSet.next()) {
	        json.put(resultSet.getString("Status"), resultSet.getInt("count"));
	    }
	    
	    return json;
	}
	
	public JSONObject selectedApplicantsGraphInDepartments(int orgId) throws SQLException, JSONException {
	    ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    JSONObject json = new JSONObject();
	    
	    PreparedStatement preparedStatement = connection.prepareStatement(Constants.selectedApplicantsGraphInDepartments);
	    preparedStatement.setInt(1, orgId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while(resultSet.next()) {
	        json.put(resultSet.getString("Departments.Title"), resultSet.getInt("count(*)"));
	    }
	    
	    return json;
	}
	
	public JSONObject selectedApplicantsGraphInMonth(int orgId) throws SQLException, JSONException {
		
		 ConnectionClass db = ConnectionClass.CreateCon();
		 Connection connection = db.getConnection();
		 JSONObject json = new JSONObject();
		    
		 PreparedStatement preparedStatement = connection.prepareStatement(Constants.selectedApplicantsGraphInMonth);
	     preparedStatement.setInt(1, orgId);
	     ResultSet resultSet = preparedStatement.executeQuery();
		    
	     while(resultSet.next()) {
	    	 SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		     Calendar calendar = Calendar.getInstance();
	         calendar.set(Calendar.MONTH, resultSet.getInt("month") - 1);
	         String monthName = sdf.format(calendar.getTime());
		        
   	         json.put(monthName, resultSet.getInt("count(*)"));
		 }
		    return json;
	}
	
	
	public JSONObject seeApplicants(int openingId, String Status) throws SQLException {
		
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		JSONObject json = new JSONObject();
		
		PreparedStatement preparedStatement = connection.prepareStatement(Constants.getApplicants);
		preparedStatement.setString(1, Status);
		preparedStatement.setInt(2, openingId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {

			int id = resultSet.getInt("Job_Seeker_Id");
	    	JSONArray detailsArray = new JSONArray();
	        detailsArray.put(id);
	        detailsArray.put(resultSet.getString("Name"));
	        detailsArray.put(resultSet.getString("Email"));
	        detailsArray.put(resultSet.getDate("DOB"));
	        detailsArray.put(resultSet.getString("Gender"));
	        detailsArray.put(resultSet.getInt("Experience"));
	        detailsArray.put(resultSet.getString("Phone"));
	        detailsArray.put(resultSet.getString("Qualification")); 
	        detailsArray.put(resultSet.getString("Photo"));
	        try {
				json.put("applicant_" + id, detailsArray);
			} 
	        catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return json;	
	}
	
}
