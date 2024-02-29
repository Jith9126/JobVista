package org.recruitment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.recruitment.dto.ApplicantDTO;
import org.util.ConnectionClass;

public class ApplicantDAOImpl implements ApplicantDAO {
    @Override
    public boolean addApplicant(ApplicantDTO applicantDTO) {
        try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
            String query = "INSERT INTO Applicant (Name, Email, DOB, Gender, Experience, Department_Id, Phone, Qualification, Photo) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, applicantDTO.getName());
                preparedStatement.setString(2, applicantDTO.getEmail());
                preparedStatement.setDate(3, applicantDTO.getDob());
                preparedStatement.setString(4, applicantDTO.getGender().getValue());
                preparedStatement.setInt(5, applicantDTO.getExperience());
                preparedStatement.setInt(6, applicantDTO.getDepartmentId());
                preparedStatement.setString(7, applicantDTO.getPhoneNo());
                preparedStatement.setString(8, applicantDTO.getQualification());
                preparedStatement.setString(9, applicantDTO.getPhoto());
                
                int rowsAffected = preparedStatement.executeUpdate();
                
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return false;
        }
    }

    @Override
    public boolean removeApplicant(String name, String email) {
        try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
            String query = "DELETE FROM Applicant WHERE Name LIKE ? AND Email LIKE ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + name + "%");
                preparedStatement.setString(2, "%" + email + "%");
                
                int rowsAffected = preparedStatement.executeUpdate();
                
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean applyForJob(String name, String email, int openingId) {
        try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
            String query = "SELECT Applicant_Id FROM Applicant WHERE Name LIKE ? AND Email LIKE ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + name + "%");
                preparedStatement.setString(2, "%" + email + "%");
                
                ResultSet rs = preparedStatement.executeQuery();
                
                while (rs.next()) {
                    int applicantId = rs.getInt("Applicant_Id");
                    String applyQuery = "INSERT INTO Application (Applicant_Id, Opening_Id, Date) VALUES (?, ?, ?)";
                    
                    try (PreparedStatement applyStatement = connection.prepareStatement(applyQuery)) {
                        applyStatement.setInt(1, applicantId);
                        applyStatement.setInt(2, openingId);
                        applyStatement.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
                        
                        int rowsAffected = applyStatement.executeUpdate();
                        
                        return rowsAffected > 0;
                    }
                }
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            return false;
        }
		return false;
    }


    // Other methods for data access
}
