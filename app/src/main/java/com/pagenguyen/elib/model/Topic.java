package com.pagenguyen.elib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Page on 19/12/2015.
 */
public class Topic implements Parcelable {
	private String mId;
	private String mTitle;
	private String[] mWords;
	private MultipleChoiceExercise[] mMultipleChoiceExercises;
	private FillInBlankExercise[] mFillInBlankExercises;

	public Topic(){ mTitle = ""; }

	public Topic(Parcel in) {
		mId = in.readString();
		mTitle = in.readString();
		mWords = in.createStringArray();
		mMultipleChoiceExercises = in.createTypedArray(MultipleChoiceExercise.CREATOR);
		mFillInBlankExercises = in.createTypedArray(FillInBlankExercise.CREATOR);
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

	public static final Creator<Topic> CREATOR = new Creator<Topic>() {
		@Override
		public Topic createFromParcel(Parcel in) {
			return new Topic(in);
		}

		@Override
		public Topic[] newArray(int size) {
			return new Topic[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mId);
		dest.writeString(mTitle);
		dest.writeStringArray(mWords);
		dest.writeTypedArray(mMultipleChoiceExercises, 0);
		dest.writeTypedArray(mFillInBlankExercises, 0);
	}
}
