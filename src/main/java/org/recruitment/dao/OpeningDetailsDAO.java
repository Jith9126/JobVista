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
	public List<JSONObject> getAllUpComingOpenings() {
		List<JSONObject> openingDetailsList = new ArrayList<>();

		try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
			String query = "SELECT \n" + "    o.Opening_Id AS Opening_Id,\n"
					+ "    o.Department_Id AS Opening_Department_Id,\n" + "    o.Description AS Opening_Description,\n"
					+ "    o.Experience AS Opening_Experience,\n" + "    o.Qualification AS Opening_Qualification,\n"
					+ "    o.Departments AS Opening_Departments,\n"
					+ "    o.EmploymentType AS Opening_EmploymentType,\n"
					+ "    o.SalaryRange AS Opening_SalaryRange,\n" + "    o.Panelist_Id AS Opening_Panelist_Id,\n"
					+ "    o.Start_Date AS Opening_Start_Date,\n" + "    o.End_Date AS Opening_End_Date,\n"
					+ "    p.Panelist_Id AS Panelist_Panelist_Id,\n" + "    p.Name AS Panelist_Name,\n"
					+ "    p.Email AS Panelist_Email,\n" + "    p.Gender AS Panelist_Gender,\n"
					+ "    p.Department_Id AS Panelist_Department_Id,\n" + "    p.Org_Id AS Panelist_Org_Id,\n"
					+ "    p.Position AS Panelist_Position,\n" + "    d.Department_Id AS Department_Department_Id,\n"
					+ "    d.Title AS Department_Title,\n" + "    d.Org_Id AS Department_Org_Id,\n"
					+ "    org1.Org_Id AS Org1_Id,\n" + "    org1.Name AS Org1_Name,\n"
					+ "    org1.TypeOfOrg AS Org1_TypeOfOrg,\n" + "    org1.Industry AS Org1_Industry,\n"
					+ "    org1.ContactEmail AS Org1_ContactEmail,\n"
					+ "    org1.ContactNumber AS Org1_ContactNumber,\n" + "    org2.Org_Id AS Org2_Id,\n"
					+ "    org2.Name AS Org2_Name,\n" + "    org2.TypeOfOrg AS Org2_TypeOfOrg,\n"
					+ "    org2.Industry AS Org2_Industry,\n" + "    org2.ContactEmail AS Org2_ContactEmail,\n"
					+ "    org2.ContactNumber AS Org2_ContactNumber\n" + "FROM Openings o\n"
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

	public List<JSONObject> getAllOpeningsForAdmin(int orgID) {
		List<JSONObject> openingDetailsList = new ArrayList<>();

		String query = "SELECT " + "o.Opening_Id AS Opening_Id, " + "o.Department_Id AS Opening_Department_Id, "
				+ "o.Description AS Opening_Description, " + "o.Experience AS Opening_Experience, "
				+ "o.Qualification AS Opening_Qualification, " + "o.Departments AS Opening_Departments, "
				+ "o.EmploymentType AS Opening_EmploymentType, " + "o.SalaryRange AS Opening_SalaryRange, "
				+ "o.Panelist_Id AS Opening_Panelist_Id, " + "o.Start_Date AS Opening_Start_Date, "
				+ "o.End_Date AS Opening_End_Date, " + "p.Panelist_Id AS Panelist_Panelist_Id, "
				+ "p.Name AS Panelist_Name, " + "p.Email AS Panelist_Email, " + "p.Gender AS Panelist_Gender, "
				+ "p.Department_Id AS Panelist_Department_Id, " + "p.Org_Id AS Panelist_Org_Id, "
				+ "p.Position AS Panelist_Position, " + "d.Department_Id AS Department_Department_Id, "
				+ "d.Title AS Department_Title, " + "d.Org_Id AS Department_Org_Id, " + "org1.Org_Id AS Org1_Id, "
				+ "org1.Name AS Org1_Name, " + "org1.TypeOfOrg AS Org1_TypeOfOrg, " + "org1.Industry AS Org1_Industry, "
				+ "org1.ContactEmail AS Org1_ContactEmail, " + "org1.ContactNumber AS Org1_ContactNumber, "
				+ "org2.Org_Id AS Org2_Id, " + "org2.Name AS Org2_Name, " + "org2.TypeOfOrg AS Org2_TypeOfOrg, "
				+ "org2.Industry AS Org2_Industry, " + "org2.ContactEmail AS Org2_ContactEmail, "
				+ "org2.ContactNumber AS Org2_ContactNumber, " + "t.Test_Id AS Test_Id, " + "t.Date AS Test_Date, "
				+ "t.Title AS Test_Title, " + "t.Duration AS Test_Duration, " + "tmp.Template_Id AS Template_Id, "
				+ "tmp.TypeOfTest AS Template_TypeOfTest, " + "tmp.RoundOn AS Template_RoundOn " + "FROM Openings o "
				+ "JOIN Panelist p ON o.Panelist_Id = p.Panelist_Id "
				+ "JOIN Departments d ON o.Department_Id = d.Department_Id "
				+ "JOIN Organization org1 ON d.Org_Id = org1.Org_Id "
				+ "JOIN Organization org2 ON p.Org_Id = org2.Org_Id "
				+ "LEFT JOIN Test t ON o.Opening_Id = t.Opening_Id "
				+ "LEFT JOIN Template tmp ON t.TemplateId = tmp.Template_Id "
				+ "WHERE o.Start_Date IS NOT NULL AND o.Start_Date >= CURDATE() "
				+ "AND (org1.Org_Id = ? OR org2.Org_Id = ?)";

		try (Connection connection = ConnectionClass.CreateCon().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setInt(1, orgID);
			preparedStatement.setInt(2, orgID);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					System.out.println("Calling mapResultSetToJSONForAdmin");
					try {
						JSONObject openingDetailsJSON = mapResultSetToJSONForAdmin(resultSet);
						openingDetailsList.add(openingDetailsJSON);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return openingDetailsList;
	}

	private JSONObject mapResultSetToJSONForAdmin(ResultSet resultSet) throws SQLException, JSONException {
		JSONObject openingDetailsJSON = new JSONObject();

		openingDetailsJSON.put("Opening", getOpeningDetails(resultSet));
		openingDetailsJSON.put("Panelist", getPanelistDetails(resultSet));
		openingDetailsJSON.put("Organization", getOrganizationDetails(resultSet));
		openingDetailsJSON.put("Department", getDepartmentDetails(resultSet));
		openingDetailsJSON.put("Test", getTestDetails(resultSet));
		openingDetailsJSON.put("Template", getTemplateDetails(resultSet));

//		JSONArray applicantsArray = getApplicantsArray(resultSet);
//		openingDetailsJSON.put("Applicants", applicantsArray);

		return openingDetailsJSON;
	}

	private JSONArray getApplicantsArray(ResultSet resultSet) throws SQLException, JSONException {
	    JSONArray applicantsArray = new JSONArray();
	    long currentOpeningId = -1; // Initialize with a value not possible in your context

	    while (resultSet.next()) {
	        System.out.println("getApplicantsArray");
	        long openingId = resultSet.getLong("Opening_Id");

	        if (currentOpeningId == -1 || openingId != currentOpeningId) {
	            System.out.println("Resetting applicantsArray for Opening_Id: " + openingId);
	            applicantsArray = new JSONArray(); // Moved inside the if block
	            currentOpeningId = openingId;
	        }
	        
	        long applicantId = resultSet.getLong("Applicant_Id");
	        // Fetch applicant details correctly from the ResultSet
	        JSONObject applicantJSON = new JSONObject();
	        applicantJSON.put("applicantGender", resultSet.getString("Applicant_Gender"));
	        applicantJSON.put("applicantName", resultSet.getString("Applicant_Name"));
	        applicantJSON.put("applicantQualification", resultSet.getString("Applicant_Qualification"));
	        applicantJSON.put("applicantId", applicantId);
	        applicantJSON.put("applicantEmail", resultSet.getString("Applicant_Email"));

	        System.out.println(applicantId);
	        System.out.println(applicantJSON.toString());
	        applicantsArray.put(applicantJSON);
	    }
	    System.out.println(applicantsArray.toString());
	    return applicantsArray;
	}




//	private JSONObject getApplicantDetails(ResultSet resultSet) throws SQLException, JSONException {
//		JSONObject applicantJSON = new JSONObject();
//
//		long applicantId = resultSet.getLong("Applicant_Id");
//		if (!resultSet.wasNull()) {
//			applicantJSON.put("applicantId", applicantId);
//			applicantJSON.put("applicantName", resultSet.getString("Applicant_Name"));
//			applicantJSON.put("applicantEmail", resultSet.getString("Applicant_Email"));
//			applicantJSON.put("applicantGender", resultSet.getString("Applicant_Gender"));
//			applicantJSON.put("applicantQualification", resultSet.getString("Applicant_Qualification"));
//		} else {
//			applicantJSON.put("applicantId", JSONObject.NULL);
//			applicantJSON.put("applicantName", JSONObject.NULL);
//			applicantJSON.put("applicantEmail", JSONObject.NULL);
//			applicantJSON.put("applicantGender", JSONObject.NULL);
//			applicantJSON.put("applicantQualification", JSONObject.NULL);
//		}
//
//		return applicantJSON;
//	}

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

	private JSONObject getTestDetails(ResultSet resultSet) throws SQLException, JSONException {
		JSONObject testJSON = new JSONObject();

		long testId = resultSet.getLong("Test_Id");
		if (!resultSet.wasNull()) {
			testJSON.put("testId", testId);
			testJSON.put("testDate", resultSet.getDate("Test_Date"));
			testJSON.put("testTitle", resultSet.getString("Test_Title"));
			testJSON.put("testDuration", resultSet.getInt("Test_Duration"));
		} else {
			testJSON.put("testId", JSONObject.NULL);
			testJSON.put("testDate", JSONObject.NULL);
			testJSON.put("testTitle", JSONObject.NULL);
			testJSON.put("testDuration", JSONObject.NULL);
		}

		return testJSON;
	}

	private JSONObject getTemplateDetails(ResultSet resultSet) throws SQLException, JSONException {
		JSONObject templateJSON = new JSONObject();

		long templateId = resultSet.getLong("Template_Id");
		if (!resultSet.wasNull()) {
			templateJSON.put("templateId", templateId);
			templateJSON.put("templateTypeOfTest", resultSet.getString("Template_TypeOfTest"));
			templateJSON.put("templateRoundOn", resultSet.getInt("Template_RoundOn"));
		} else {
			templateJSON.put("templateId", JSONObject.NULL);
			templateJSON.put("templateTypeOfTest", JSONObject.NULL);
			templateJSON.put("templateRoundOn", JSONObject.NULL);
		}

		return templateJSON;
	}

	public JSONArray convertToJSONArray(List<JSONObject> openingDetailsList) {
		return new JSONArray(openingDetailsList);
	}
}

//public List<JSONObject> getAllOpeningsForAdmin(int orgID) {
//List<JSONObject> openingDetailsList = new ArrayList<>();
//
//String query = "SELECT " +
//        "o.Opening_Id AS Opening_Id, " +
//        "o.Department_Id AS Opening_Department_Id, " +
//        "o.Description AS Opening_Description, " +
//        "o.Experience AS Opening_Experience, " +
//        "o.Qualification AS Opening_Qualification, " +
//        "o.Departments AS Opening_Departments, " +
//        "o.EmploymentType AS Opening_EmploymentType, " +
//        "o.SalaryRange AS Opening_SalaryRange, " +
//        "o.Panelist_Id AS Opening_Panelist_Id, " +
//        "o.Start_Date AS Opening_Start_Date, " +
//        "o.End_Date AS Opening_End_Date, " +
//        "p.Panelist_Id AS Panelist_Panelist_Id, " +
//        "p.Name AS Panelist_Name, " +
//        "p.Email AS Panelist_Email, " +
//        "p.Gender AS Panelist_Gender, " +
//        "p.Department_Id AS Panelist_Department_Id, " +
//        "p.Org_Id AS Panelist_Org_Id, " +
//        "p.Position AS Panelist_Position, " +
//        "d.Department_Id AS Department_Department_Id, " +
//        "d.Title AS Department_Title, " +
//        "d.Org_Id AS Department_Org_Id, " +
//        "org1.Org_Id AS Org1_Id, " +
//        "org1.Name AS Org1_Name, " +
//        "org1.TypeOfOrg AS Org1_TypeOfOrg, " +
//        "org1.Industry AS Org1_Industry, " +
//        "org1.ContactEmail AS Org1_ContactEmail, " +
//        "org1.ContactNumber AS Org1_ContactNumber, " +
//        "org2.Org_Id AS Org2_Id, " +
//        "org2.Name AS Org2_Name, " +
//        "org2.TypeOfOrg AS Org2_TypeOfOrg, " +
//        "org2.Industry AS Org2_Industry, " +
//        "org2.ContactEmail AS Org2_ContactEmail, " +
//        "org2.ContactNumber AS Org2_ContactNumber, " +
//        "t.Test_Id AS Test_Id, " +
//        "t.Date AS Test_Date, " +
//        "t.Title AS Test_Title, " +
//        "t.Duration AS Test_Duration, " +
//        "tmp.Template_Id AS Template_Id, " +
//        "tmp.TypeOfTest AS Template_TypeOfTest, " +
//        "tmp.RoundOn AS Template_RoundOn " +
//        "FROM Openings o " +
//        "JOIN Panelist p ON o.Panelist_Id = p.Panelist_Id " +
//        "JOIN Departments d ON o.Department_Id = d.Department_Id " +
//        "JOIN Organization org1 ON d.Org_Id = org1.Org_Id " +
//        "JOIN Organization org2 ON p.Org_Id = org2.Org_Id " +
//        "LEFT JOIN Test t ON o.Opening_Id = t.Opening_Id " +
//        "LEFT JOIN Template tmp ON t.TemplateId = tmp.Template_Id " +
//        "WHERE o.Start_Date IS NOT NULL AND o.Start_Date >= CURDATE() " +
//        "AND (org1.Org_Id = ? OR org2.Org_Id = ?)";
//
//try (Connection connection = ConnectionClass.CreateCon().getConnection();
//     PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//    preparedStatement.setInt(1, orgID);
//    preparedStatement.setInt(2, orgID);
//
//    try (ResultSet resultSet = preparedStatement.executeQuery()) {
//        while (resultSet.next()) {
//            System.out.println("Calling mapResultSetToJSONForAdmin");
//            try {
//                JSONObject openingDetailsJSON = mapResultSetToJSONForAdmin(resultSet);
//                openingDetailsList.add(openingDetailsJSON);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//} catch (SQLException e) {
//    e.printStackTrace();
//}
//
//return openingDetailsList;
//}






















//package org.recruitment.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.util.ConnectionClass;
//
//public class OpeningDetailsDAO {
//	public List<JSONObject> getAllUpComingOpenings() {
//		List<JSONObject> openingDetailsList = new ArrayList<>();
//
//		try (Connection connection = ConnectionClass.CreateCon().getConnection()) {
//			String query = "SELECT \n" + "    o.Opening_Id AS Opening_Id,\n"
//					+ "    o.Department_Id AS Opening_Department_Id,\n" + "    o.Description AS Opening_Description,\n"
//					+ "    o.Experience AS Opening_Experience,\n" + "    o.Qualification AS Opening_Qualification,\n"
//					+ "    o.Departments AS Opening_Departments,\n"
//					+ "    o.EmploymentType AS Opening_EmploymentType,\n"
//					+ "    o.SalaryRange AS Opening_SalaryRange,\n" + "    o.Panelist_Id AS Opening_Panelist_Id,\n"
//					+ "    o.Start_Date AS Opening_Start_Date,\n" + "    o.End_Date AS Opening_End_Date,\n"
//					+ "    p.Panelist_Id AS Panelist_Panelist_Id,\n" + "    p.Name AS Panelist_Name,\n"
//					+ "    p.Email AS Panelist_Email,\n" + "    p.Gender AS Panelist_Gender,\n"
//					+ "    p.Department_Id AS Panelist_Department_Id,\n" + "    p.Org_Id AS Panelist_Org_Id,\n"
//					+ "    p.Position AS Panelist_Position,\n" + "    d.Department_Id AS Department_Department_Id,\n"
//					+ "    d.Title AS Department_Title,\n" + "    d.Org_Id AS Department_Org_Id,\n"
//					+ "    org1.Org_Id AS Org1_Id,\n" + "    org1.Name AS Org1_Name,\n"
//					+ "    org1.TypeOfOrg AS Org1_TypeOfOrg,\n" + "    org1.Industry AS Org1_Industry,\n"
//					+ "    org1.ContactEmail AS Org1_ContactEmail,\n"
//					+ "    org1.ContactNumber AS Org1_ContactNumber,\n" + "    org2.Org_Id AS Org2_Id,\n"
//					+ "    org2.Name AS Org2_Name,\n" + "    org2.TypeOfOrg AS Org2_TypeOfOrg,\n"
//					+ "    org2.Industry AS Org2_Industry,\n" + "    org2.ContactEmail AS Org2_ContactEmail,\n"
//					+ "    org2.ContactNumber AS Org2_ContactNumber\n" + "FROM Openings o\n"
//					+ "JOIN Panelist p ON o.Panelist_Id = p.Panelist_Id\n"
//					+ "JOIN Departments d ON o.Department_Id = d.Department_Id\n"
//					+ "JOIN Organization org1 ON d.Org_Id = org1.Org_Id\n"
//					+ "JOIN Organization org2 ON p.Org_Id = org2.Org_Id\n"
//					+ "WHERE o.Start_Date IS NOT NULL AND o.Start_Date >= CURDATE();\n";
//
//			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//				try (ResultSet resultSet = preparedStatement.executeQuery()) {
//					while (resultSet.next()) {
//						System.out.println("Calling mapResultSetToJSON");
//						JSONObject openingDetailsJSON;
//						try {
//							openingDetailsJSON = mapResultSetToJSON(resultSet);
//							openingDetailsList.add(openingDetailsJSON);
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return openingDetailsList;
//	}
//
//	public List<JSONObject> getAllOpeningsForAdmin(int orgID) {
//		List<JSONObject> openingDetailsList = new ArrayList<>();
//		String query = "SELECT \n" + "    o.Opening_Id AS Opening_Id,\n"
//				+ "    o.Department_Id AS Opening_Department_Id,\n" + "    o.Description AS Opening_Description,\n"
//				+ "    o.Experience AS Opening_Experience,\n" + "    o.Qualification AS Opening_Qualification,\n"
//				+ "    o.Departments AS Opening_Departments,\n" + "    o.EmploymentType AS Opening_EmploymentType,\n"
//				+ "    o.SalaryRange AS Opening_SalaryRange,\n" + "    o.Panelist_Id AS Opening_Panelist_Id,\n"
//				+ "    o.Start_Date AS Opening_Start_Date,\n" + "    o.End_Date AS Opening_End_Date,\n"
//				+ "    p.Panelist_Id AS Panelist_Panelist_Id,\n" + "    p.Name AS Panelist_Name,\n"
//				+ "    p.Email AS Panelist_Email,\n" + "    p.Gender AS Panelist_Gender,\n"
//				+ "    p.Department_Id AS Panelist_Department_Id,\n" + "    p.Org_Id AS Panelist_Org_Id,\n"
//				+ "    p.Position AS Panelist_Position,\n" + "    d.Department_Id AS Department_Department_Id,\n"
//				+ "    d.Title AS Department_Title,\n" + "    d.Org_Id AS Department_Org_Id,\n"
//				+ "    org1.Org_Id AS Org1_Id,\n" + "    org1.Name AS Org1_Name,\n"
//				+ "    org1.TypeOfOrg AS Org1_TypeOfOrg,\n" + "    org1.Industry AS Org1_Industry,\n"
//				+ "    org1.ContactEmail AS Org1_ContactEmail,\n" + "    org1.ContactNumber AS Org1_ContactNumber,\n"
//				+ "    org2.Org_Id AS Org2_Id,\n" + "    org2.Name AS Org2_Name,\n"
//				+ "    org2.TypeOfOrg AS Org2_TypeOfOrg,\n" + "    org2.Industry AS Org2_Industry,\n"
//				+ "    org2.ContactEmail AS Org2_ContactEmail,\n" + "    org2.ContactNumber AS Org2_ContactNumber,\n"
//				+ "    t.Test_Id AS Test_Id,\n" + "    t.Date AS Test_Date,\n" + "    t.Title AS Test_Title,\n"
//				+ "    t.Duration AS Test_Duration,\n" + "    tmp.Template_Id AS Template_Id,\n"
//				+ "    tmp.TypeOfTest AS Template_TypeOfTest,\n" + "    tmp.RoundOn AS Template_RoundOn,\n"
//				+ "    a.Job_Seeker_Id AS Applicant_Id,\n" + "    a.Name AS Applicant_Name,\n"
//				+ "    a.Email AS Applicant_Email,\n" + "    a.Gender AS Applicant_Gender,\n"
//				+ "    a.Qualification AS Applicant_Qualification\n" + "FROM \n" + "    Openings o\n" + "JOIN \n"
//				+ "    Panelist p ON o.Panelist_Id = p.Panelist_Id\n" + "JOIN \n"
//				+ "    Departments d ON o.Department_Id = d.Department_Id\n" + "JOIN \n"
//				+ "    Organization org1 ON d.Org_Id = org1.Org_Id\n" + "JOIN \n"
//				+ "    Organization org2 ON p.Org_Id = org2.Org_Id\n" + "LEFT JOIN \n"
//				+ "    Test t ON o.Opening_Id = t.Opening_Id\n" + "LEFT JOIN \n"
//				+ "    Template tmp ON t.TemplateId = tmp.Template_Id\n" + "LEFT JOIN \n"
//				+ "    Application app ON o.Opening_Id = app.Opening_Id\n" + "LEFT JOIN \n"
//				+ "    Job_Seeker a ON app.Job_Seeker_Id = a.Job_Seeker_Id\n" + "WHERE \n"
//				+ "    org1.Org_Id = ? OR org2.Org_Id = ?;\n";
//
////o.Start_Date IS NOT NULL AND o.Start_Date >= CURDATE() 
////		try (Connection connection = ConnectionClass.CreateCon().getConnection();
////				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
////
////			preparedStatement.setInt(1, 1);
////			preparedStatement.setInt(2, 1);
////
////			try (ResultSet resultSet = preparedStatement.executeQuery()) {
////
////			    while (resultSet.next()) {
////			        System.out.println(openingDetailsList.toString());
////			        System.out.println("Calling mapResultSetToJSONForAdmin");
////
////			        // Create a new ResultSet object for each iteration
////			        try (ResultSet applicantResultSet = preparedStatement.executeQuery()) {
////			            JSONObject openingDetailsJSON = mapResultSetToJSONForAdmin(applicantResultSet);
////			            openingDetailsList.add(openingDetailsJSON);
////			        } catch (JSONException e) {
////			            e.printStackTrace();
////			        }
////			    }
////			} catch (SQLException e) {
////			    e.printStackTrace();
////			}
////		}
////		catch (Exception e) {
////			e.printStackTrace();
////		}
//
//
//		try (Connection connection = ConnectionClass.CreateCon().getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//			preparedStatement.setInt(1, orgID);
//			preparedStatement.setInt(2, orgID);
//
//			try (ResultSet resultSet = preparedStatement.executeQuery()) {
//				while (resultSet.next()) {
//					System.out.println("Calling mapResultSetToJSONForAdmin");
//					try {
//						JSONObject openingDetailsJSON = mapResultSetToJSONForAdmin(resultSet);
//						openingDetailsList.add(openingDetailsJSON);
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		
//		
//		
//		return openingDetailsList;
//	}
//
//	private JSONObject mapResultSetToJSONForAdmin(ResultSet resultSet) throws SQLException, JSONException {
//		JSONObject openingDetailsJSON = new JSONObject();
//
//		openingDetailsJSON.put("Opening", getOpeningDetails(resultSet));
//		openingDetailsJSON.put("Panelist", getPanelistDetails(resultSet));
//		openingDetailsJSON.put("Organization", getOrganizationDetails(resultSet));
//		openingDetailsJSON.put("Department", getDepartmentDetails(resultSet));
//		openingDetailsJSON.put("Test", getTestDetails(resultSet));
//		openingDetailsJSON.put("Template", getTemplateDetails(resultSet));
//
//		JSONArray applicantsArray = getApplicantsArray(resultSet);
//		openingDetailsJSON.put("Applicants", applicantsArray);
//
//		return openingDetailsJSON;
//	}
//
//	private JSONArray getApplicantsArray(ResultSet resultSet) throws SQLException, JSONException {
//	    JSONArray applicantsArray = new JSONArray();
//	    long currentOpeningId = -1; // Initialize with a value not possible in your context
//
//	    while (resultSet.next()) {
//	        System.out.println("getApplicantsArray");
//	        long openingId = resultSet.getLong("Opening_Id");
//
//	        if (currentOpeningId == -1 || openingId != currentOpeningId) {
//	            System.out.println("Resetting applicantsArray for Opening_Id: " + openingId);
//	            applicantsArray = new JSONArray(); // Moved inside the if block
//	            currentOpeningId = openingId;
//	        }
//	        
//	        long applicantId = resultSet.getLong("Applicant_Id");
//	        // Fetch applicant details correctly from the ResultSet
//	        JSONObject applicantJSON = new JSONObject();
//	        applicantJSON.put("applicantGender", resultSet.getString("Applicant_Gender"));
//	        applicantJSON.put("applicantName", resultSet.getString("Applicant_Name"));
//	        applicantJSON.put("applicantQualification", resultSet.getString("Applicant_Qualification"));
//	        applicantJSON.put("applicantId", applicantId);
//	        applicantJSON.put("applicantEmail", resultSet.getString("Applicant_Email"));
//
//	        System.out.println(applicantId);
//	        System.out.println(applicantJSON.toString());
//	        applicantsArray.put(applicantJSON);
//	    }
//	    System.out.println(applicantsArray.toString());
//	    return applicantsArray;
//	}
//
//
//
//
////	private JSONObject getApplicantDetails(ResultSet resultSet) throws SQLException, JSONException {
////		JSONObject applicantJSON = new JSONObject();
////
////		long applicantId = resultSet.getLong("Applicant_Id");
////		if (!resultSet.wasNull()) {
////			applicantJSON.put("applicantId", applicantId);
////			applicantJSON.put("applicantName", resultSet.getString("Applicant_Name"));
////			applicantJSON.put("applicantEmail", resultSet.getString("Applicant_Email"));
////			applicantJSON.put("applicantGender", resultSet.getString("Applicant_Gender"));
////			applicantJSON.put("applicantQualification", resultSet.getString("Applicant_Qualification"));
////		} else {
////			applicantJSON.put("applicantId", JSONObject.NULL);
////			applicantJSON.put("applicantName", JSONObject.NULL);
////			applicantJSON.put("applicantEmail", JSONObject.NULL);
////			applicantJSON.put("applicantGender", JSONObject.NULL);
////			applicantJSON.put("applicantQualification", JSONObject.NULL);
////		}
////
////		return applicantJSON;
////	}
//
//	private JSONObject mapResultSetToJSON(ResultSet resultSet) throws SQLException, JSONException {
//		JSONObject openingDetailsJSON = new JSONObject();
//
//		openingDetailsJSON.put("Opening", getOpeningDetails(resultSet));
//		openingDetailsJSON.put("Panelist", getPanelistDetails(resultSet));
//		openingDetailsJSON.put("Organization", getOrganizationDetails(resultSet));
//		openingDetailsJSON.put("Department", getDepartmentDetails(resultSet));
//
//		return openingDetailsJSON;
//	}
//
//	private JSONObject getOpeningDetails(ResultSet resultSet) throws SQLException, JSONException {
//		JSONObject openingJSON = new JSONObject();
//
//		openingJSON.put("openingId", resultSet.getLong("Opening_Id"));
//		openingJSON.put("description", resultSet.getString("Opening_Description"));
//		openingJSON.put("experience", resultSet.getInt("Opening_Experience"));
//		openingJSON.put("qualification", resultSet.getString("Opening_Qualification"));
//		openingJSON.put("departments", resultSet.getString("Opening_Departments"));
//		openingJSON.put("employmentType", resultSet.getString("Opening_EmploymentType"));
//		openingJSON.put("salaryRange", resultSet.getString("Opening_SalaryRange"));
//		openingJSON.put("startDate", resultSet.getDate("Opening_Start_Date"));
//		openingJSON.put("endDate", resultSet.getDate("Opening_End_Date"));
//
//		return openingJSON;
//	}
//
//	private JSONObject getPanelistDetails(ResultSet resultSet) throws SQLException, JSONException {
//		JSONObject panelistJSON = new JSONObject();
//
//		panelistJSON.put("panelistId", resultSet.getLong("Panelist_Panelist_Id"));
//		panelistJSON.put("panelistName", resultSet.getString("Panelist_Name"));
//		panelistJSON.put("panelistEmail", resultSet.getString("Panelist_Email"));
//		panelistJSON.put("panelistGender", resultSet.getString("Panelist_Gender"));
//		panelistJSON.put("panelistDepartmentId", resultSet.getLong("Panelist_Department_Id"));
//		panelistJSON.put("panelistOrgId", resultSet.getLong("Panelist_Org_Id"));
//		panelistJSON.put("panelistPosition", resultSet.getString("Panelist_Position"));
//
//		return panelistJSON;
//	}
//
//	private JSONObject getOrganizationDetails(ResultSet resultSet) throws SQLException, JSONException {
//		JSONObject organizationJSON = new JSONObject();
//
//		organizationJSON.put("orgId", resultSet.getLong("Org1_Id"));
//		organizationJSON.put("orgName", resultSet.getString("Org1_Name"));
//		organizationJSON.put("orgTypeOfOrg", resultSet.getString("Org1_TypeOfOrg"));
//		organizationJSON.put("orgIndustry", resultSet.getString("Org1_Industry"));
//		organizationJSON.put("orgContactEmail", resultSet.getString("Org1_ContactEmail"));
//		organizationJSON.put("orgContactNumber", resultSet.getString("Org1_ContactNumber"));
//		return organizationJSON;
//	}
//
//	private JSONObject getDepartmentDetails(ResultSet resultSet) throws SQLException, JSONException {
//		JSONObject departmentJSON = new JSONObject();
//
//		departmentJSON.put("departmentId", resultSet.getLong("Department_Department_Id"));
//		departmentJSON.put("departmentTitle", resultSet.getString("Department_Title"));
//		departmentJSON.put("departmentOrgId", resultSet.getLong("Department_Org_Id"));
//
//		return departmentJSON;
//	}
//
//	private JSONObject getTestDetails(ResultSet resultSet) throws SQLException, JSONException {
//		JSONObject testJSON = new JSONObject();
//
//		long testId = resultSet.getLong("Test_Id");
//		if (!resultSet.wasNull()) {
//			testJSON.put("testId", testId);
//			testJSON.put("testDate", resultSet.getDate("Test_Date"));
//			testJSON.put("testTitle", resultSet.getString("Test_Title"));
//			testJSON.put("testDuration", resultSet.getInt("Test_Duration"));
//		} else {
//			testJSON.put("testId", JSONObject.NULL);
//			testJSON.put("testDate", JSONObject.NULL);
//			testJSON.put("testTitle", JSONObject.NULL);
//			testJSON.put("testDuration", JSONObject.NULL);
//		}
//
//		return testJSON;
//	}
//
//	private JSONObject getTemplateDetails(ResultSet resultSet) throws SQLException, JSONException {
//		JSONObject templateJSON = new JSONObject();
//
//		long templateId = resultSet.getLong("Template_Id");
//		if (!resultSet.wasNull()) {
//			templateJSON.put("templateId", templateId);
//			templateJSON.put("templateTypeOfTest", resultSet.getString("Template_TypeOfTest"));
//			templateJSON.put("templateRoundOn", resultSet.getInt("Template_RoundOn"));
//		} else {
//			templateJSON.put("templateId", JSONObject.NULL);
//			templateJSON.put("templateTypeOfTest", JSONObject.NULL);
//			templateJSON.put("templateRoundOn", JSONObject.NULL);
//		}
//
//		return templateJSON;
//	}
//
//	public JSONArray convertToJSONArray(List<JSONObject> openingDetailsList) {
//		return new JSONArray(openingDetailsList);
//	}
//}
//
////public List<JSONObject> getAllOpeningsForAdmin(int orgID) {
////List<JSONObject> openingDetailsList = new ArrayList<>();
////
////String query = "SELECT " +
////        "o.Opening_Id AS Opening_Id, " +
////        "o.Department_Id AS Opening_Department_Id, " +
////        "o.Description AS Opening_Description, " +
////        "o.Experience AS Opening_Experience, " +
////        "o.Qualification AS Opening_Qualification, " +
////        "o.Departments AS Opening_Departments, " +
////        "o.EmploymentType AS Opening_EmploymentType, " +
////        "o.SalaryRange AS Opening_SalaryRange, " +
////        "o.Panelist_Id AS Opening_Panelist_Id, " +
////        "o.Start_Date AS Opening_Start_Date, " +
////        "o.End_Date AS Opening_End_Date, " +
////        "p.Panelist_Id AS Panelist_Panelist_Id, " +
////        "p.Name AS Panelist_Name, " +
////        "p.Email AS Panelist_Email, " +
////        "p.Gender AS Panelist_Gender, " +
////        "p.Department_Id AS Panelist_Department_Id, " +
////        "p.Org_Id AS Panelist_Org_Id, " +
////        "p.Position AS Panelist_Position, " +
////        "d.Department_Id AS Department_Department_Id, " +
////        "d.Title AS Department_Title, " +
////        "d.Org_Id AS Department_Org_Id, " +
////        "org1.Org_Id AS Org1_Id, " +
////        "org1.Name AS Org1_Name, " +
////        "org1.TypeOfOrg AS Org1_TypeOfOrg, " +
////        "org1.Industry AS Org1_Industry, " +
////        "org1.ContactEmail AS Org1_ContactEmail, " +
////        "org1.ContactNumber AS Org1_ContactNumber, " +
////        "org2.Org_Id AS Org2_Id, " +
////        "org2.Name AS Org2_Name, " +
////        "org2.TypeOfOrg AS Org2_TypeOfOrg, " +
////        "org2.Industry AS Org2_Industry, " +
////        "org2.ContactEmail AS Org2_ContactEmail, " +
////        "org2.ContactNumber AS Org2_ContactNumber, " +
////        "t.Test_Id AS Test_Id, " +
////        "t.Date AS Test_Date, " +
////        "t.Title AS Test_Title, " +
////        "t.Duration AS Test_Duration, " +
////        "tmp.Template_Id AS Template_Id, " +
////        "tmp.TypeOfTest AS Template_TypeOfTest, " +
////        "tmp.RoundOn AS Template_RoundOn " +
////        "FROM Openings o " +
////        "JOIN Panelist p ON o.Panelist_Id = p.Panelist_Id " +
////        "JOIN Departments d ON o.Department_Id = d.Department_Id " +
////        "JOIN Organization org1 ON d.Org_Id = org1.Org_Id " +
////        "JOIN Organization org2 ON p.Org_Id = org2.Org_Id " +
////        "LEFT JOIN Test t ON o.Opening_Id = t.Opening_Id " +
////        "LEFT JOIN Template tmp ON t.TemplateId = tmp.Template_Id " +
////        "WHERE o.Start_Date IS NOT NULL AND o.Start_Date >= CURDATE() " +
////        "AND (org1.Org_Id = ? OR org2.Org_Id = ?)";
////
////try (Connection connection = ConnectionClass.CreateCon().getConnection();
////     PreparedStatement preparedStatement = connection.prepareStatement(query)) {
////
////    preparedStatement.setInt(1, orgID);
////    preparedStatement.setInt(2, orgID);
////
////    try (ResultSet resultSet = preparedStatement.executeQuery()) {
////        while (resultSet.next()) {
////            System.out.println("Calling mapResultSetToJSONForAdmin");
////            try {
////                JSONObject openingDetailsJSON = mapResultSetToJSONForAdmin(resultSet);
////                openingDetailsList.add(openingDetailsJSON);
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
////        }
////    }
////} catch (SQLException e) {
////    e.printStackTrace();
////}
////
////return openingDetailsList;
////}
