package org.servlet.admin.panelist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.util.CommonLogger;
import org.recruitment.organization.AdminManagement;

@WebServlet("/GetPanelistWithDepartment")
public class GetPanelistWithDepartment extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    Logger logger = CommonLogger.getCommon().getLogger(GetPanelistWithDepartment.class);
    
    public GetPanelistWithDepartment() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
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
        
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        
        try {
        	
            JSONObject jsonObject = new JSONObject(sb.toString());
            int departmentId = jsonObject.getInt("departmentId");
            JSONArray panelistsData = adminManagement.getPanelistsWithDepartment(Integer.parseInt(orgId), departmentId);
            responseData.put("statusCode", 200);
            responseData.put("message", panelistsData.toString());
            
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
