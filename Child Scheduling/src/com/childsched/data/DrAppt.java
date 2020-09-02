package com.childsched.data;

public class DrAppt extends Appointment {
	
	private String sDrName = "";
	
	
	public DrAppt() {
		// TODO Auto-generated constructor stub
	}
	
	public DrAppt(String doctorName) {
		sDrName = doctorName;
	}

	public DrAppt(String doctorName, String actId, String location) {
		super(actId, location);
		sDrName = doctorName;
	}

}
