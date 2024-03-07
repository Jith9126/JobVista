package org.servlet.login;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.recruitment.login.AdminOrPanelistCheck;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/CheckCookieAfterLogin")
public class CheckCookieAfterLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	


	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
		// TODO Auto-generated method stub
		
        AdminOrPanelistCheck adminOrPanelistCheck = new AdminOrPanelistCheck();
		JSONObject responseData = new JSONObject();
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;

		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		int orgId = 0;
		int panelistId = 0;
		int adminId = 0;
		String role = null;
		//You need to give a JSON;
		//Which have Org_Id and Panelist_Id;
		try {
			JSONObject jsonObject = new JSONObject(sb.toString());
			JSONObject userDetails = jsonObject.getJSONObject("userDetails");
			
			orgId = userDetails.getInt("Org_Id");
			role = userDetails.getString("role");
			if(role.equals("Admin")) {
				adminId = userDetails.getInt("Admin_Id");
                if (adminOrPanelistCheck.isAdminIdValid(adminId)) {
                	responseData.put("status", "Success");
                    responseData.put("message", "Authentication Success");
                    response.getWriter().write(responseData.toString());
                    response.setStatus(HttpServletResponse.SC_OK);
                    
                } else {
                    responseData.put("message", "Invalid Admin ID");
                    responseData.put("status", "error");
                    response.getWriter().write(responseData.toString());
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
				
			}
			else if(role.equals("Panelist")) {
				panelistId = userDetails.getInt("Panelist_Id");	
                if (adminOrPanelistCheck.isPanelistIdValid(panelistId)) {
                	responseData.put("status", "Success");
                    responseData.put("message", "Authentication Success");
                    response.getWriter().write(responseData.toString());
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    responseData.put("message", "Invalid Panelist ID");
                    responseData.put("status", "error");
                    response.getWriter().write(responseData.toString());
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
			}
		}
		catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
	}

}
