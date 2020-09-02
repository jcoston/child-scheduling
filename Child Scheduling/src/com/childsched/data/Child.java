package com.childsched.data;

import java.time.*;


public class Child {
	private String    sFamilyName = "";
	private String    sName = "";
	private String    gender = "";
	private LocalDate dBirthDate = LocalDate.of(1900, 1, 1);
	
	// Default Constructor
	public Child() {
	}
	
	// Constructor 2
	public Child(String familyName, String name, String gender, int yr, int mo, int da) {
		sFamilyName = familyName;
		sName = name;
		this.gender = gender;
		dBirthDate = LocalDate.of(yr, mo, da);
		
	}
	
	//----------------------------//
	// Class Methods              //
	//----------------------------//
	
	// Output this class to the console
	public void toConsole() {
		System.out.println("Name: " + sName);
		System.out.println("Gender: " + gender);
		System.out.println("Birth Date: " + dBirthDate.toString());
		System.out.println("Current Age: " + new Integer(getAge()).toString());
	}
	
	// Return the age of the child (years only)
	public int getAge() {
		Period pd = dBirthDate.until(LocalDate.now());
		return pd.getYears();
	}

	//----------------------------//
	// Getters/Setters            //
	//----------------------------//
	
	public String getsFamilyName() {
		return sFamilyName;
	}

	public void setsFamilyName(String sFamilyName) {
		this.sFamilyName = sFamilyName;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getdBirthDate() {
		return dBirthDate;
	}

	public void setdBirthDate(LocalDate dBirthDate) {
		this.dBirthDate = dBirthDate;
	}

	
}
