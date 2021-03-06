package com.childsched.data;

import java.time.*;
import java.util.ArrayList;

public class Appointment implements Serializable {
	//---------------------------------------------------// 
	//      CONSTANTS                                    //
	//---------------------------------------------------// 

	// Recurring Frequencies that we support
	public static final byte FREQ_DAILY   = 0;
	public static final byte FREQ_WEEKLY  = 1;
	public static final byte FREQ_MONTHLY = 2;
	public static final byte FREQ_YEARLY  = 3;
	
	// Frequency Types
	public static final byte FREQ_TYPE_WEEKDAY       = 0;
	public static final byte FREQ_TYPE_DAY_OF_MONTH  = 1;
	
	// Week Numbers (Used if Frequency is monthly and type is WeekDay or 
	//					  if Frequency is Yearly and week/day are specified
	public static final byte WEEK_FIRST   = 1;
	public static final byte WEEK_SECOND  = 2;
	public static final byte WEEK_THIRD   = 3;
	public static final byte WEEK_FOURTH  = 4;
	
	//------------------------------------------------------//
	//     Variables                                        //
	//------------------------------------------------------//
	private String sActivityId = "";
	private String sLocation = "";
	private boolean bRecurs = false;

	// non-recurring variables
	private LocalDateTime 	dStartDateTime = LocalDateTime.of(1900, 01, 01, 0, 0);
	private short 			nDuration = 0;   // only greater than 0 if diff from Activity's
	
	//----------------------------//
	// Recurring Variables        //
	//----------------------------//
	private LocalDate dEndsOn = LocalDate.of(1900, 01, 01);
	
	private byte nRecurFreq;		// See FREQ_* Constants above
	private byte nFreqPeriods  = 1; // How many RecurFreqs between appts.
	private byte nFreqType     = 0; // See FREQ_TYPE_* Constants above
	
	// in both of the following arrays, the zero indexed element is not used. 
	
	private int[] nWeeksOfMonth = {0,0,0,0,0,0};	// Weeks of Month of Appointments
												// Values of 0 = no Appt or 1 = Appt
												// Each Element corresponds to a week
												// of the month
									
	private int[] nPossibleDays  = {0,0,0,0,0,0,0,0}; 	// Weekdays of Appointments
														// Values of 0=no Appt or 1 = Appt 
								  						// Each Element corresponds to a day
														// of the week
	
	//-----------------------------//
	//    Constructor(s)           //
	//-----------------------------//
	public Appointment() {
	}
	
	public Appointment(String actId, String location) {
		this.sActivityId = actId;
		this.sLocation = location;
	}

	//-----------------------------// 
	//  Class Methods              //
	//-----------------------------//

	public LocalDateTime getNextAppointmentAfter(LocalDateTime from) {
		
		// if no from date was passed, set it to now. 
		if(from == null) {
			from = LocalDateTime.now();
		}
		// Do we recur?
		if(!bRecurs) {
			// If not, is our Appt date/time >= the from date/time?
			// if so, return our Appt Start Date/Time
			if(dStartDateTime.isAfter(from)) {
				return dStartDateTime;
				// Shouldn't this only occur if dStartDateTime is after "from" AND before "to"?
			}
			return null;
		} else {
			// If we do recur, 
			// Is the from date after our end date?
			// if so, return null
			LocalDateTime ldtEndsOn = dEndsOn.atStartOfDay();
			if(from.isAfter(ldtEndsOn)) {
				return null;
			}
			// if not, calculate (from the from date) our next Appt Date and return it.
			return calcNextAppointmentAfter(from);
		}
	}
	
	private LocalDateTime calcNextAppointmentAfter(LocalDateTime from) {
		LocalDateTime test = dStartDateTime;  // date/time we'l use to iterate thru appts
		
		// loop thru iterations until we get past the test date
		while(test.isBefore(from) || test.isEqual(from)) {
			test = iterateAppointment(test,null);
			if(test == null)
				return null;
		}
		return test;
	}

	public ArrayList<LocalDateTime> getAppointmentsBetween(LocalDateTime from, LocalDateTime to) {
		//TODO write pseudo code of how this method should work
		// if no from date was passed, set it to now. 
		if(from == null) {
			from = LocalDateTime.now();
		}
		// if no to date was passed, set it to tomorrow.
		if(to == null) {
			to = LocalDateTime.now().plusDays(1);	
		}
		// Do we recur?
		if(!bRecurs) {
			// If not, is our Appt date/time >= the from date/time and <= the to date/time?
			// if so, return our Appt Start Date/Time
			if(dStartDateTime.isAfter(from) && dStartDateTime.isBefore(to)) {
				ArrayList<LocalDateTime> al = new ArrayList<LocalDateTime>();
				al.add(dStartDateTime);
				return al;
			}
			return null;
		} else {
		// If we do recur, 
		// Is the from date after our end date?
		// if so, return null
		// is the to date before the start date?
		// if so, return null
			LocalDateTime ldtEndsOn = dEndsOn.atStartOfDay();
			if(from.isAfter(ldtEndsOn) || to.isBefore(dStartDateTime)) {
				return null;
			}
		// if not, calculate the appointments between from and to.
			return calcAppointmentsBetween(from,to);
		}
	}

	private ArrayList<LocalDateTime> calcAppointmentsBetween(LocalDateTime from, LocalDateTime to) {
		// TODO write pseudo code of how this method should work 
		
		LocalDateTime test = dStartDateTime;  // date/time we'll use to iterate thru appts
		
		// loop thru iterations until we get past the test date
		while(test.isBefore(from)) {
			test = iterateAppointment(test,to);
			if(test == null)
				return null;
		}
		
		ArrayList<LocalDateTime> alTest = new ArrayList<LocalDateTime>();
		alTest.add(test);
		
		while((test.isAfter(from) || test.isEqual(from)) && 
				(test.isBefore(to) || test.isEqual(to))) {
			test = iterateAppointment(test,to);
			if(test == null) return alTest;
			alTest.add(test);
		}
		return alTest;
	}
	
	public boolean hasAppointmentOn(LocalDate tstDt) {
		LocalDateTime nxtOn = getNextAppointmentAfter(tstDt.atStartOfDay());
		if(nxtOn.toLocalDate().isEqual(tstDt))
			return true;
		return false;
	}
	
	// iterate from one appointment date and time to the next
	// Note that it's assumed that the passed in date and time 
	//is an actual appointment date and time
	private LocalDateTime iterateAppointment(LocalDateTime dt, LocalDateTime to) {
		LocalDateTime x = LocalDateTime.of(dEndsOn,  dStartDateTime.toLocalTime());
		LocalDateTime retDt;
		
		if(to == null) {
			to = x;
		else {
			if(to.isAfter(x))
				to = x;
		}
		
		switch(nRecurFreq) {
		case FREQ_DAILY:
			retDt = dt.plusDays((long)nFreqPeriods);
			break;
		case FREQ_WEEKLY:
			retDt = dt.plusWeeks((long)nFreqPeriods);
			break;
		case FREQ_MONTHLY:
			// if it's a day of month, no problem
			if(nFreqType == FREQ_TYPE_DAY_OF_MONTH)
				retDt = dt.plusMonths((long)nFreqPeriods);
			else {
				// not a day of month, so it's a more complicated situation.
				// Depending on selections, we might have more appts this month
				// so we'll check from the passed date first 
				ArrayList<LocalDateTime> alDt = getDaysOfMonthForWeekAndDay(dt);
				for(LocalDateTime xdt : alDt) {
					if(xdt.isAfter(dt) && xdt.toLocalDate().isBefore(dEndsOn)) {
						retDt = xdt;
					}
				}
				// if we get here, then we didn't find another appointment in the passed month
				// so we'll bump our frequency, move to the first of that month,  and try again
				LocalDateTime newDt = dt.plusMonths((long)nFreqPeriods);
				// get all appointments for the month
				alDt = getDaysOfMonthForWeekAndDay(newDt);
				// find the first one after our passed in date
				for(LocalDateTime xdt : alDt) {
					if(xdt.isAfter(dt) && xdt.toLocalDate().isBefore(dEndsOn)) {
						retDt = xdt;
					}
				}
			}
			// we just couldn't find one that fit. 
			retDt = null;
			break;
		case FREQ_YEARLY:
			// if it's a day of month, no problem
			if(nFreqType == FREQ_TYPE_DAY_OF_MONTH)
				retDt = dt.plusYears((long)nFreqPeriods);
			else {
				// not a day of month, so it's a more complicated.
				// Depending on selections, we might have more appts this month
				// so we'll check from the passed date first 
				ArrayList<LocalDateTime> alDt = getDaysOfMonthForWeekAndDay(dt);
				for(LocalDateTime xdt : alDt) {
					if(xdt.isAfter(dt) && xdt.toLocalDate().isBefore(dEndsOn)) {
						retDt = xdt;
					}
				}
				// if we get here, then we didn't find another appointment in the passed month
				// so we'll bump our frequency, move to the first of that month,  and try again
				LocalDateTime newDt = dt.plusYears((long)nFreqPeriods);
				// move to first of month
				newDt = newDt.minusDays(newDt.getDayOfMonth() - 1);
				// get all appointments for that month
				alDt = getDaysOfMonthForWeekAndDay(newDt);
				// find the first one after our passed in date
				for(LocalDateTime xdt : alDt) {
					if(xdt.isAfter(dt) && xdt.toLocalDate().isBefore(dEndsOn)) {
						retDt = xdt;
					}
				}
			}
			// we just couldn't find one that fit. 
			retDt = null;
			break;
		default:
			retDt = null;
		}
		if(retDt == null) {return null;}
		
		if(retDt.isBefore(to) || 
				retDt.isEqual(to))
			return retDt;
		else
			return null;
	}
	
	private ArrayList<LocalDateTime> getDaysOfMonthForWeekAndDay(LocalDateTime dSomeDayInMonth) {
		ArrayList<LocalDateTime> al = new ArrayList<LocalDateTime>();
		int nbrHits[] = {0,0,0,0,0,0,0,0};
		int dow = -1;
		Month mth;
		LocalDateTime testDt;
		
		LocalDateTime dFirst = dSomeDayInMonth.minusDays(dSomeDayInMonth.getDayOfMonth() - 1);
		mth = dSomeDayInMonth.getMonth();
		testDt = dFirst;
		
		while(testDt.getMonth() == mth) {
			dow = testDt.getDayOfWeek().getValue();
			nbrHits[dow] ++;
			// are we in a valid week?
			int x = nbrHits[dow];
			if(nWeeksOfMonth[x] == 1) {
				// is this a valid day within the week?
				if(nPossibleDays[dow] == 1) {
					al.add(testDt);
				}
			}
			testDt = testDt.plusDays(1);
		}
		return al;
	}
	
	//-----------------------------// 
	//  Getters/Setters            //
	//-----------------------------//

 	public String getsActivityId() {
		return sActivityId;
	}

	public void setsActivityId(String sActivityId) {
		this.sActivityId = sActivityId;
	}

	public String getsLocation() {
		return sLocation;
	}

	public void setsLocation(String sLocation) {
		this.sLocation = sLocation;
	}

	public boolean isbRecurs() {
		return bRecurs;
	}

	public void setbRecurs(boolean bRecurs) {
		this.bRecurs = bRecurs;
	}

	public LocalDateTime getdStartDateTime() {
		return dStartDateTime;
	}

	public void setdStartDateTime(LocalDateTime sdt) {
		this.dStartDateTime = sdt;
	}

	public short getnDuration() {
		return nDuration;
	}

	public void setnDuration(short nDuration) {
		this.nDuration = nDuration;
	}

	public LocalDate getdEndsOn() {
		return dEndsOn;
	}

	public void setdEndsOn(LocalDate dEndsOn) {
		this.dEndsOn = dEndsOn;
	}

	public byte getnRecurFreq() {
		return nRecurFreq;
	}

	public void setnRecurFreq(byte nRecurFreq) {
		this.nRecurFreq = nRecurFreq;
	}

	public byte getnFreqPeriods() {
		return nFreqPeriods;
	}

	public void setnFreqPeriods(byte nFreqPeriods) {
		this.nFreqPeriods = nFreqPeriods;
	}

	public byte getnFreqType() {
		return nFreqType;
	}

	public void setnFreqType(byte nFreqType) {
		this.nFreqType = nFreqType;
	}

	public int[] getnWeeksOfMonth() {
		return nWeeksOfMonth;
	}

	public void setnWeeksOfMonth(int[] wom) {
		this.nWeeksOfMonth = wom;
	}

	public int[] getnPossibleDays() {
		return nPossibleDays;
	}

	public void setnPossibleDays(int[] days) {
		nPossibleDays = days;
	}
}
