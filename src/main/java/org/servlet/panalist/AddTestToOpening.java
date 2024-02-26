package org.servlet.panalist;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONException;
import org.json.JSONObject;
import org.recruitment.tests.Test;
import org.recruitment.tests.TestManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddTestToOpening
 */
public class AddTestToOpening extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AddTestToOpening() {
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
            
//            JSONObject jsonData = new JSONObject(request.getParameter("JSON"));
            JSONObject jsonData = new JSONObject(jsonLoad.toString());
            logger.info("JSON payload successfully parsed.");
            JSONObject testJson  = jsonData.getJSONObject("test");
            
			Test newTest = new Test(testJson.getString("title"), new Date(testJson.getLong("date")), testJson.getInt("duration"), testJson.getString("typeOfTest"));
			int openingId = testJson.getInt("openingId");
			TestManager.getTestManager().addTest(openingId, newTest);
			
			responseJson.put("status", 200);
			responseJson.put("code", "success");
			
			
			response.getWriter().write(responseJson.toString());
			
			
            
            
            
            
        }
        catch(Exception e) {
        	
        	try {
        	responseJson.put("status", 500);
        	responseJson.put("error", e.getMessage());
        	response.getWriter().write(responseJson.toString());
        	}
        	catch (JSONException e1) {
        		
        	}
        }
		
		
		
		
	}

}
