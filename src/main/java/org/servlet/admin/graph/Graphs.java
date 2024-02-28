package org.servlet.admin.graph;

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
 * Servlet implementation class Graphs
 */
@WebServlet("/Graphs")
public class Graphs extends HttpServlet {
	
	Logger logger = CommonLogger.getCommon().getLogger(Graphs.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Graphs() {
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
	        
	        JSONObject openingGraphWithDepartments = adminManagement.getOpeningGraphWithDepartments(Integer.parseInt(orgId));
	        JSONObject openingsGraphByMonth = adminManagement.getOpeningsGraphByMonth(Integer.parseInt(orgId));
	        JSONObject applicantsStatusGraph = adminManagement.getApplicantsStatusGraph(Integer.parseInt(orgId));
	        JSONObject selectedApplicantsGraphInDepartments = adminManagement.selectedApplicantsGraphInDepartments(Integer.parseInt(orgId));
	        JSONObject selectedApplicantsGraphInMonth = adminManagement.selectedApplicantsGraphInMonth(Integer.parseInt(orgId));
	        
	        responseData.put("statusCode", 200);
	        responseData.put("openingGraphWithDepartments", openingGraphWithDepartments);
	        responseData.put("openingsGraphByMonth", openingsGraphByMonth);
	        responseData.put("applicantsStatusGraph", applicantsStatusGraph);
	        responseData.put("selectedApplicantsGraphInDepartments", selectedApplicantsGraphInDepartments);
	        responseData.put("selectedApplicantsGraphInMonth", selectedApplicantsGraphInMonth);
	        
	        
	    } 
	    catch (JSONException e) {
	        
	    	logger.error("User:"+adminId+"\nError parsing JSON object.\n" + e.getMessage());
	    	try {
				responseData.put("statusCode", 500);
				responseData.put("message", "Error parsing JSON object.\n");
			} 
	    	catch (JSONException e1) {
	    		logger.error("User:"+adminId+"\nError parsing JSON object." + e1.getMessage());
			}
	        
	    } 
	    catch (SQLException e) {
	      
	    	try {
				responseData.put("statusCode", 500);
				responseData.put("message", "Error occurred while retrieving data from the database.");
			} 
	    	catch (JSONException e1) {
	    		logger.error("User: "+adminId+"\nError parsing JSON object.\n" + e1.getMessage());
			}
	    	logger.error("User:"+adminId+"\nError occurred while retrieving data from the database. \n"+e.getMessage());
	        
	    }
	    response.getWriter().write(responseData.toString());
	
	}

}
