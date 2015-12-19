package com.pagenguyen.elib.model;

/**
 * Created by Page on 19/12/2015.
 */
public class Topic {
	private String mId;
	private String mTitle;
	private String[] mWords;
	private MultipleChoiceExercise[] mMultipleChoiceExercises;
	private FillInBlankExercise[] mFillInBlankExercises;

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
		mTitle = title;
	}

	public String[] getWords() {
		return mWords;
	}

	public void setWords(String[] words) {
		this.mWords = words;
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
