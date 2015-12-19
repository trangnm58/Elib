package com.pagenguyen.elib.model;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Can on 08-Dec-15.
 */
public class FillInBlankQuestion extends AppCompatActivity {
    private String mId;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public boolean checkKey(String key){
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
}
