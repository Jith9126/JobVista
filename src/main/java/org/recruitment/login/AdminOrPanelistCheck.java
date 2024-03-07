package org.recruitment.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.util.ConnectionClass;

public class AdminOrPanelistCheck {

    public boolean isAdminIdValid(int adminId) {

		try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
			String query = "select * from Admin where Admin_Id = ?;";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setInt(1, adminId);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						return true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			} catch (SQLException e2) {
				
				e2.printStackTrace();
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		return false;
    }
    
    public boolean isPanelistIdValid(int panelistId) {
		try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
			String query = "select * from Panelist where Panelist_Id = ?;";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setInt(1, panelistId);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						return true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			} catch (SQLException e2) {
				
				e2.printStackTrace();
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		return false;
    }
}
