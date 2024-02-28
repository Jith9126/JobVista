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
        	
        	//Applicants
        	JSONObject hiredApplicants = adminManagement.seeApplicants(0,"Selected");
        	JSONObject onHoldApplicants = adminManagement.seeApplicants(0,"Onhold");
        	JSONObject rejectedApplicants = adminManagement.seeApplicants(0,"Rejected");
        	
        	//Openings
        	JSONObject currentOpenings = adminManagement.getCurrentOpenings(Integer.parseInt(orgId));
        	JSONObject openings = adminManagement.getOpenings(Integer.parseInt(orgId));
        	
        	//Panelists
        	JSONObject panelist = adminManagement.getPanelistsWithDepartment(Integer.parseInt(orgId), 0);
        	
        	//Departments
        	JSONObject departments = adminManagement.getDepartments(Integer.parseInt(orgId));
        	
        	JSONObject combinedJSON = new JSONObject();

        	JSONArray panelistsArray = new JSONArray();
        	panelistsArray.put(panelist);
        	
        	JSONArray departmentsArray = new JSONArray();
        	departmentsArray.put(departments);

        	JSONArray currentOpeningsArray = new JSONArray();
        	currentOpeningsArray.put(currentOpenings);
        	JSONArray allOpeningsArray = new JSONArray();
        	allOpeningsArray.put(openings);

        	JSONArray hiredApplicantsArray = new JSONArray();
        	hiredApplicantsArray.put(hiredApplicants);
        	JSONArray onHoldApplicantsArray = new JSONArray();
        	onHoldApplicantsArray.put(onHoldApplicants);
        	JSONArray rejectedApplicantsArray = new JSONArray();
        	rejectedApplicantsArray.put(rejectedApplicants);

        	JSONObject organizationJSON = new JSONObject();
        	organizationJSON.put("organization", departmentsArray);
        	organizationJSON.put("panelist", panelistsArray);
        	organizationJSON.put("openings", allOpeningsArray);

        	JSONObject applicantsJSON = new JSONObject();
        	applicantsJSON.put("hire", hiredApplicantsArray);
        	applicantsJSON.put("hold", onHoldApplicantsArray);
        	applicantsJSON.put("rejected", rejectedApplicantsArray);

        	combinedJSON.put("departments", organizationJSON);
        	combinedJSON.put("applicants", applicantsJSON);
       	
        	responseData.put("statusCode", 200);
			responseData.put("message", combinedJSON.toString());
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
