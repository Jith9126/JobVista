package org.servlet.admin.organization;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.recruitment.organization.AdminManagement;
import org.servlet.admin.opening.GetCurrentOpenings;
import org.util.CommonLogger;

/**
 * Servlet implementation class AdminDashBoard
 */
@WebServlet("/AdminDashBoard")
public class AdminDashBoard extends HttpServlet {
	
	Logger logger = CommonLogger.getCommon().getLogger(GetCurrentOpenings.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDashBoard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		JSONObject responseData = new JSONObject();
		AdminManagement adminManagement = new AdminManagement();
		Cookie[] cookies = request.getCookies();
		String orgId = "1";
		String adminId = null;
        if (cookies != null) {
            
        	for(Cookie cookie:cookies) {
        		if(cookie.getName().equalsIgnoreCase("org_Id")) {
        			orgId = cookie.getValue();
            	}
        		if(cookie.getName().equalsIgnoreCase("admin_Id")) {
        			adminId = cookie.getValue();
        		}
        	}
        }
        
        try {
        
        	JSONArray departments = adminManagement.getDepartments(Integer.parseInt(orgId));
        	JSONObject jsonResponse = new JSONObject();
        	JSONArray departmentArray = new JSONArray();

        	for (int i = 0; i < departments.length(); i++) {
        	    JSONObject department = departments.getJSONObject(i);
        	    int departmentId = department.getInt("id");
        	    JSONArray openings = adminManagement.getOpeningsWithDepartment(departmentId);
        	    JSONObject departmentObject = new JSONObject(department.toString());
        	    JSONArray openingArray = new JSONArray();
        	    
        	    for (int j = 0; j < openings.length(); j++) {
        	        JSONObject opening = openings.getJSONObject(j);
        	        int openingId = opening.getInt("id");
        	        JSONArray applicants = adminManagement.seeApplicants(openingId, "Selected");
        	        JSONObject openingObject = new JSONObject(opening.toString());
        	        openingObject.put("applicants", applicants);
        	        openingArray.put(openingObject);
        	    }
        	    
        	    departmentObject.put("openings", openingArray);
        	    departmentArray.put(departmentObject);
        	}

        	jsonResponse.put("departments", departmentArray);
        	responseData.put("statusCode", 200);
        	responseData.put("message", jsonResponse);

		
        }
        
        catch (JSONException e) {
	        
	    	logger.error("Admin:"+adminId+"\nError parsing JSON object.\n" + e.getMessage());
	    	try {
				responseData.put("statusCode", 500);
				responseData.put("message", "Error parsing JSON object.\n");
			} 
	    	catch (JSONException e1) {
	    		logger.error("Admin:"+adminId+"\nError parsing JSON object." + e1.getMessage());
			}
	    	
	    } 
	    catch (SQLException e) {
	      
	    	try {
				responseData.put("statusCode", 500);
				responseData.put("message", "Error occurred while retrieving data from the database.");
			} 
	    	catch (JSONException e1) {
	    		logger.error("Admin: "+adminId+"\nError parsing JSON object.\n" + e1.getMessage());
			}
	    	logger.error("Admin:"+adminId+"\nError occurred while retrieving data from the database. \n"+e.getMessage());
	        
	    }
	    
	    response.getWriter().write(responseData.toString());
	        
	}
	

}
