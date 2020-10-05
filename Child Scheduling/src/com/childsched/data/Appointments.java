package com.childsched.data;

import java.io.*;
import java.util.ArrayList;

public class Appointments implements Serializable {

		/**
		 * Version 1
		 */
		private static final long serialVersionUID = 1L;
		
		private ArrayList<Appointment> alAppts;
		
		public Appointments() {
			alAppts = new ArrayList<Appointment>();
		}
		
		public void addAppointment(Appointment act) {
			alAppts.add(act);
		}
		
		public void removeAppointment(Appointment act) {
			alAppts.remove(act);
		}
		
		public void removeAppointmentAtIndex(int ndx) {
			alAppts.remove(ndx);
		}
		
		public ArrayList<Appointment> getAllAppointments() {
			return alAppts;
		}
		
		public Appointment getAppointmentAtIndex(int ndx) {
			return alAppts.get(ndx);
		}
		
		public Appointment getAppointmentWithID(String ID) {
			return null;
		}

}
