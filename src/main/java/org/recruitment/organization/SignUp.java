package org.recruitment.organization;

import org.apache.log4j.Logger;
import org.util.CommonLogger;
import org.util.ConnectionClass;
import org.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUp {
	Logger logger = CommonLogger.getCommon().getLogger(SignUp.class);
	public String addAdmin(String name, String email, String password, int orgId) throws SQLException {
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement(Constants.addAdmin);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, email);
		preparedStatement.setInt(3, orgId);
		int affectedRows = preparedStatement.executeUpdate();
		if (affectedRows <=0 ) {
			preparedStatement = connection.prepareStatement(Constants.addUser);
			preparedStatement.setString(1, "Admin");
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, email);
			int addUser = preparedStatement.executeUpdate();
			
			if(addUser>0) {
				logger.info("Admin sucessfully added");
				return "Admin successfully added";
			}
		}
		logger.info("Failed to add admin");
		return "Failed to add Admin";
		
	}
	
	public String addOrganisation(String orgName, String orgType, String industry, String contactEmail, String contactNumber, String adminName, String adminEmail, String adminPassword) throws SQLException {
		
		ConnectionClass db = ConnectionClass.CreateCon();
		Connection connection = db.getConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement(Constants.addOrganization);
		preparedStatement.setString(1, orgName);
		preparedStatement.setString(2, orgType);
		preparedStatement.setString(3, industry);
		preparedStatement.setString(4, contactEmail);
		preparedStatement.setString(5, contactNumber);
		int affectedRows = preparedStatement.executeUpdate();
		if (affectedRows <=0 ) {
			logger.info("Failed to add Oraganization");
			return "Failed to add Oraganization";
		}
		
		preparedStatement = connection.prepareStatement(Constants.getOrgId);
		preparedStatement.setString(1, orgName);
		ResultSet resultSet = preparedStatement.executeQuery();
		int org_id = 0;
		while(resultSet.next()) {
			logger.info("Organization successfully added");
			org_id = resultSet.getInt("Org_Id");
		}
		return addAdmin(adminName, adminEmail, adminPassword, org_id);
		
	}
	
}
