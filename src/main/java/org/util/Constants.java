package org.util;

public class Constants {
	
	
	
	//For panelist creating Opening
	public static String countOfOpenings= "Select count(*) From Openings"; 
	public static String selectDepartmentandPanelist = "SELECT Department_Id, Panelist_Id FROM Panelist WHERE Email = ? AND Name = ?";
	public static String selectDepartmentAndOrgId = "SELECT Departments.Department_Id, Departments.Title AS DepartmentName, Organization.Org_Id, Organization.Name AS OrganizationName FROM Departments JOIN Organization ON Departments.Org_Id = Organization.Org_Id WHERE Departments.Title = ? AND Organization.Name = ?";
	public static String selextPanelistFromDepartOrg = "SELECT Panelist_Id, Name, Email, Gender, Position, Department_Id, Org_Id FROM Panelist WHERE Name like ? AND Gender like ? AND Email like ? AND Position like ? AND Org_Id = ? AND Department_Id = ?";
	
	
	
	public static String insertIntoOpenings = "INSERT INTO Openings (Opening_Id, Department_Id, Description, Experience, Qualification, Departments, EmploymentType, SalaryRange, Panelist_Id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
}
