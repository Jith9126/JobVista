package org.recruitment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.util.ConnectionClass;
import org.util.Constants;

public class Login {
	
	public String checkUser(String email, String password) throws SQLException {
		
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		
        PreparedStatement statement = connection.prepareStatement(Constants.checkUser);
        statement.setString(1, email);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        System.out.println("cefdc");
        if (rs.next()) {
        	
            updateSession(email);
            return "Logged in successfully";
        }
        return "Invalid email or password";
	}
	
	public void updateSession(String email) throws SQLException {
		
		String sessionId = UUID.randomUUID().toString();
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();

        PreparedStatement updateSession = connection.prepareStatement(Constants.updateSession);
        updateSession.setString(1, sessionId);
        updateSession.setString(2, email);
        updateSession.executeUpdate();

	}
}
