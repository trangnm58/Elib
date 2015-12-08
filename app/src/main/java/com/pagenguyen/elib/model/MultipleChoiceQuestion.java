package com.pagenguyen.elib.model;

import com.parse.ParseObject;

/**
 * Created by Can on 08-Dec-15.
 */
public class MultipleChoiceQuestion {
    private String mQuestion;
    private String[] mOption;
    private int mKey; // key là 0, 1, 2, 3... tương ứng với đáp án đúng trong option

    public MultipleChoiceQuestion() {
        mQuestion = "";
        mKey = -1;
    }

    public MultipleChoiceQuestion(String question, String[] option, int key) {
        mQuestion = question;
        mOption = option;
        mKey = key;
    }

    public boolean checkKey(int key){
        // TODO Định nghĩa hàm chấm điểm ở đây nhé Duy
        return mKey == key;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String[] getOption() {
        return mOption;
    }

    public void setOption(String[] option) {
        mOption = option;
    }

    public int getKey() {
        return mKey;
    }

    public void setKey(int key) {
        mKey = key;
    }

    public String createParseObject () {
        // convert to ParseObject
        ParseObject object = new ParseObject(ParseConstants.CLASS_MULTIPLE_CHOICE_QUESTION);
        object.put(ParseConstants.EXERCISE_QUESTION, mQuestion);
        object.put(ParseConstants.EXERCISE_KEY, mKey);
	    for (int i=0; i < mOption.length; i++) {
		    object.add(ParseConstants.EXERCISE_OPTION, mOption[i]);
	    }
	    // save to Parse
	    object.saveInBackground();
	    // return id
	    return object.getObjectId();
    }
}
