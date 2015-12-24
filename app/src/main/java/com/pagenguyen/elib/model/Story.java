package com.pagenguyen.elib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Page on 07/12/2015.
 */
public class Story implements Parcelable {
	private String mId;
	private String mContent;
	private String mTitle;
	private String[] mNewWords;
	private MultipleChoiceExercise[] mMultipleChoiceExercises;
	private FillInBlankExercise[] mFillInBlankExercises;

	public Story(){ mTitle = ""; }

	public Story(Parcel in) {
		mId = in.readString();
		mContent = in.readString();
		mTitle = in.readString();
		mNewWords = in.createStringArray();
		mMultipleChoiceExercises = in.createTypedArray(MultipleChoiceExercise.CREATOR);
		mFillInBlankExercises = in.createTypedArray(FillInBlankExercise.CREATOR);
	}

	public static final Creator<Story> CREATOR = new Creator<Story>() {
		@Override
		public Story createFromParcel(Parcel in) {
			return new Story(in);
		}

		@Override
		public Story[] newArray(int size) {
			return new Story[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mId);
		dest.writeString(mContent);
		dest.writeString(mTitle);
		dest.writeStringArray(mNewWords);
		dest.writeTypedArray(mMultipleChoiceExercises, 0);
		dest.writeTypedArray(mFillInBlankExercises, 0);
	}

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
