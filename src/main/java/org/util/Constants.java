package org.util;

public class Constants {
	
	
	
	//For panelist creating Opening
	public static String countOfOpenings= "Select count(*) From Openings"; 
	public static String selectDepartmentandPanelist = "SELECT Department_Id, Panelist_Id FROM Panelist WHERE Email = ? AND Name = ?";
	public static String selectDepartmentAndOrgId = "SELECT Departments.Department_Id, Departments.Title AS DepartmentName, Organization.Org_Id, Organization.Name AS OrganizationName FROM Departments JOIN Organization ON Departments.Org_Id = Organization.Org_Id WHERE Departments.Title = ? AND Organization.Name = ?";
	public static String selextPanelistFromDepartOrg = "SELECT Panelist_Id, Name, Email, Gender, Position, Department_Id, Org_Id FROM Panelist WHERE Name like ? AND Gender like ? AND Email like ? AND Position like ? AND Org_Id = ? AND Department_Id = ?";
	
	
	
	public static String insertIntoOpenings = "INSERT INTO Openings (Opening_Id, Department_Id, Description, Experience, Qualification, Departments, EmploymentType, SalaryRange, Panelist_Id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static String assignPanelistToOpenings = "INSERT INTO OpeningAndPanelist (Panelist_Id, Opening_Id) VALUES ((SELECT Panelist_Id FROM Panelist WHERE Name like ? AND Email like ?),(SELECT Opening_Id FROM Openings WHERE Opening_Id = ?))";
	
	
	
	//For Adding Test
	
	public static String selectCoutOfTemp = "SELECT COUNT(*) AS NumberOfTemplates FROM `Template` WHERE Opening_Id = ? GROUP BY Opening_Id";
	
	public static String addNewTempToOpening = "INSERT INTO `Template` (`Opening_Id`, `TypeOfTest`, `RoundOn`) VALUES (?, ?, ?);";
	public static String addNewTestOpening = "INSERT INTO `Test` (`Opening_Id`, `Date`, `Title`, `Duration`, `TemplateId`) VALUES (?, ?, ?, ?, (select Template_Id from `Template` where `Opening_Id` = ? and `RoundOn` = ?))";
	
	
	
	public static String updateStartDateInOpening = "UPDATE `Openings` SET `Start_Date` = ? WHERE `Opening_Id` = ?";
	
	
	
	//for choosing a candidate
	public static String testIdFromTileAndOpeningId = "SELECT Test_Id FROM `Test` WHERE `Title` like ? and `Opening_Id` = ?";
	public static String applicantFromMailAndName = "SELECT * FROM `Job_Seeker` WHERE `Name`= ? and `Email` = ?";
	
	
	
	public static String giveResultToApplicant  = "INSERT INTO `Result` (`Job_Seeker_Id`, `Test_Id`, `Status`,`Points`) VALUES (?, ?, ?, ?)";
	
	
	
	
	
}
