package org.servlet.panelist.opening;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.recruitment.dao.OpeningDetailsDAO;
import org.recruitment.organization.AdminManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PanelistOpeningServlet")
public class PanelistOpeningServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		int PanelistID = 2;
		int OrgID = 1;

//		if (cookies != null) {
//
//			for (Cookie cookie : cookies) {
//				if (cookie.getName().equalsIgnoreCase("org_Id")) {
//					OrgID = Integer.parseInt(cookie.getValue());
//				}
//				if (cookie.getName().equalsIgnoreCase("admin_Id")) {
//					PanelistID = Integer.parseInt(cookie.getValue());
//				}
//			}
//		}
		List<JSONObject> openingDetailsList = new ArrayList<>();
		openingDetailsList = OpeningDetailsDAO.getOpeningsForPanelist(PanelistID,openingDetailsList);
		
		List<JSONObject> currentOpeningDetailsList = new ArrayList<>();
		currentOpeningDetailsList = OpeningDetailsDAO.getCurrentOpeningsForPanelist(PanelistID,currentOpeningDetailsList);
		
		List<JSONObject> upComingOpeningDetailsList = new ArrayList<>();
		upComingOpeningDetailsList = OpeningDetailsDAO.getUpComingOpeningsForPanelist(PanelistID,upComingOpeningDetailsList);
		
		JSONObject jsonResponse = new JSONObject();
		JSONObject jsonResponseForPanelist = new JSONObject();

		try {
		    jsonResponseForPanelist.put("openings", openingDetailsList);
		    jsonResponseForPanelist.put("currentOpenings", currentOpeningDetailsList);
		    jsonResponseForPanelist.put("upComingOpenings", upComingOpeningDetailsList);

		    jsonResponse.put("Status", "Success");
		    jsonResponse.put("Value", jsonResponseForPanelist);

		} catch (JSONException e) {
		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    e.printStackTrace();
		}

		response.setContentType("application/json");

		// Write JSON response
		response.getWriter().print(jsonResponse.toString());


//		JSONObject jsonResponse = new JSONObject();
//		JSONObject jsonResponseforPanelist = new JSONObject();
//		try {
//			jsonResponseforPanelist.put("openings", openingDetailsList);
//			jsonResponseforPanelist.put("currentOpenings", currentOpeningDetailsList);
//			jsonResponse.put("Status", "Success");
//			jsonResponse.put("Value", jsonResponseforPanelist);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			e.printStackTrace();
//		}
//
//		response.setContentType("application/json");
//
//		// Write JSON response
//		response.getWriter().print(jsonResponse.toString());

	}
}