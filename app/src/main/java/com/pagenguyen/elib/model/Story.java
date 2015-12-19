package com.pagenguyen.elib.model;

/**
 * Created by Page on 07/12/2015.
 */
public class Story {
	private String mId;
	private String mContent;
	private String mTitle;
	private String[] mNewWords;
	private MultipleChoiceExercise[] mMultipleChoiceExercises;
	private FillInBlankExercise[] mFillInBlankExercises;

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		mContent = content;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String[] getNewWords() {
		return mNewWords;
	}

	public void setNewWords(String[] newWords) {
		mNewWords = newWords;
	}

	public MultipleChoiceExercise[] getMultipleChoiceExercises() {
		return mMultipleChoiceExercises;
	}

	public void setMultipleChoiceExercises(MultipleChoiceExercise[] multipleChoiceExercises) {
		mMultipleChoiceExercises = multipleChoiceExercises;
	}

	public FillInBlankExercise[] getFillInBlankExercises() {
		return mFillInBlankExercises;
	}

	public void setFillInBlankExercises(FillInBlankExercise[] fillInBlankExercises) {
		mFillInBlankExercises = fillInBlankExercises;
	}
}
