package org.servlet.admin.opening;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.recruitment.dao.OpeningDetailsDAO;
import org.recruitment.organization.AdminManagement;
import org.util.CommonLogger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class getOpeningsWithDepartment
 */
@WebServlet("/GetOpenings")
public class GetOpenings extends HttpServlet {

	Logger logger = CommonLogger.getCommon().getLogger(GetOpenings.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetOpenings() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setStatus(HttpServletResponse.SC_OK);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");

		JSONObject responseData = new JSONObject();
		AdminManagement adminManagement = new AdminManagement();
		OpeningDetailsDAO OpeningDetailsDAO = new OpeningDetailsDAO();
		Cookie[] cookies = request.getCookies();
		String orgId = "1";
		String adminId = null;
		int ORGID = Integer.parseInt(orgId);

		if (cookies != null) {

//			for (Cookie cookie : cookies) {
//				if (cookie.getName().equalsIgnoreCase("org_Id")) {
//					orgId = cookie.getValue();
//				}
//				if (cookie.getName().equalsIgnoreCase("admin_Id")) {
//					adminId = cookie.getValue();
//				}
//			}
		}

		List<JSONObject> openingDetailsList = OpeningDetailsDAO.getAllOpeningsForAdmin(ORGID);
		JSONObject jsonResponse = new JSONObject();
        try {
			jsonResponse.put("Status", "Success");
			jsonResponse.put("Value", new JSONArray(openingDetailsList));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        response.setContentType("application/json");

        // Write JSON response
        response.getWriter().print(jsonResponse.toString());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}

//package org.servlet.admin.opening;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.recruitment.dao.OpeningDetailsDAO;
//import org.recruitment.organization.AdminManagement;
//import org.util.CommonLogger;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
///**
// * Servlet implementation class getOpeningsWithDepartment
// */
//@WebServlet("/GetOpenings")
//public class GetOpenings extends HttpServlet {
//
//	Logger logger = CommonLogger.getCommon().getLogger(GetOpenings.class);
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * @see HttpServlet#HttpServlet()
//	 */
//	public GetOpenings() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
//		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//		response.setStatus(HttpServletResponse.SC_OK);
//	}
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "GET");
//		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//
//		JSONObject responseData = new JSONObject();
//		AdminManagement adminManagement = new AdminManagement();
//		OpeningDetailsDAO OpeningDetailsDAO = new OpeningDetailsDAO();
//		Cookie[] cookies = request.getCookies();
//		String orgId = "1";
//		String adminId = null;
//		int ORGID = Integer.parseInt(orgId);
//
//		if (cookies != null) {
//
////			for (Cookie cookie : cookies) {
////				if (cookie.getName().equalsIgnoreCase("org_Id")) {
////					orgId = cookie.getValue();
////				}
////				if (cookie.getName().equalsIgnoreCase("admin_Id")) {
////					adminId = cookie.getValue();
////				}
////			}
//		}
//		try {
//			
//			List<JSONObject> openingDetailsList = OpeningDetailsDAO.getAllOpeningsForAdmin(ORGID);
//			System.out.println("1");
//			JSONArray openings = adminManagement.getOpenings(Integer.parseInt(orgId));
//			System.out.println("2");
//			JSONArray openingArray = new JSONArray();
//			System.out.println("3");
//			for (int j = 0; j < openings.length(); j++) {
//				System.out.println("4");
//				JSONObject opening = openings.getJSONObject(j);
//				int openingId = opening.getInt("id");
//				System.out.println("5");
//				JSONArray applicants = adminManagement.seeApplicants(openingId, "Selected");
//				System.out.println("6");
//				JSONObject openingObject = new JSONObject(opening.toString());
//				System.out.println("7");
//				openingObject.put("applicants", applicants);
//				System.out.println("8");
//				openingArray.put(openingObject);
//				System.out.println("9");
//			}
//			System.out.println("10");
//			responseData.put("statusCode", 200);
//			System.out.println("11");
//			responseData.put("message", openingArray);
//			System.out.println("12");
//
//		} catch (JSONException e) {
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//
//			logger.error("Admin:" + adminId + "\nError parsing JSON object.\n" + e.getMessage());
//			try {
//				responseData.put("statusCode", 500);
//				responseData.put("message", "Error parsing JSON object.\n");
//			} catch (JSONException e1) {
//				logger.error("Admin:" + adminId + "\nError parsing JSON object." + e1.getMessage());
//			}
//
//		} catch (SQLException e) {
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//
//			try {
//				responseData.put("statusCode", 500);
//				responseData.put("message", "Error occurred while retrieving data from the database.");
//			} catch (JSONException e1) {
//				logger.error("Admin: " + adminId + "\nError parsing JSON object.\n" + e1.getMessage());
//			}
//			logger.error("Admin:" + adminId + "\nError occurred while retrieving data from the database. \n"
//					+ e.getMessage());
//		}
//
//		response.getWriter().write(responseData.toString());
//
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//	}
//
//}
