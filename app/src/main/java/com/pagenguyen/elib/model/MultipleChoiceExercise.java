package com.pagenguyen.elib.model;

/**
 * Created by Can on 08/12/2015.
 */
public class MultipleChoiceExercise extends Exercise {
	private String mId;
	private String mTitle;
	private MultipleChoiceQuestion[] mQuestionList;

	public MultipleChoiceExercise() {
		this.mTitle = "";
	}

	public MultipleChoiceExercise(String title) {
		this.mTitle = title;
	}

    public MultipleChoiceExercise(String title, MultipleChoiceQuestion[] questionList) {
        mTitle = title;
        mQuestionList = questionList;
    }

    @Override
	public void doExercise() {
        // TODO đến trang làm bài tập
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

    public MultipleChoiceQuestion[] getQuestionList() {
        return mQuestionList;
    }

    public void setQuestionList(MultipleChoiceQuestion[] questionList) {
        mQuestionList = questionList;
    }
}
