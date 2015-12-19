package com.pagenguyen.elib.model;

/**
 * Created by Can on 08-Dec-15.
 */
public class MultipleChoiceQuestion {
    private String mId;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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
}
