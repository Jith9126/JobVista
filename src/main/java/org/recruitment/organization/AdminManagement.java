package org.recruitment.organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.util.ConnectionClass;
import org.util.Gender;

public class AdminManagement {

	public String addPanelist(String name, String email, Gender gender, String position, int departmentId, int orgId) throws SQLException {
		
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		
		String checkQuery = "select * from Panelist where Name = ? and Email = ? and Org_Id = ?";
		PreparedStatement checkPreparedStatement = connection.prepareStatement(checkQuery);
		checkPreparedStatement.setString(1, name);
		checkPreparedStatement.setString(2, email);
		checkPreparedStatement.setInt(3, orgId);
		ResultSet panelists = checkPreparedStatement.executeQuery();
		if (panelists.next()) {
			return "Panelist already exists";
		}
		
		String query = "insert into Panelist (Name, Email, Gender, Department_Id, Org_Id, Position) values (?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, email);
		preparedStatement.setString(3, gender.getValue());
		preparedStatement.setInt(4, departmentId);
		preparedStatement.setInt(5, orgId);
		preparedStatement.setString(6, position);
        int affectedRows = preparedStatement.executeUpdate();
		
		if(affectedRows > 0) {
			return "Panelist added";
		}
		return "Failed to add panelist";
		
	}
	
	public boolean removePanelist(int panelistId) throws SQLException {
		
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		
		String query = "delete from Panelist where Panelist_Id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, panelistId);
		int affectedRows = preparedStatement.executeUpdate();
		
		return affectedRows > 0;
		
	}
	
	public JSONObject getPanelists(int orgId) throws SQLException {
		
		
//	    DBConnection db = DBConnection.getDB();
		ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    
	    String query = "select Panelist.*, Departments.Title from Panelist join Departments on Departments.Department_Id = Panelist.Department_Id where Org_Id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println(e.getMessage());
			}
	    }
	    
	    return jsonObject;
	}
	
	public JSONObject getPanelistsWithDepartment(int orgId, int departmentId) throws SQLException {
//	    DBConnection db = DBConnection.getDB();
		ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    
	    String query = "select Panelist.*, Departments.Title from Panelist join Departments on Departments.Department_Id = Panelist.Department_Id where Org_Id = ? and Department_Id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setInt(1, orgId);
	    preparedStatement.setInt(1, departmentId);
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println(e.getMessage());
			}
	    }
	    
	    return jsonObject;
	}
	
	public JSONObject getPanelistWithName(int panelistId) throws SQLException {
//	    DBConnection db = DBConnection.getDB();
		ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    
	    String query = "select Panelist.*, Departments.Title from Panelist join Departments on Departments.Department_Id = Panelist.Department_Id where Panelist_Id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    return jsonObject;
	}
	
	public String addDepartment(String title, String description, int orgId) throws SQLException {
		
//		DBConnection db = DBConnection.getDB();
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		
		String checkQuery = "select * from Departments where  title = ? and Org_Id = ?";
		PreparedStatement checkPreparedStatement = connection.prepareStatement(checkQuery);
		checkPreparedStatement.setString(1, title);
		checkPreparedStatement.setInt(2, orgId);
		ResultSet departments = checkPreparedStatement.executeQuery();
		if (departments.next()) {
			return "Department already exists";
		}
		
		String query = "insert into Departments (Title, Org_Id, Description) values (?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, title);
		preparedStatement.setInt(2, orgId);
		preparedStatement.setString(3, description);
        int affectedRows = preparedStatement.executeUpdate();
		
		if(affectedRows > 0) {
			return "Department added";
		}
		return "Failed to add department";
		
	}
	
	public boolean removeDepartments(int departmentId) throws SQLException {
		
//		DBConnection db = DBConnection.getDB();
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		
		String query = "delete from department where Department_Id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, departmentId);
		int affectedRows = preparedStatement.executeUpdate();
		
		return affectedRows > 0;
		
	}
	
	public JSONObject getDepartments(int orgId) throws SQLException {
//	    DBConnection db = DBConnection.getDB();
		ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    
	    String query = "select * from Departments where Org_Id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    return jsonObject;
	}
	
	public JSONObject getOpenings(int departmentId) throws SQLException {
//		DBConnection db = DBConnection.getDB();
		ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();
	    
	    String query = "select Openings.*, Panelist.Name from Openings join Panelist on Panelist.Panelist_Id = Openings.Panelist_Id where Department_Id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setInt(1, departmentId);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    JSONObject jsonObject = new JSONObject();
	    
	    while (resultSet.next()) {
	        
	    	int id = resultSet.getInt("Opening_Id");
	        JSONArray detailsArray = new JSONArray();
	        detailsArray.put(id);
	        detailsArray.put(resultSet.getString("Description"));
	        detailsArray.put(resultSet.getInt("Experience"));
	        detailsArray.put(resultSet.getString("Qualification"));
	        detailsArray.put(resultSet.getString("Departments"));
	        detailsArray.put(resultSet.getString("EmploymentType"));
	        detailsArray.put(resultSet.getString("SalaryRange"));
	        
	        
	        try {
				jsonObject.put("opening_" + id, detailsArray);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println(e.getMessage());
			}
	    }
	    
	    return jsonObject;
	}
	
}