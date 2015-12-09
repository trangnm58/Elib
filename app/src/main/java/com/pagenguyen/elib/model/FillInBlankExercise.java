package com.pagenguyen.elib.model;

/**
 * Created by Can on 08/12/2015.
 */
public class FillInBlankExercise extends Exercise {
	private String mTitle;
	private FillInBlankQuestion[] mQuestionList;

	public FillInBlankExercise() {
		this.mTitle = "";
	}

	public FillInBlankExercise(String title) {
		this.mTitle = title;
	}

    public FillInBlankExercise(String title, FillInBlankQuestion[] questionList) {
        mTitle = title;
        mQuestionList = questionList;
    }

    @Override
	public void doExercise() {
        // TODO đến trang làm bài tập
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

    public FillInBlankQuestion[] getQuestionList() {
        return mQuestionList;
    }

    public void setQuestionList(FillInBlankQuestion[] questionList) {
        mQuestionList = questionList;
    }
}
