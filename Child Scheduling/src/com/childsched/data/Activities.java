package com.childsched.data;

import java.io.*;
import java.util.ArrayList;

public class Activities implements Serializable {
	
	/**
	 * Version 1
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	//******************************//
	//      Serialization           //
	//******************************//
	public void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream("Activities.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in Activities.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public static Activities load() {
		try {
			FileInputStream fileIn = new FileInputStream("Activities.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Activities ac = (Activities) in.readObject();
			in.close();
			fileIn.close();
			System.out.printf("Serialized Activities have been loaded");
			return ac;
		} catch (IOException | ClassNotFoundException i) {
			i.printStackTrace();
			return new Activities();
		}
	}
	

}