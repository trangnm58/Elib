package com.pagenguyen.elib.model;

import com.pagenguyen.elib.database.ParseConstants;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Page on 26/12/2015.
 */
public class SavedWord {
	private String mWord;
	private String mMeaning;

	public String getWord() {
		return mWord;
	}

	public void setWord(String word) {
		mWord = word;
	}

	public String getMeaning() {
		return mMeaning;
	}

	public void setMeaning(String meaning) {
		mMeaning = meaning;
	}

	public void createParseObject () {
		// convert to ParseObject
		ParseObject object = new ParseObject(ParseConstants.CLASS_SAVED_WORD);
		object.put(ParseConstants.SAVED_WORD, mWord);
		object.put(ParseConstants.SAVED_WORD_MEANING, mMeaning);
		object.put(ParseConstants.SAVED_WORD_USER, ParseUser.getCurrentUser());

		// save to Parse
		object.saveInBackground();
	}
}
