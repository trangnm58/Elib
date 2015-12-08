package com.pagenguyen.elib.model;

import com.parse.ParseObject;

/**
 * Created by Can on 08-Dec-15.
 */
public class FillInBlankQuestion {
    private String mQuestion;
    private String mKey;

    public FillInBlankQuestion() {
        mQuestion = "";
        mKey = "";
    }

    public FillInBlankQuestion(String question, String key) {
        mQuestion = question;
        mKey = key;
    }

    public boolean checkKey(String key){
        // TODO Định nghĩa hàm chấm điểm ở đây nhé Dũng
        return mKey.equals(key);
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String createParseObject () {
        // convert to ParseObject
        ParseObject object = new ParseObject(ParseConstants.CLASS_FILL_IN_BLANK_QUESTION);
        object.put(ParseConstants.EXERCISE_QUESTION, mQuestion);
        object.put(ParseConstants.EXERCISE_KEY, mKey);
        // save to Parse
        object.saveInBackground();
	    // return id
	    return object.getObjectId();
    }
}
