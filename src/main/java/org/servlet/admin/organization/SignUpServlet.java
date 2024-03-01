package org.servlet.admin.organization;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.recruitment.organization.SignUp;
import org.util.CommonLogger;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	Logger logger = CommonLogger.getCommon().getLogger(SignUpServlet.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
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
	 * @sefile:///home/ragavi-zstk352/Downloads/jakarta.servlet-api-6.1.0-M1.jar
e HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		JSONObject jsonObject = (JSONObject) request.getAttribute("object");
		
		String orgName = null;
		String orgType = null;
		String industry = null;
		String contactEmail = null;
		String contactNumber = null;
		String adminName = null;
		String adminEmail = null;
		String adminPassword = null;
		JSONObject organization = null;
		JSONObject admin = null;
		
		try {
			
			organization = jsonObject.getJSONObject("organization");
			admin = jsonObject.getJSONObject("admin");
			
			orgName = organization.getString("orgName");
			orgType = organization.getString("orgType");
			industry = organization.getString("industry");
			contactEmail = organization.getString("contactEmail");
			contactNumber = organization.getString("contactNumber");
			adminName = admin.getString("adminName");
			adminEmail = admin.getString("adminEmail");
			adminPassword = admin.getString("adminPassword");
		
		} 
		
		catch (JSONException e) {
		
			logger.error("json exception in signup servlet while getting data from json object");
			e.printStackTrace();
		
		}
		
		SignUp signUp = new SignUp();
		String output = null;
		JSONObject responseObject = new JSONObject();
		
		try {
			output = signUp.addOrganisation(orgName, orgType, industry, contactEmail, contactNumber, adminName, adminEmail,adminPassword);
			
			if(output.equals("Admin successfully added")) {
				responseObject.put("statusCode", 200);
				responseObject.put("message", output);
			}
			else {
				responseObject.put("statusCode", 500);
				responseObject.put("message", output);
			}
		
		}
		
		catch (JSONException e) {
	        
	    	logger.error("Error parsing JSON object. "+e.getMessage());
	    	try {
				responseObject.put("statusCode", 500);
				responseObject.put("message", "Error parsing JSON object.");
			} 
	    	catch (JSONException e1) {
	    		logger.error("Error parsing JSON object. "+e1.getMessage());
			}
	        
	    } 
	    catch (SQLException e) {
	      
	    	try {
	    		responseObject.put("statusCode", 500);
	    		responseObject.put("message", "Error occurred while retrieving data from the database.");
			} 
	    	catch (JSONException e1) {
	    		logger.error("Error parsing JSON object. "+e1.getMessage());
			}
	    	logger.error("Error occurred while retrieving data from the database. "+e.getMessage());
	       
	    }
	    
	    
	    response.getWriter().write(responseObject.toString());
		
	}

}
