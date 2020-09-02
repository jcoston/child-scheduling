package com.childsched.data;

import java.util.ArrayList;

public class Activities {
	
	private ArrayList<Activity> alActivities;
	
	public Activities() {
		alActivities = new ArrayList<Activity>();
	}
	
	public void addActivity(Activity act) {
		alActivities.add(act);
	}
	
	public void removeActivity(Activity act) {
		alActivities.remove(act);
	}
	
	public void removeActivityAtIndex(int ndx) {
		alActivities.remove(ndx);
	}
	
	public ArrayList<Activity> getAllActivities() {
		return alActivities;
	}
	
	public Activity getActivityAtIndex(int ndx) {
		return alActivities.get(ndx);
	}
	
	public Activity getActivityWithID(String ID) {
		return null;
	}
	

}