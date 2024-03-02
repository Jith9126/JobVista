package org.servlet.admin.department;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.recruitment.organization.AdminManagement;
import org.util.CommonLogger;

/**
 * Servlet implementation class AddDepartment
 */
@WebServlet("/AddDepartment")
public class AddDepartment extends HttpServlet {
	
	Logger logger = CommonLogger.getCommon().getLogger(AddDepartment.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDepartment() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		
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
	       
	        StringBuilder sb = new StringBuilder();
	        BufferedReader reader = request.getReader();
	        String line;
	        
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	        
	        JSONObject jsonObject = new JSONObject(sb.toString());
	        String title = jsonObject.getString("title");
	        String description = jsonObject.getString("description");
	        responseData.put("statusCode", 200);
	        responseData.put("message",adminManagement.addDepartment(title, description, Integer.parseInt(orgId)));
	        
	    } 
	    
	    catch (JSONException e) {
<<<<<<< HEAD
	        
	    	logger.error("Admin:"+adminId+"\nError parsing JSON object.\n" + e.getMessage());
=======
	    	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    	logger.error("User:"+adminId+"\nError parsing JSON object.\n" + e.getMessage());
>>>>>>> 14f5cfc (today Commit)
	    	try {
				responseData.put("statusCode", 500);
				responseData.put("message", "Error parsing JSON object.\n");
			} 
	    	catch (JSONException e1) {
	    		logger.error("Admin:"+adminId+"\nError parsing JSON object." + e1.getMessage());
			}
	        
	    } 
	    catch (SQLException e) {
	    	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    	try {
				responseData.put("statusCode", 500);
				responseData.put("message", "Error occurred while retrieving data from the database.");
			} 
	    	catch (JSONException e1) {
<<<<<<< HEAD
	    		logger.error("Admin: "+adminId+"\nError parsing JSON object.\n" + e1.getMessage());
=======
	    		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    		logger.error("User: "+adminId+"\nError parsing JSON object.\n" + e1.getMessage());
>>>>>>> 14f5cfc (today Commit)
			}
	    	logger.error("Admin:"+adminId+"\nError occurred while adding data in the database. \n"+e.getMessage());
	        
	    }

	    response.getWriter().write(responseData.toString());
	
	}

}
