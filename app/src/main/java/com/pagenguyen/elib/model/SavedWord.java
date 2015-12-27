package com.pagenguyen.elib.model;

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
	}
}
