package org.recruitment.login;
import org.json.JSONException;
import org.json.JSONObject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebFilter("/Login")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        System.out.println("checking");
        
        if (httpRequest.getMethod().equalsIgnoreCase("POST") && httpRequest.getContentType().equalsIgnoreCase("application/json")) {
            StringBuilder builder = new StringBuilder();
            
            try (BufferedReader reader = httpRequest.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } 
            catch (IOException e) {
                httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reading request body");
                return;
            }

            String jsonData = builder.toString();
            
            JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(jsonData);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            String email = jsonObject.optString("email");
            String password = jsonObject.optString("password");

            String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

            if (!password.matches(passwordRegex) || !email.matches(emailRegex)) {
            	JSONObject jsonResponse = new JSONObject();
                try {
        			jsonResponse.put("error", true);
        			jsonResponse.put("statusCode", 500);
        			jsonResponse.put("message", "Invalid email or password format");
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                
                response.getWriter().print(jsonResponse.toString());
                return;
            }

            // Set the JSON object as a request attribute for further processing
            request.setAttribute("jsonObject", jsonObject);
        }

        // Proceed with the filter chain
        chain.doFilter(request, response);
    }

   

    @Override
    public void destroy() {
        // Clean-up code if needed
    }
}
