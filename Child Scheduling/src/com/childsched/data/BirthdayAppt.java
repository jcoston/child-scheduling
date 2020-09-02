package com.childsched.data;

import java.time.*;

public class BirthdayAppt extends Appointment {

	public BirthdayAppt() {
	}

	public BirthdayAppt(String actId, String location) {
		super(actId, location);
	}
	
	public BirthdayAppt(Child c) {
		LocalDate ld = c.getdBirthDate();
		this.setbRecurs(true);
		this.setdEndsOn(LocalDate.of(2199, 12, 31));
		this.setdStartDateTime(LocalDateTime.of(ld, LocalTime.MIN));
		this.setnDuration((short)1440);  			// minutes in a whole day
		this.setnFreqPeriods((byte)1);
		this.setnFreqType(FREQ_TYPE_DAY_OF_MONTH);
		this.setnRecurFreq(FREQ_YEARLY);
		this.setsActivityId("Birthday");
		this.setsLocation("Home");
	}

}
