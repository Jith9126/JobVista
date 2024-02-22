package org.recruitment.tests;

import org.recruitment.users.Applicant;

public class Result {
	private Applicant Applicant;
	private Test test;
	private int rankOfSeeker;
	private boolean selectedOrNot;
	
	public Applicant getApplicant() {
		return Applicant;
	}
	public void setApplicant(Applicant Applicant) {
		this.Applicant = Applicant;
	}
	public Test getTestId() {
		return test;
	}
	public void setTestId(Test test) {
		this.test = test;
	}
	public int getRankOfSeeker() {
		return rankOfSeeker;
	}
	public void setRankOfSeeker(int rankOfSeeker) {
		this.rankOfSeeker = rankOfSeeker;
	}
	public boolean isSelectedOrNot() {
		return selectedOrNot;
	}
	public void setSelectedOrNot(boolean selectedOrNot) {
		this.selectedOrNot = selectedOrNot;
	}
	
	public Result(Applicant Applicant, Test test, int rankOfSeeker, boolean selectedOrNot) {
		this.Applicant = Applicant;
		this.test = test;
		this.rankOfSeeker = rankOfSeeker;
		this.selectedOrNot = selectedOrNot;
	}
}
