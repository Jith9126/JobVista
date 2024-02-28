package org.servlet.panalist;

import org.json.JSONException;
import org.json.JSONObject;
import org.recruitment.users.InterviewerService;
import org.util.ConnectionClass;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class InterviewerSetMarksServlet extends HttpServlet {

    private final InterviewerService interviewerService = new InterviewerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonResponse = new JSONObject();
        resp.setContentType("application/json");

        int jobSeekerId = Integer.parseInt(req.getParameter("jobSeekerId"));
        int testId = Integer.parseInt(req.getParameter("testId"));
        int marks = Integer.parseInt(req.getParameter("marks"));
        String status = req.getParameter("status");

        try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
            interviewerService.setMarks(jobSeekerId, testId, marks, status);
            jsonResponse.put("status", "success");
            jsonResponse.put("message", "Marks and status set successfully.");
        } catch (SQLException e) {
            try {
				jsonResponse.put("status", "error");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
            try {
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

