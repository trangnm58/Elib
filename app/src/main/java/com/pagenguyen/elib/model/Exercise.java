package com.pagenguyen.elib.model;

/**
 * Created by Can on 08/12/2015.
 */
public class Exercise {
	// putExtra and create intent to exactly exercise page
	private String mTitle;
	private int mId;

	public String getTitle(){
		return mTitle;
	}

	public int getId(){
		return mId;
	}

	public void setTitle(String title){
		mTitle = title;
	}

	public void setId(int id){
		mId = id;
	}

	//abstract public void doExercise();
}
