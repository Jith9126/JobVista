package org.recruitment.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.recruitment.jobs.Openings;
import org.util.ConnectionClass;
import org.util.Constants;



public class PanelistManager {
	
	
	private static PanelistManager thisPanelistmanager;
	
	public boolean addSkillToUser(Applicant curApplicant,Skill skill) {
		
		return false;
	}
	
	private PanelistManager() {
		
	}
	
	public static PanelistManager getPanelistManger() {
		if(thisPanelistmanager == null) {
			thisPanelistmanager = new PanelistManager();
			
		}
		return thisPanelistmanager;
	}
	
	
	
	
	public int addOpening(Panelist panelist, Openings opening, List<Panelist> interviwers) throws Exception {
		
		try {
		Connection conn = ConnectionClass.CreateCon().getConnection();
		if(panelist.getPosition().equalsIgnoreCase("Manager")){
			
				Statement countOfOpeningsStatement = conn.createStatement();
				int count = 10000;
				ResultSet countOfOpeningsSet = countOfOpeningsStatement.executeQuery(Constants.countOfOpenings);
				if(countOfOpeningsSet.next()) {
					count += countOfOpeningsSet.getInt(1);
				}
				int departmentId = 0;
				int panelistID = 0;
				PreparedStatement selectDepartmentAndPanelistId = conn.prepareStatement(Constants.selectDepartmentandPanelist);
				selectDepartmentAndPanelistId.setString(1, panelist.getEmail());
				selectDepartmentAndPanelistId.setString(2, panelist.getName());
				
				ResultSet departmentPenelistId = selectDepartmentAndPanelistId.executeQuery();
				if(departmentPenelistId.next()) {
					departmentId = departmentPenelistId.getInt("Department_Id");
					panelistID = departmentPenelistId.getInt("Panelist_Id");
				}
				
				PreparedStatement createAnewOpening = conn.prepareStatement(Constants.insertIntoOpenings);
				createAnewOpening.setLong(1,count);
				createAnewOpening.setInt(2, departmentId);
				createAnewOpening.setString(3, opening.getDescription());
				createAnewOpening.setInt(4, opening.getExperience());
				createAnewOpening.setString(5, opening.getQualification());
				createAnewOpening.setString(6, opening.getDepartments());
				createAnewOpening.setString(7, opening.getEmploymentType());
				createAnewOpening.setString(8, opening.getSalaryRange());
				createAnewOpening.setInt(9, panelistID);
				
				
				
				
				
				int affectedRows = createAnewOpening.executeUpdate();
				
				for (Panelist currPanelist: interviwers ) {
					PreparedStatement assinginterviwers = conn.prepareStatement(Constants.assignPanelistToOpenings);
					assinginterviwers.setString(1, currPanelist.getName());
					assinginterviwers.setString(2, currPanelist.getEmail());
					assinginterviwers.setInt(3,count);
					
					assinginterviwers.executeUpdate();
					
				}
				
				
				
	            if (affectedRows > 0) {
	                System.out.println("A new row has been inserted.");
	            }
	            
	            return count;
			}else {
				throw new Exception("You are Not a manager");
			}
			
		}
		catch (SQLException e) {
			// TODO: handle exception
			throw new Exception("Problem in Connection");
		}
	}
	
	public boolean isPanelistExist(Panelist panelist) throws Exception {
		int departmentId=0;
		int orgId=0;
		
		
		try {
			Connection conn = ConnectionClass.CreateCon().getConnection();
			
			PreparedStatement selectDepartmentAndOrgId = conn.prepareStatement(Constants.selectDepartmentAndOrgId);
			
			selectDepartmentAndOrgId.setString(1, panelist.getDepartment());
			selectDepartmentAndOrgId.setString(2, panelist.getOrganistion());
			
			
			ResultSet departmentOrgId = selectDepartmentAndOrgId.executeQuery();
			if(departmentOrgId.next()) {
				departmentId = departmentOrgId.getInt("Department_Id");
				orgId = departmentOrgId.getInt("Org_Id");
			}
			
			
			
			PreparedStatement selectPanelist = conn.prepareStatement(Constants.selextPanelistFromDepartOrg);
			selectPanelist.setString(1, panelist.getName());
			selectPanelist.setString(2,panelist.getGender().getValue());
			selectPanelist.setString(3, panelist.getEmail());
			selectPanelist.setString(4, panelist.getPosition());
			selectPanelist.setInt(5, orgId);
			selectPanelist.setInt(6, departmentId);
			ResultSet panelistrs = selectPanelist.executeQuery();
			if (panelistrs.next()) {
				return true;
			}
			else {
				throw new Exception("Panelist not Found");
			}
			
			
			
		}
		catch (Exception e){
			throw new Exception(e.getMessage());
		}
		
		
		
		
	}
	
}
