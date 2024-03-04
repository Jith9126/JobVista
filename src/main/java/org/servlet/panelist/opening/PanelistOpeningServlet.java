package org.servlet.panelist.opening;

import java.io.BufferedReader;
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
		int PanelistID = 1;
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

		List<JSONObject> openingDetailsList = OpeningDetailsDAO.getOpeningsForPanelist(PanelistID);
		JSONObject jsonResponse = new JSONObject();
        try {
			jsonResponse.put("Status", "Success");
			jsonResponse.put("Value", new JSONArray(openingDetailsList));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
        
        response.setContentType("application/json");

        // Write JSON response
        response.getWriter().print(jsonResponse.toString());

	}
	
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        BufferedReader reader = request.getReader();
//        StringBuilder jsonStringBuilder = new StringBuilder();
//        String line;
//
//        while ((line = reader.readLine()) != null) {
//            jsonStringBuilder.append(line);
//        }
//
//        String json = jsonStringBuilder.toString();
//
//        try {
//            JSONObject requestJson = new JSONObject(json);
//
//            // Extracting panelist details
//            JSONObject panelistJson = requestJson.getJSONObject("panelist");
//            String panelistName = panelistJson.getString("name");
//            String panelistEmail = panelistJson.getString("email");
//            String panelistGender = panelistJson.getString("gender");
//            String panelistPosition = panelistJson.getString("position");
//            String panelistOrganization = panelistJson.getString("organization");
//            String panelistDepartment = panelistJson.getString("department");
//
//            // Extracting openings details
//            JSONArray openingsJsonArray = requestJson.getJSONArray("openings");
//            List<JSONObject> openingsList = new ArrayList<>();
//
//            for (int i = 0; i < openingsJsonArray.length(); i++) {
//                JSONObject openingJson = openingsJsonArray.getJSONObject(i);
//
//                // Extracting opening details
//                long openingId = openingJson.getLong("openingId");
//                String title = openingJson.getString("title");
//                int experience = openingJson.getInt("experience");
//                String qualification = openingJson.getString("qualification");
//                String employmentType = openingJson.getString("EmploymentType");
//                String salaryRange = openingJson.getString("SalaryRange");
//
//                // Extracting test details
//                JSONArray testsJsonArray = openingJson.getJSONArray("test");
//                List<JSONObject> testsList = new ArrayList<>();
//
//                for (int j = 0; j < testsJsonArray.length(); j++) {
//                    JSONObject testJson = testsJsonArray.getJSONObject(j);
//
//                    // Extracting test details
//                    String testTitle = testJson.getString("title");
//                    String testDate = testJson.getString("Date");
//                    int testDuration = testJson.getInt("Duration");
//                    String typeOfTest = testJson.getString("typeOfTest");
//                    int roundOn = testJson.getInt("roundOn");
//
//                    // Extracting applicants details
//                    JSONArray applicantsJsonArray = testJson.getJSONArray("applicants");
//                    List<JSONObject> applicantsList = new ArrayList<>();
//
//                    for (int k = 0; k < applicantsJsonArray.length(); k++) {
//                        JSONObject applicantJson = applicantsJsonArray.getJSONObject(k);
//
//                        // Extracting applicant details
//                        String applicantName = applicantJson.getString("name");
//                        String applicantEmail = applicantJson.getString("email");
//                        // ... (extract other applicant details as needed)
//
//                        // Construct the applicant JSON
//                        JSONObject applicantDetails = new JSONObject();
//                        applicantDetails.put("name", applicantName);
//                        applicantDetails.put("email", applicantEmail);
//                        // ... (add other applicant details to the JSON)
//
//                        applicantsList.add(applicantDetails);
//                    }
//
//                    // Construct the test JSON
//                    JSONObject testDetails = new JSONObject();
//                    testDetails.put("title", testTitle);
//                    testDetails.put("Date", testDate);
//                    testDetails.put("Duration", testDuration);
//                    testDetails.put("typeOfTest", typeOfTest);
//                    testDetails.put("roundOn", roundOn);
//                    testDetails.put("applicants", applicantsList);
//
//                    testsList.add(testDetails);
//                }
//
//                // Construct the opening JSON
//                JSONObject openingDetails = new JSONObject();
//                openingDetails.put("openingId", openingId);
//                openingDetails.put("title", title);
//                openingDetails.put("experience", experience);
//                openingDetails.put("qualification", qualification);
//                openingDetails.put("EmploymentType", employmentType);
//                openingDetails.put("SalaryRange", salaryRange);
//                openingDetails.put("test", testsList);
//
//                openingsList.add(openingDetails);
//            }
//
//            // Construct the final JSON response
//            JSONObject responseJson = new JSONObject();
//            responseJson.put("panelist", panelistJson);
//            responseJson.put("openings", openingsList);
//
//            // Return the JSON response
//            response.getWriter().write(responseJson.toString());
//
//        } catch (JSONException e) {
//            // Handle JSON parsing errors
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("Error parsing JSON: " + e.getMessage());
//        }
//    }
}
