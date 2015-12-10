package com.pagenguyen.elib.model;

import android.support.v7.app.AppCompatActivity;

import com.pagenguyen.elib.R;

/**
 * Created by Can on 08-Dec-15.
 */
public class FillInBlankQuestion extends AppCompatActivity {
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
