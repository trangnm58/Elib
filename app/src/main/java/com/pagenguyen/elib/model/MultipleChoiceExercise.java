package com.pagenguyen.elib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Can on 08/12/2015.
 */
public class MultipleChoiceExercise implements Parcelable {
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

    /*@Override
	public void doExercise() {
        // TODO đến trang làm bài tập
	}*/

	protected MultipleChoiceExercise(Parcel in) {
		mId = in.readString();
		mTitle = in.readString();
	}

	public static final Creator<MultipleChoiceExercise> CREATOR = new Creator<MultipleChoiceExercise>() {
		@Override
		public MultipleChoiceExercise createFromParcel(Parcel in) {
			return new MultipleChoiceExercise(in);
		}

		@Override
		public MultipleChoiceExercise[] newArray(int size) {
			return new MultipleChoiceExercise[size];
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
