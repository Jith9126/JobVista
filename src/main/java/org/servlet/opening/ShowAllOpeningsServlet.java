package org.servlet.opening;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.recruitment.dao.OpeningDetailsDAO;
import org.recruitment.dto.OpeningDetailsDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/ShowAllOpeningsServlet")
public class ShowAllOpeningsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OpeningDetailsDAO openingDetailsDAO = new OpeningDetailsDAO();
        List<OpeningDetailsDTO> openingDetailsList = openingDetailsDAO.getAllOpenings();

        JSONArray openingsArray = new JSONArray();

        for (OpeningDetailsDTO openingDetails : openingDetailsList) {
            JSONObject openingJson = new JSONObject();
            try {
				openingJson.put("openingId", openingDetails.getOpeningId());
	            openingJson.put("experience", openingDetails.getExperience());
	            openingJson.put("qualification", openingDetails.getQualification());
	            openingJson.put("departments", openingDetails.getDepartments());
	            openingJson.put("type", openingDetails.getEmploymentType());
	            openingJson.put("salaryRange", openingDetails.getSalaryRange());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            openingsArray.put(openingJson);
        }

        // Set response content type
        response.setContentType("application/json");

        // Write JSON response
        response.getWriter().print(openingsArray.toString());
    }
}
