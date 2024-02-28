package org.servlet.panalist;

import org.util.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InterviewerService {

    public void addReview(int panelistId, int jobSeekerId, String review) throws SQLException {
        try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
            String query = "INSERT INTO NotesFromPanelist (Panelist_Id, Job_Seeker_Id, Review) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, panelistId);
                preparedStatement.setInt(2, jobSeekerId);
                preparedStatement.setString(3, review);
                preparedStatement.executeUpdate();
            }
        }
    }

    public void setMarks(int panelistId, int jobSeekerId, int marks) throws SQLException {
        try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
            String query = "UPDATE NotesFromPanelist SET Points = ? WHERE Panelist_Id = ? AND Job_Seeker_Id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, marks);
                preparedStatement.setInt(2, panelistId);
                preparedStatement.setInt(3, jobSeekerId);
                preparedStatement.executeUpdate();
            }
        }
    }
}
