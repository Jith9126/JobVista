package org.servlet.panalist;

import org.json.JSONException;
import org.json.JSONObject;
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

        int panelistId = Integer.parseInt(req.getParameter("panelistId"));
        int jobSeekerId = Integer.parseInt(req.getParameter("jobSeekerId"));
        int marks = Integer.parseInt(req.getParameter("marks"));

        try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
            interviewerService.setMarks(panelistId, jobSeekerId, marks);
            jsonResponse.put("status", "success");
            jsonResponse.put("message", "Marks set successfully.");
        } catch (SQLException e) {
            try {
				jsonResponse.put("status", "error");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
				jsonResponse.put("message", "Error setting marks: " + e.getMessage());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        resp.getWriter().write(jsonResponse.toString());
    }
}
