package org.servlet.panalist;

import java.awt.Panel;
import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.recruitment.jobs.Openings;
import org.recruitment.users.Panelist;
import org.recruitment.users.PanelistManager;
import org.util.CommonLogger;
import org.util.Gender;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateOpening
 */
public class CreateOpening extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CreateOpening() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		
		Logger logger = CommonLogger.getCommon().getLogger(CreateOpening.class);
		JSONObject responseJson = new JSONObject();
		StringBuilder jsonLoad = new StringBuilder();
        try {
            logger.info("Starting to read JSON payload from request.");

            BufferedReader br = request.getReader();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                jsonLoad.append(line);
            }
            JSONObject jsonData = new JSONObject(jsonLoad.toString());
            logger.info("JSON payload successfully parsed.");

            JSONObject panelistJson = jsonData.getJSONObject("panelist");
            JSONObject openingJson = jsonData.getJSONObject("opening");

            Panelist panelist = new Panelist(
                panelistJson.getString("name"),
                panelistJson.getString("email"),
//                Gender.valueOf(panelistJson.getString("gender")),
                Gender.valueOf("FEMALE"),
                panelistJson.getString("position"),
                panelistJson.getString("organistion"), // Fixed typo: organistion -> organization
                panelistJson.getString("department")
            );

            Openings opening = new Openings(
                openingJson.getString("title"),
                openingJson.getInt("experience"),
                openingJson.getString("qualification"),
                openingJson.getString("departments"),
                openingJson.getString("type"),
                openingJson.getString("salaryRange")
            );

            logger.info("Creating panelist and opening objects.");

            int OpeningId = PanelistManager.getPanelistManger().addOpening(panelist, opening);

            logger.info("Opening added successfully.");
            
            
            responseJson.put("status",200);
            responseJson.put("openingId",OpeningId);
            
            
            response.getWriter().write(responseJson.toString());

        } catch (Exception e) {
            logger.error(e.getMessage());
            try {
            	responseJson.put("status",500);
                responseJson.put("error",e.getMessage());
            	
                response.getWriter().write(responseJson.toString());
            } catch (Exception ioException) {
                logger.error(e.getMessage());
            }
        }

		
		
		
	}

}