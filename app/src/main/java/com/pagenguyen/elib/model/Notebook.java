package com.pagenguyen.elib.model;

import com.pagenguyen.elib.database.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Page on 27/12/2015.
 */
public class Notebook {
	private List<ParseObject> mWordList;

	public List<ParseObject> getWordList() {
		return mWordList;
	}

	public void setWordList(List<ParseObject> wordList) {
		mWordList = wordList;
	}

	public void addWord(String word, String meaning) {
		// convert to ParseObject
		ParseObject object = new ParseObject(ParseConstants.CLASS_SAVED_WORD);
		object.put(ParseConstants.SAVED_WORD, word);
		object.put(ParseConstants.SAVED_WORD_MEANING, meaning);
		object.put(ParseConstants.SAVED_WORD_USER, ParseUser.getCurrentUser());

		// save to local database
		object.pinInBackground();
		// save to Parse
		object.saveInBackground();
	}

	public void deleteWord (int index) {
		// delete in Parse
		mWordList.get(index).deleteInBackground();
		// unpin from local database
		mWordList.get(index).unpinInBackground();
	}

	public void getWordList(FindCallback<ParseObject> callback) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_SAVED_WORD);
		query.whereEqualTo(ParseConstants.SAVED_WORD_USER, ParseUser.getCurrentUser());
		query.fromLocalDatastore();
		query.findInBackground(callback);
	}

	public void syncWithParse() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_SAVED_WORD);
		query.whereEqualTo(ParseConstants.SAVED_WORD_USER, ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> words, ParseException e) {
				if (e == null) {
					// delete current data in Parse
					for (ParseObject word : words) {
						word.deleteInBackground();
					}

					// save data in local database to Parse
					ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_SAVED_WORD);
					query.whereEqualTo(ParseConstants.SAVED_WORD_USER, ParseUser.getCurrentUser());
					query.fromLocalDatastore();
					query.findInBackground(new FindCallback<ParseObject>() {
						@Override
						public void done(List<ParseObject> words, ParseException e) {
							if (e == null) {
								// save each word to Parse
								for (ParseObject word : words) {
									word.saveInBackground();
								}
							}
						}
					});
				}
			}
		});
	}
}
