package com.pagenguyen.elib.model;

import com.parse.ParseObject;

/**
 * Created by Page on 07/12/2015.
 */
public class Story {
	private String mContent;
	private String mTitle;
	private String[] mNewWords;

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		mContent = content;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String[] getNewWords() {
		return mNewWords;
	}

	public void setNewWords(String[] newWords) {
		mNewWords = newWords;
	}

	public void createParseStory() {
		// create new story object in Story class
		ParseObject story = new ParseObject(ParseConstants.CLASS_STORY);
		story.put(ParseConstants.STORY_CONTENT, mContent);
		story.put(ParseConstants.STORY_TITLE, mTitle);
		story.put(ParseConstants.STORY_NEW_WORDS, mNewWords);
		// save story to parse
		story.saveInBackground();
	}
}
