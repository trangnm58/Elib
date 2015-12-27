package com.pagenguyen.elib.model;

import com.pagenguyen.elib.database.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Page on 26/12/2015.
 */
public class SavedWord {
	private String mWord;
	private String mMeaning;

    public SavedWord(String word, String meaning) {
        mWord = word;
        mMeaning = meaning;
    }

    public SavedWord() {
        mWord = "";
        mMeaning = "";
    }

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
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_SAVED_WORD);
		query.whereEqualTo(ParseConstants.SAVED_WORD_USER, ParseUser.getCurrentUser());

		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> wordList, ParseException e) {
				if (e == null) {
					// check for duplicate
					boolean flag = true;
					for (int i = 0; i < wordList.size(); i++) {
						if (wordList.get(i).getString(ParseConstants.SAVED_WORD).equals(mWord)) {
							flag = false;
							break;
						}
					}
					if (flag) {
						// no duplicate
						// convert to ParseObject
						ParseObject object = new ParseObject(ParseConstants.CLASS_SAVED_WORD);
						object.put(ParseConstants.SAVED_WORD, mWord);
						object.put(ParseConstants.SAVED_WORD_MEANING, mMeaning);
						object.put(ParseConstants.SAVED_WORD_USER, ParseUser.getCurrentUser());

						// save to Parse
						object.saveInBackground();
					}
				}
			}
		});
	}
}
