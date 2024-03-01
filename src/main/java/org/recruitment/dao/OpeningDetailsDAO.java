package org.recruitment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.recruitment.dto.DepartmentsDTO;
import org.recruitment.dto.OpeningDetailsDTO;
import org.recruitment.dto.OpeningsDTO;
import org.recruitment.dto.OrganizationDTO;
import org.recruitment.dto.PanelistDTO;
import org.util.ConnectionClass;

public class OpeningDetailsDAO {
    public List<OpeningDetailsDTO> getAllOpenings() {
        List<OpeningDetailsDTO> openingDetailsList = new ArrayList<>();

        try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
            String query = "SELECT * FROM Openings o " +
                    "JOIN Panelist p ON o.Panelist_Id = p.Panelist_Id " +
                    "JOIN Departments d ON o.Department_Id = d.Department_Id " +
                    "JOIN Organization org ON d.Org_Id = org.Org_Id " +
                    "WHERE o.Start_Date > NOW()";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        OpeningDetailsDTO openingDetailsDTO = mapResultSetToDTO(resultSet);
                        openingDetailsList.add(openingDetailsDTO);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return openingDetailsList;
    }

    private OpeningDetailsDTO mapResultSetToDTO(ResultSet resultSet) throws SQLException {
        OpeningsDTO openingDTO = new OpeningsDTO(
                resultSet.getLong("Opening_Id"),
                resultSet.getString("Description"),
                resultSet.getInt("Experience"),
                resultSet.getString("Qualification"),
                resultSet.getString("Departments"),
                resultSet.getString("EmploymentType"),
                resultSet.getString("SalaryRange"),
                resultSet.getDate("Start_Date"),
                resultSet.getDate("End_Date")
        );

        PanelistDTO panelistDTO = new PanelistDTO(
                resultSet.getString("Name"),
                resultSet.getString("Email")
        );

        OrganizationDTO organizationDTO = new OrganizationDTO(
                resultSet.getString("Org_Name")
        );

        DepartmentsDTO departmentDTO = new DepartmentsDTO(
                resultSet.getString("Title")
        );

        OpeningDetailsDTO openingDetailsDTO = new OpeningDetailsDTO();
        openingDetailsDTO.setOpening(openingDTO);
        openingDetailsDTO.setPanelist(panelistDTO);
        openingDetailsDTO.setOrganization(organizationDTO);
        openingDetailsDTO.setDepartment(departmentDTO);

        return openingDetailsDTO;
    }
}
