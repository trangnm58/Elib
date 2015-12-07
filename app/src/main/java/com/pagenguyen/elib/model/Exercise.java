package com.pagenguyen.elib.model;

import com.parse.ParseObject;

/**
 * Created by Page on 07/12/2015.
 */
public class Exercise {
	private String mContent;

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		mContent = content;
	}

	public void createParseStory() {
		// create new exercise object in Exercise class
		ParseObject exercise = new ParseObject(ParseConstants.CLASS_EXERCISE);
		exercise.put(ParseConstants.EXERCISE_CONTENT, mContent);
		// save exercise to parse
		exercise.saveInBackground();
	}
}
