package com.childsched.data;

import java.io.Serializable;

public class Activity implements Serializable {
	
	private static final long serialVersionUID = 191208118325126075L;
	private String sID = "";
	private String sName = "";
	private int    nColor = 0;
	private int    nNormalDuration = 0;  // minutes

	//***********************//
	//      Constructors     //
	//***********************//
	public Activity() {
		
	}
	
	public Activity(String id, String name, int color, int dur) {
		sID = id;
		sName = name;
		nColor = color;
		nNormalDuration = dur;
	}

	//***********************//
	//  Getters and Setters  //
	//***********************//
	public String getsID() {
		return sID;
	}
	
	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public int getnColor() {
		return nColor;
	}

	public void setnColor(int nColor) {
		this.nColor = nColor;
	}

	public int getnNormalDuration() {
		return nNormalDuration;
	}

	public void setnNormalDuration(int nNormalDuration) {
		this.nNormalDuration = nNormalDuration;
	}
	

}
