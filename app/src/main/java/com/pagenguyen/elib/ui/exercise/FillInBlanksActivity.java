package com.pagenguyen.elib.ui.exercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.model.FillInBlankExercise;
import com.pagenguyen.elib.database.ParseConstants;
import com.pagenguyen.elib.model.Story;
import com.pagenguyen.elib.ui.main.HomeActivity;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FillInBlanksActivity extends AppCompatActivity {
    @Bind(R.id.exerciseTitleView) TextView mExerciseTitle;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.loadingQuestionView) ProgressBar mLoadQuestion;
    @Bind(R.id.fibProgressBar) ProgressBar mFipProgressBar;
    @Bind(R.id.nextQuestionButton) Button mNextQuestion;

    @Bind(R.id.questionContentLayout) LinearLayout mQuestionContent;

    @Bind(R.id.questionTextView) TextView mQuestionText;
    @Bind(R.id.answerInputField) EditText mAnswerInput;
    @Bind(R.id.resultTextView) TextView mKeyText;
    @Bind(R.id.fibProgressText) TextView mFipProgressText;

    public FillInBlankExercise mExercise;
    public List<ParseObject> mQuestionList;
    public String mExerciseId;

    private int menuItemId;

    private int mQuestPos;
    private int mRightAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_blanks);
        ButterKnife.bind(this);

        mLoadQuestion.setVisibility(View.VISIBLE);
        mAnswerInput.setVisibility(View.GONE);
        mNextQuestion.setVisibility(View.GONE);

        setupToolbar();

        //position of 1st question
        mQuestPos = 0;
        //number of user's right answers
        mRightAnswers = 0;
        //set exercise's title
        setExerciseTitle(mQuestPos);

        //set exercise content
        setExcerciseContent();

        //set next question button action
        setNextButtonClick();

        //press enter on keyboard to submit answer
        submitExerciseByEnter();
    }

    private void submitExerciseByEnter() {
        //To receive a keyboard event, a View must have focus
        mAnswerInput.setFocusableInTouchMode(true);
        mAnswerInput.requestFocus();

        mAnswerInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    if (mQuestPos + 1 <= mQuestionList.size()) {
                        submitAnswers();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_home):{
                menuItemId = R.id.action_home;
                setDialog();
                return true;
            }
            case (android.R.id.home): {
                menuItemId = R.id.home;
                setDialog();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FillInBlanksActivity.this);
        builder.setTitle(R.string.warn_title)
                .setMessage(R.string.warn_message)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*if (menuItemId == R.id.action_done) {
                            //done and sumbit user's answers
                            submitAnswers();
                        } else */if (menuItemId == R.id.action_home) {
                            // Back to home page
                            Intent intent = new Intent(FillInBlanksActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            onBackPressed();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_button, null);

        AlertDialog dialog = builder.create();
        dialog.show();

        //set size for text of dialog's button
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(16);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(16);
    }

    private void submitAnswers() {
        //FillInBlanksActivity.mFillInBlanksMenu.getItem(0).setEnabled(false);

        //get user's answer
        String answers = mAnswerInput.getText().toString();

        String key = mQuestionList.get(mQuestPos).getString(ParseConstants.EXERCISE_KEY);
        boolean check = key.equals(answers);

        //disable edit text
        mAnswerInput.setEnabled(false);

        //increase position of question - go to next question
        mQuestPos++;

        //update progress bar
        setFipProgressBar();

        if(check) {
            mRightAnswers++;
            //Right answer
            mAnswerInput.setTextColor(Color.GREEN);

            //auto change question after 1.5 seconds if user's answer is correct

            new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (mQuestPos + 1 <= mQuestionList.size()) { nextQuestion(); }
                        else {
                            //move to result activity if user done all questions
                            Intent intent = new Intent(FillInBlanksActivity.this, ExerciseResultActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("right_answers", mRightAnswers);
                            intent.putExtra("num_questions", mQuestionList.size());
                            startActivity(intent);
                        }
                    }
                }, 1200);
        } else {
            //Wrong answer
            mAnswerInput.setTextColor(Color.RED);

            mKeyText.setVisibility(View.VISIBLE);

            //show next question button
            if(mQuestPos + 1 <= mQuestionList.size()){
                mNextQuestion.setVisibility(View.VISIBLE);
            }
            else {
                mNextQuestion.setVisibility(View.GONE);

                new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            //move to result activity if user done all questions
                            Intent intent = new Intent(FillInBlanksActivity.this, ExerciseResultActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("right_answers", mRightAnswers);
                            intent.putExtra("num_questions", mQuestionList.size());
                            startActivity(intent);
                            }
                    }, 1200);
            }
        }
    }


    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setExerciseTitle(int number) {
        number = number + 1;
        mExercise = new FillInBlankExercise("Câu " + number + ":");
        mExerciseTitle.setText(mExercise.getTitle());
    }

    private void setExcerciseContent() {
        Intent intent = getIntent();
        mExerciseId = intent.getStringExtra("exercise_id");

        //get current exercise by Id
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_FILL_IN_BLANK_EXERCISE);
        query.getInBackground(mExerciseId, new GetCallback<ParseObject>() {
            public void done(ParseObject exerciseObj, com.parse.ParseException e) {

                //get exercises of current story
                ParseQuery<ParseObject> exercises = ParseQuery.getQuery(ParseConstants.CLASS_FILL_IN_BLANK_QUESTION);
                exercises.whereEqualTo(ParseConstants.RELATION_BELONG_TO, exerciseObj);

                exercises.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> object, ParseException e) {
                        if (e == null) {
                            mQuestionList = object;

                            setQuestionView(mQuestionList, 0);

                            mLoadQuestion.setVisibility(View.GONE);

                            mFipProgressBar.setMax(mQuestionList.size());
                            //update progress bar
                            setFipProgressBar();
                        } else {
                        }
                    }
                });
            }
        });
    }

    private void setQuestionView(List<ParseObject> questions,int position){
        //set content for question view
        mQuestionText.setText(questions.get(position).getString(ParseConstants.EXERCISE_QUESTION));
        mAnswerInput.setVisibility(View.VISIBLE);
        mAnswerInput.setText("");

        mKeyText.setText(questions.get(position).getString(ParseConstants.EXERCISE_KEY));
        mKeyText.setVisibility(View.GONE);

        //hide next button when do exercise
        mNextQuestion.setVisibility(View.GONE);
    }

    private void setFipProgressBar(){
        mFipProgressBar.setProgress(mQuestPos);
        mFipProgressText.setText(mQuestPos + "/" + mQuestionList.size());
    }

    private void setNextButtonClick() {
        mNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
    }

    private void nextQuestion() {
        setExerciseTitle(mQuestPos);

        setQuestionView(mQuestionList, mQuestPos);

        mAnswerInput.setEnabled(true);
        mAnswerInput.setTextColor(Color.BLACK);
    }
}
