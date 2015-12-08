package com.pagenguyen.elib.model;

import com.parse.ParseObject;

/**
 * Created by Can on 08/12/2015.
 */
public class MultipleChoiceExercise extends Exercise {
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

	@Override
	public void createParseExercise() {
        // TODO tạo dữ liệu trên Parse
	}

    // TODO nếu cần thì override cái toString nữa

//	public void createParseExercise() {
//		// create new exercise object in Exercise class
//		ParseObject exercise = new ParseObject(ParseConstants.CLASS_EXERCISE);
//		exercise.put(ParseConstants.EXERCISE_CONTENT, mContent);
//		// save exercise to parse
//		exercise.saveInBackground();
//	}

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

	public void createParseObject () {
		// convert to ParseObject
		ParseObject object = new ParseObject(ParseConstants.CLASS_MULTIPLE_CHOICE_EXERCISE);
		object.put(ParseConstants.EXERCISE_TITLE, mTitle);

		// save all question to Parse and save their ids in questionList
		String[] questionList = new String[mQuestionList.length];
		for (int i=0; i < mQuestionList.length; i++) {
			questionList[i] = mQuestionList[i].createParseObject();
			object.add(ParseConstants.EXERCISE_QUESTION_LIST, questionList[i]);
		}

		// save to Parse
		object.saveInBackground();
	}
}
