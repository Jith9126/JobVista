package org.servlet.panelist;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.recruitment.users.InterviewerService;
import org.util.ConnectionClass;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/InterviewerSetMarksServlet")
public class InterviewerSetMarksServlet extends HttpServlet {

	private final InterviewerService interviewerService = new InterviewerService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject jsonResponse = new JSONObject();
		resp.setContentType("application/json");

		// Read the JSON payload from the request body
		BufferedReader reader = req.getReader();
		StringBuilder jsonPayload = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			jsonPayload.append(line);
		}

		// Parse the JSON payload
		JSONObject jsonRequest;
		int jobSeekerId = 0;
		int testId = 0;
		int marks = 0;
		String status = null;
		try {
			jsonRequest = new JSONObject(jsonPayload.toString());
			// Extract values from the JSON object
			jobSeekerId = jsonRequest.getInt("jobSeekerId");
			testId = jsonRequest.getInt("testId");
			marks = jsonRequest.getInt("marks");
			status = jsonRequest.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
			if(jobSeekerId != 0 || testId != 0) {
				if(status.equals("Selected") || status.equals("Rejected") ||status.equals("Onhold") ) {
					interviewerService.setMarks(jobSeekerId, testId, marks, status);
					jsonResponse.put("status", "success");
					jsonResponse.put("message", "Marks and status set successfully.");
				}
				else {
					jsonResponse.put("status", "error");
					jsonResponse.put("message", "Error: " +"Need valid status" );
				}
			}
			else {
				jsonResponse.put("status", "error");
				jsonResponse.put("message", "Error: " +"Needed valid Details" );
			}
		} catch (SQLException e) {
			try {
				jsonResponse.put("status", "error");
				jsonResponse.put("message", "Error setting marks and status: " + e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		resp.getWriter().write(jsonResponse.toString());
	}
}
