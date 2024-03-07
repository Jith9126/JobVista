package org.servlet.panelist.opening;

import java.io.BufferedReader;
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
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;

		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		int orgId = 0;
		int panelistId = 0;
		//You need to give a JSON;
		//Which have Org_Id and Panelist_Id;
		try {
			JSONObject jsonObject = new JSONObject(sb.toString());
			JSONObject userDetails = jsonObject.getJSONObject("userDetails");
			orgId = userDetails.getInt("Org_Id");
			panelistId = userDetails.getInt(" ");
		}
		catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		if (orgId == 0 || panelistId == 0) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		} else {
			List<JSONObject> openingDetailsList = new ArrayList<>();
			openingDetailsList = OpeningDetailsDAO.getOpeningsForPanelist(panelistId, openingDetailsList);
			System.out.println(openingDetailsList.toString());

			List<JSONObject> currentOpeningDetailsList = new ArrayList<>();
			currentOpeningDetailsList = OpeningDetailsDAO.getCurrentOpeningsForPanelist(panelistId,currentOpeningDetailsList);
			System.out.println(currentOpeningDetailsList.toString());

			List<JSONObject> upComingOpeningDetailsList = new ArrayList<>();
			upComingOpeningDetailsList = OpeningDetailsDAO.getUpComingOpeningsForPanelist(panelistId,upComingOpeningDetailsList);
			System.out.println(upComingOpeningDetailsList.toString());

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
		}
	}
}