package com.pagenguyen.elib.model;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.pagenguyen.elib.ui.exercise.ExerciseListActivity;
import com.pagenguyen.elib.ui.exercise.FillInBlanksActivity;

/**
 * Created by Can on 08/12/2015.
 */
public class FillInBlankExercise implements Parcelable {
	private String mId;
	private String mTitle;
	private FillInBlankQuestion[] mQuestionList;

	public FillInBlankExercise() {
		this.mTitle = "";
	}

	protected FillInBlankExercise(Parcel in) {
		mId = in.readString();
		mTitle = in.readString();
	}

	public static final Creator<FillInBlankExercise> CREATOR = new Creator<FillInBlankExercise>() {
		@Override
		public FillInBlankExercise createFromParcel(Parcel in) {
			return new FillInBlankExercise(in);
		}

		@Override
		public FillInBlankExercise[] newArray(int size) {
			return new FillInBlankExercise[size];
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
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public FillInBlankExercise(String title) {
		this.mTitle = title;
	}

    public FillInBlankExercise(String title, FillInBlankQuestion[] questionList) {
        mTitle = title;
        mQuestionList = questionList;
    }

   /* @Override
	public void doExercise() {
        // TODO đến trang làm bài tập

	}*/

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
