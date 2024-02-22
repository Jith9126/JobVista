package org.util;

public class Constants {
	
	
	
	//For panelist creating Opening
	public static String countOfOpenings= "Select count(*) From Openings"; 
	public static String selectDepartmentandPanelist = "SELECT Department_Id, Panelist_Id FROM Panelist WHERE Email = ? AND Name = ?";
	
	public static String insertIntoOpenings = "INSERT INTO Openings (Opening_Id, Department_Id, Description, Experience, Qualification, Departments, EmploymentType, SalaryRange, Panelist_Id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
}
