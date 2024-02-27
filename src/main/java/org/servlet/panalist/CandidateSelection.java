package org.servlet.panalist;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CandidateSelection
 */
public class CandidateSelection extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CandidateSelection() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		
		Logger logger = Logger.getLogger(CreateOpening.class);
		PropertyConfigurator.configure("/home/ajith-zstk355/.logs/jobvista.properties");
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
            JSONObject testJson  = jsonData.getJSONObject("");
            
            
            
            
            
        }catch (Exception e) {
        	
        }
		
	}

}
