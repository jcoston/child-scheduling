package com.childsched;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.childsched.data.*;

public class Main {

	public static void main(String[] args) {
		
//		Child c1 = new Child();
//		Child c2 = new Child("Coston", "Jeremiah", "M", 2013, 8, 21);
//		c1.setsName("Joe");
//		
//		
//		c1.toConsole();
//		c2.toConsole();
		
		Activities acs = Activities.load();
		if(acs.getActivityWithID("Birthday") == null) {
			acs.addActivity(new Activity("Birthday", "Birthday", 1, 1440));
			acs.save();
		}

		byte x = 1;
		int[] a = {1, 0, 0, 1, 0, 0, 0, 0};
		int[] w = {0, 0, 0, 1, 1, 0};
		
		Appointment appt = new Appointment();
		appt.setbRecurs(true);
		appt.setdEndsOn(LocalDate.of(2020, 12, 31));
		appt.setdStartDateTime(LocalDateTime.of(2020, 8, 29, 10, 00));
		appt.setnFreqPeriods(x);
		appt.setnRecurFreq(Appointment.FREQ_WEEKLY);
		appt.setnFreqType(Appointment.FREQ_TYPE_WEEKDAY);
		appt.setnPossibleDays(a);
		appt.setnWeeksOfMonth(w);
		
<<<<<<< Upstream, based on origin/master
		ArrayList<LocalDateTime> aldt = appt.getAppointmentsBetween(LocalDateTime.of(2020, 8, 29, 11, 00),LocalDateTime.of(2020, 10, 31, 10, 00));
		for (LocalDateTime ldt:aldt) {
			System.out.println(ldt);
		}
=======
		LocalDateTime ldt = appt.getNextAppointmentAfter(LocalDateTime.of(2020, 8, 29, 10, 00));
		if(ldt == null)
			System.out.println("NULL!!!");
		else
			System.out.println(ldt.toString());
		
		
>>>>>>> 140d2c8 # WARNING: head commit changed in the meantime
	}
}
