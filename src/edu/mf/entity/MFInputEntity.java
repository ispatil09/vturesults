package edu.mf.entity;

import java.util.ArrayList;
import java.util.List;

public class MFInputEntity {
	private SITE_SOURCE siteSource = null;
	private SEMESTER_URLID semesterURLId = null;
	private byte SEMESTER = 0;
	private int year = 0;
	private int numOfRegularSubjects = 8;
	private List<String> usnList = new ArrayList<>();

	public SITE_SOURCE getSiteSource() {
		return siteSource;
	}

	public void setSiteSource(SITE_SOURCE siteSource) {
		this.siteSource = siteSource;
	}

	public int getNumOfRegularSubjects() {
		return numOfRegularSubjects;
	}

	public void setNumOfRegularSubjects(int numOfRegularSubjects) {
		this.numOfRegularSubjects = numOfRegularSubjects;
	}

	public List<String> getInputUSNList() {
		return usnList;
	}

	public void setInputUSNList(List<String> inputUSN) {
		this.usnList = inputUSN;
	}

	public SEMESTER_URLID getSemesterURLId() {
		return semesterURLId;
	}

	public void setSemesterURLId(SEMESTER_URLID semesterURLId) {
		this.semesterURLId = semesterURLId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public byte getSEMESTER() {
		return SEMESTER;
	}

	public void setSEMESTER(byte sEMESTER) {
		SEMESTER = sEMESTER;
	}
}
