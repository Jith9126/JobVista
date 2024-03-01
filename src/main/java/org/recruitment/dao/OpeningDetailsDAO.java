package org.recruitment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.util.ConnectionClass;

public class OpeningDetailsDAO {
    public List<JSONObject> getAllOpenings() {
        List<JSONObject> openingDetailsList = new ArrayList<>();

        try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
        	String query = "SELECT \n"
        			+ "    o.Opening_Id AS Opening_Id,\n"
        			+ "    o.Department_Id AS Opening_Department_Id,\n"
        			+ "    o.Description AS Opening_Description,\n"
        			+ "    o.Experience AS Opening_Experience,\n"
        			+ "    o.Qualification AS Opening_Qualification,\n"
        			+ "    o.Departments AS Opening_Departments,\n"
        			+ "    o.EmploymentType AS Opening_EmploymentType,\n"
        			+ "    o.SalaryRange AS Opening_SalaryRange,\n"
        			+ "    o.Panelist_Id AS Opening_Panelist_Id,\n"
        			+ "    o.Start_Date AS Opening_Start_Date,\n"
        			+ "    o.End_Date AS Opening_End_Date,\n"
        			+ "    p.Panelist_Id AS Panelist_Panelist_Id,\n"
        			+ "    p.Name AS Panelist_Name,\n"
        			+ "    p.Email AS Panelist_Email,\n"
        			+ "    p.Gender AS Panelist_Gender,\n"
        			+ "    p.Department_Id AS Panelist_Department_Id,\n"
        			+ "    p.Org_Id AS Panelist_Org_Id,\n"
        			+ "    p.Position AS Panelist_Position,\n"
        			+ "    d.Department_Id AS Department_Department_Id,\n"
        			+ "    d.Title AS Department_Title,\n"
        			+ "    d.Org_Id AS Department_Org_Id,\n"
        			+ "    org1.Org_Id AS Org1_Id,\n"
        			+ "    org1.Name AS Org1_Name,\n"
        			+ "    org1.TypeOfOrg AS Org1_TypeOfOrg,\n"
        			+ "    org1.Industry AS Org1_Industry,\n"
        			+ "    org1.ContactEmail AS Org1_ContactEmail,\n"
        			+ "    org1.ContactNumber AS Org1_ContactNumber,\n"
        			+ "    org2.Org_Id AS Org2_Id,\n"
        			+ "    org2.Name AS Org2_Name,\n"
        			+ "    org2.TypeOfOrg AS Org2_TypeOfOrg,\n"
        			+ "    org2.Industry AS Org2_Industry,\n"
        			+ "    org2.ContactEmail AS Org2_ContactEmail,\n"
        			+ "    org2.ContactNumber AS Org2_ContactNumber\n"
        			+ "FROM Openings o\n"
        			+ "JOIN Panelist p ON o.Panelist_Id = p.Panelist_Id\n"
        			+ "JOIN Departments d ON o.Department_Id = d.Department_Id\n"
        			+ "JOIN Organization org1 ON d.Org_Id = org1.Org_Id\n"
        			+ "JOIN Organization org2 ON p.Org_Id = org2.Org_Id\n"
        			+ "WHERE o.Start_Date IS NOT NULL AND o.Start_Date >= CURDATE();\n";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("Calling mapResultSetToJSON");
                        JSONObject openingDetailsJSON;
						try {
							openingDetailsJSON = mapResultSetToJSON(resultSet);
							openingDetailsList.add(openingDetailsJSON);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return openingDetailsList;
    }

    private JSONObject mapResultSetToJSON(ResultSet resultSet) throws SQLException, JSONException {
        JSONObject openingDetailsJSON = new JSONObject();

        openingDetailsJSON.put("Opening", getOpeningDetails(resultSet));
        openingDetailsJSON.put("Panelist", getPanelistDetails(resultSet));
        openingDetailsJSON.put("Organization", getOrganizationDetails(resultSet));
        openingDetailsJSON.put("Department", getDepartmentDetails(resultSet));

        return openingDetailsJSON;
    }

    private JSONObject getOpeningDetails(ResultSet resultSet) throws SQLException, JSONException {
        JSONObject openingJSON = new JSONObject();

        openingJSON.put("openingId", resultSet.getLong("Opening_Id"));
        openingJSON.put("description", resultSet.getString("Opening_Description"));
        openingJSON.put("experience", resultSet.getInt("Opening_Experience"));
        openingJSON.put("qualification", resultSet.getString("Opening_Qualification"));
        openingJSON.put("departments", resultSet.getString("Opening_Departments"));
        openingJSON.put("employmentType", resultSet.getString("Opening_EmploymentType"));
        openingJSON.put("salaryRange", resultSet.getString("Opening_SalaryRange"));
        openingJSON.put("startDate", resultSet.getDate("Opening_Start_Date"));
        openingJSON.put("endDate", resultSet.getDate("Opening_End_Date"));

        return openingJSON;
    }

    private JSONObject getPanelistDetails(ResultSet resultSet) throws SQLException, JSONException {
        JSONObject panelistJSON = new JSONObject();

        panelistJSON.put("panelistId", resultSet.getLong("Panelist_Panelist_Id"));
        panelistJSON.put("panelistName", resultSet.getString("Panelist_Name"));
        panelistJSON.put("panelistEmail", resultSet.getString("Panelist_Email"));
        panelistJSON.put("panelistGender", resultSet.getString("Panelist_Gender"));
        panelistJSON.put("panelistDepartmentId", resultSet.getLong("Panelist_Department_Id"));
        panelistJSON.put("panelistOrgId", resultSet.getLong("Panelist_Org_Id"));
        panelistJSON.put("panelistPosition", resultSet.getString("Panelist_Position"));

        return panelistJSON;
    }

    private JSONObject getOrganizationDetails(ResultSet resultSet) throws SQLException, JSONException {
        JSONObject organizationJSON = new JSONObject();

        organizationJSON.put("orgId", resultSet.getLong("Org1_Id"));
        organizationJSON.put("orgName", resultSet.getString("Org1_Name"));
        organizationJSON.put("orgTypeOfOrg", resultSet.getString("Org1_TypeOfOrg"));
        organizationJSON.put("orgIndustry", resultSet.getString("Org1_Industry"));
        organizationJSON.put("orgContactEmail", resultSet.getString("Org1_ContactEmail"));
        organizationJSON.put("orgContactNumber", resultSet.getString("Org1_ContactNumber"));
        return organizationJSON;
    }

    private JSONObject getDepartmentDetails(ResultSet resultSet) throws SQLException, JSONException {
        JSONObject departmentJSON = new JSONObject();

        departmentJSON.put("departmentId", resultSet.getLong("Department_Department_Id"));
        departmentJSON.put("departmentTitle", resultSet.getString("Department_Title"));
        departmentJSON.put("departmentOrgId", resultSet.getLong("Department_Org_Id"));

        return departmentJSON;
    }

    public JSONArray convertToJSONArray(List<JSONObject> openingDetailsList) {
        return new JSONArray(openingDetailsList);
    }
}
