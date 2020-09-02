package com.childsched.data;

public class DrAppt extends Appointment {
	
	private String sDrName = "";
	
	
	public DrAppt() {
	}
	
	public DrAppt(String doctorName) {
		sDrName = doctorName;
	}

	public DrAppt(String doctorName, String actId, String location) {
		super(actId, location);
		sDrName = doctorName;
	}

	//*******************************//
	//    Getters and Setters        //
	//*******************************//
	public String getsDrName() {
		return sDrName;
	}

	public void setsDrName(String sDrName) {
		this.sDrName = sDrName;
	}

}
