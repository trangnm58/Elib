package com.pagenguyen.elib.database;

/**
 * Created by Page on 30/11/2015.
 */
public class ParseConstants {
	// class names
	public static final String CLASS_STORY = "Story";
	public static final String CLASS_TOPIC = "Topic";
	public static final String CLASS_FILL_IN_BLANK_EXERCISE = "FillInBlankExercise";
	public static final String CLASS_FILL_IN_BLANK_QUESTION = "FillInBlankQuestion";
	public static final String CLASS_MULTIPLE_CHOICE_EXERCISE = "MultipleChoiceExercise";
	public static final String CLASS_MULTIPLE_CHOICE_QUESTION = "MultipleChoiceQuestion";
	public static final String CLASS_SAVED_WORD = "SavedWord";

	// field names
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String NEW_WORDS = "newWords";

	public static final String EXERCISE_QUESTION = "question";
	public static final String EXERCISE_OPTION = "option";
	public static final String EXERCISE_KEY = "key";

	public static final String SAVED_WORD = "word";
	public static final String SAVED_WORD_MEANING = "meaning";
	public static final String SAVED_WORD_USER = "user";

	// relation
	public static final String RELATION_BELONG_TO = "belongTo";
}
