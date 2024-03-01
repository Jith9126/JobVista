package org.recruitment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.util.ConnectionClass;
import org.util.Constants;

import jakarta.servlet.http.Cookie;

public class Login {
	
	String role = null;
	public String checkUser(String email, String password) throws SQLException {
		
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		
        PreparedStatement statement = connection.prepareStatement(Constants.checkUser);
        statement.setString(1, email);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
        	this.role = rs.getString("Role");
            return "Logged in successfully";
        }
        return "Invalid email or password";
	}
	
	public Cookie[] updateSession(String email) throws SQLException {
	    String sessionId = UUID.randomUUID().toString();
	    ConnectionClass db = ConnectionClass.CreateCon();
	    Connection connection = db.getConnection();

	    PreparedStatement updateSession = connection.prepareStatement(Constants.updateSession);
	    updateSession.setString(1, sessionId);
	    updateSession.setString(2, email);
	    updateSession.executeUpdate();

	    Cookie sessionIdCookie = new Cookie("session_Id", sessionId);
	    sessionIdCookie.setMaxAge(86400);
	    
	    Cookie orgId = null;
	    PreparedStatement getOrgId = connection.prepareStatement(Constants.OrgId);
	    getOrgId.setString(1, email);
	    ResultSet orgResultSet = getOrgId.executeQuery();
	    while (orgResultSet.next()) {
	        orgId = new Cookie("org_Id", String.valueOf(orgResultSet.getInt("Org_Id")));
	        orgId.setMaxAge(86400);
	    }
	   
	    Cookie panelistId = null;
	    Cookie adminId = null;

	    if (this.role.equalsIgnoreCase("Admin")) {
	        PreparedStatement getAdminId = connection.prepareStatement(Constants.adminId);
	        getAdminId.setString(1, email);
	        ResultSet adminResultSet = getAdminId.executeQuery();
	        while (adminResultSet.next()) {
	            adminId = new Cookie("admin_Id", String.valueOf(adminResultSet.getInt("Admin_Id")));
	            adminId.setMaxAge(86400);
	        }
	    } else {
	        PreparedStatement getPanelistId = connection.prepareStatement(Constants.panelistId);
	        getPanelistId.setString(1, email);
	        ResultSet panelistResultSet = getPanelistId.executeQuery();
	        while (panelistResultSet.next()) {
	            panelistId = new Cookie("panelist_Id", String.valueOf(panelistResultSet.getInt("Panelist_Id")));
	            panelistId.setMaxAge(86400);
	        }
	    }
	    
	    if (orgId == null) {
	        orgId = new Cookie("org_Id", "");
	        orgId.setMaxAge(0); 
	    }
	    
	    if (panelistId == null) {
	        panelistId = new Cookie("panelist_Id", "");
	        panelistId.setMaxAge(0); 
	    }
	    
	    if (adminId == null) {
	        adminId = new Cookie("admin_Id", "");
	        adminId.setMaxAge(0);
	    }
	    
	    Cookie[] cookies = {sessionIdCookie, orgId, panelistId, adminId};
	    return cookies;
	}

	
}
