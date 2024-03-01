package org.servlet.Applicant;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.recruitment.dao.ApplicantDAO;
import org.recruitment.dao.ApplicantDAOImpl;
import org.recruitment.dto.ApplicantDTO;
import org.recruitment.dto.SkillDTO;
import org.recruitment.dto.SocialMediaDTO;
import org.util.Gender;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddApplicantServlet
 */
@WebServlet("/AddApplicantServlet")
public class AddApplicantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        Logger logger = new CommonLogger(AddApplicantServlet.class).getLogger();

        BufferedReader reader = request.getReader();
        StringBuilder jsonStringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonStringBuilder.append(line);
        }

        String json = jsonStringBuilder.toString();

        try {
            JSONObject jsonObject = new JSONObject(json);
            
            // Extracting values from JSON
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email");
            String dobString = jsonObject.getString("dob");
            Date dob = Date.valueOf(dobString); // Assuming the date is provided as a string in "yyyy-MM-dd" format
            Gender gender = Gender.valueOf(jsonObject.getString("gender")); // Assuming Gender is an Enum
            int experience = jsonObject.getInt("experience");
            int departmentId = jsonObject.getInt("departmentId");
            String phoneNo = jsonObject.getString("phone");
            String qualification = jsonObject.getString("qualification");
//            String photo = jsonObject.getString("photo");

            // Extracting sources (assuming it's an array of JSON objects with "link" and "platform" properties)
            JSONArray sourcesArray = jsonObject.getJSONArray("sources");
            List<SocialMediaDTO> sources = new ArrayList<>();
            for (int i = 0; i < sourcesArray.length(); i++) {
                JSONObject sourceObject = sourcesArray.getJSONObject(i);
                String link = sourceObject.getString("link");
                String platform = sourceObject.getString("platform");
                sources.add(new SocialMediaDTO(link, platform));
            }

            // Extracting skills (assuming it's an array of JSON objects with "skill" and "description" properties)
            JSONArray skillsArray = jsonObject.getJSONArray("skills");
            List<SkillDTO> skills = new ArrayList<>();
            for (int i = 0; i < skillsArray.length(); i++) {
                JSONObject skillObject = skillsArray.getJSONObject(i);
                String skill = skillObject.getString("skill");
                String description = skillObject.getString("description");
                skills.add(new SkillDTO(skill, description));
            }

            ApplicantDTO applicantDTO = new ApplicantDTO(name, email, dob, gender, experience, departmentId, phoneNo,
                    qualification, sources, skills);
            
            // Assuming you have an ApplicantDAO instance
            ApplicantDAO applicantDAO = new ApplicantDAOImpl();
            boolean success = applicantDAO.addApplicant(applicantDTO);

            JSONObject responseJson = new JSONObject();

            if (success) {
                // Forward the request to ApplyForJobServlet after successfully adding the applicant
                RequestDispatcher dispatcher = request.getRequestDispatcher("/ApplyForJobServlet");
                dispatcher.forward(request, response);
            } else {
                responseJson.put("status", "error");
                responseJson.put("message", "Failed to add applicant.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(responseJson.toString());
            }
        } catch (JSONException e) {
            JSONObject responseJson = new JSONObject();
            try {
				responseJson.put("status", "error");
				responseJson.put("message", "Error parsing JSON: " + e.getMessage());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(responseJson.toString());
        }
    }
}
