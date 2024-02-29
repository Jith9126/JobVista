package org.recruitment.users;

import org.util.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InterviewerService {
	
    public void addReview(int panelistId, int jobSeekerId, String review, int points) throws SQLException {
        try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
            String query = "INSERT INTO NotesFromPanelist (Panelist_Id, Job_Seeker_Id, Review, Points) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, panelistId);
                preparedStatement.setInt(2, jobSeekerId);
                preparedStatement.setString(3, review);
                preparedStatement.setInt(4, points);
                preparedStatement.executeUpdate();
            }
        }
    }

    public void setMarks(int jobSeekerId, int testId, int marks, String status) throws SQLException {
        try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
            String query = "INSERT INTO Result (Job_Seeker_Id, Test_Id, Points, Status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, jobSeekerId);
                preparedStatement.setInt(2, testId);
                preparedStatement.setInt(3, marks);
                preparedStatement.setString(4, status);
                preparedStatement.executeUpdate();
            }
        }
    }

}