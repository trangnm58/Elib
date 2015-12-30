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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.model.ExerciseResult;
import com.pagenguyen.elib.model.FillInBlankExercise;
import com.pagenguyen.elib.database.ParseConstants;
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
    //Fill in blank layout
    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.loadingQuestionView) ProgressBar mLoadQuestion;
    @Bind(R.id.fibProgressBar) ProgressBar mFipProgressBar;
    @Bind(R.id.nextQuestionButton) Button mNextQuestion;
    @Bind(R.id.submitExerciseButton) Button mSubmitButton;

    @Bind(R.id.questionTextView) TextView mQuestionText;
    @Bind(R.id.answerInputField) EditText mAnswerInput;
    @Bind(R.id.resultTextView) TextView mKeyText;
    @Bind(R.id.fibProgressText) TextView mFipProgressText;

    @Bind(R.id.exerciseRelavLayout) RelativeLayout mRelativeLayout;

    //Exercise result layout
    @Bind(R.id.resultScoreText) TextView mScoreView;
    @Bind(R.id.resultStatusText) TextView mStatusView;
    @Bind(R.id.numberRightAnswer) TextView mRightAnswersView;
    @Bind(R.id.iconImageView) ImageView mStatusIcon;

    @Bind(R.id.fillInBlankLayout2) RelativeLayout mResultLayout;


    public FillInBlankExercise mExercise;
    public List<ParseObject> mQuestionList;
    public String mExerciseId;

    private int menuItemId;

    private int mQuestPos;
    private int mRightAnswers;

    private boolean mFinishExercise;

    private RelativeLayout.LayoutParams mParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_blanks);
        ButterKnife.bind(this);

        //hide result view
        mResultLayout.setVisibility(View.GONE);
        setExerciseLayout(View.GONE);
        mSubmitButton.setVisibility(View.GONE);
        mNextQuestion.setVisibility(View.GONE);

        mLoadQuestion.setVisibility(View.VISIBLE);

        setupToolbar();

        //position of 1st question
        mQuestPos = 0;
        //number of user's right answers
        mRightAnswers = 0;
        //doing exercise
        mFinishExercise = false;

        //set exercise content
        setExcerciseContent();

        setEditTextFocusListener();

        //set next question button action
        setNextButtonClick();

        //press enter on keyboard to submit answer
        submitExerciseByEnter();

        setSubmitButtonClick();

        mParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private void setExerciseLayout(int status) {
        mAnswerInput.setVisibility(status);
        mQuestionText.setVisibility(status);
        mFipProgressBar.setVisibility(status);
        mFipProgressText.setVisibility(status);
    }

    private void setSubmitButtonClick() {
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuestPos + 1 <= mQuestionList.size()) {
                    submitAnswers();
                }
            }
        });
    }

    private void submitExerciseByEnter() {
        mAnswerInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Perform action on key press
                    if (mQuestPos + 1 <= mQuestionList.size()) {
                        submitAnswers();
                    }
                    handled = true;
                }
                return handled;
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

                if(!mFinishExercise) { setDialog(); }
                else { backToHomePage(); }

                return true;
            }
            case (android.R.id.home): {
                menuItemId = R.id.home;

                if(!mFinishExercise) { setDialog(); }
                else { onBackPressed(); }

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
                        if (menuItemId == R.id.action_home) {
                            // Back to home page
                            backToHomePage();
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

    private void backToHomePage(){
        Intent intent = new Intent(FillInBlanksActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void submitAnswers() {
        mSubmitButton.setVisibility(View.GONE);

        //get user's answer
        String answers = mAnswerInput.getText().toString();

        String key = mQuestionList.get(mQuestPos).getString(ParseConstants.EXERCISE_KEY);
        boolean check = key.equals(answers);

        //disable edit text
        mAnswerInput.setEnabled(false);
        mAnswerInput.clearFocus();

        //increase position of question - go to next question
        mQuestPos++;

        if(check) {
            mNextQuestion.setEnabled(false);

            mRightAnswers++;
            //Right answer
            mAnswerInput.setTextColor(getResources().getColor(R.color.TextColorGreen));

            //auto change question after 1.5 seconds if user's answer is correct

            new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (mQuestPos + 1 <= mQuestionList.size()) { nextQuestion(); }
                        else {
                            showResult();
                        }
                    }
                }, 1000);
        } else {
            //Wrong answer
            mAnswerInput.setTextColor(getResources().getColor(R.color.TextColorRed));

            mKeyText.setVisibility(View.VISIBLE);

            //show next question button
            if(mQuestPos + 1 <= mQuestionList.size()){
                mNextQuestion.setEnabled(true);
            }
            else {

                new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            showResult();
                            }
                    }, 1000);
            }
        }
    }

    private void showResult(){
        //hide exercise view
        setExerciseLayout(View.GONE);
        mKeyText.setVisibility(View.GONE);
        //show result view
        mResultLayout.setVisibility(View.VISIBLE);

        int numQuestion = mQuestionList.size();

        float score = (float) mRightAnswers / numQuestion;

        ExerciseResult result = new ExerciseResult(score);

        String status = result.getStatus();
        int icon = result.getIcon();

        mScoreView.setText(result.getScore() + "%");
        mRightAnswersView.setText("Số câu đúng: " + mRightAnswers + "/" + numQuestion);
        mStatusIcon.setImageResource(icon);
        mStatusView.setText(status);

        //doing exercise
        mFinishExercise = true;

        mNextQuestion.setVisibility(View.GONE);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
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

                            setExerciseLayout(View.VISIBLE);
                            mSubmitButton.setVisibility(View.VISIBLE);
                            mNextQuestion.setVisibility(View.VISIBLE);

                            mFipProgressBar.setMax(mQuestionList.size());
                            //update progress bar
                            setFipProgressBar();
                        }
                    }
                });
            }
        });
    }

    private void setQuestionView(List<ParseObject> questions,int position){
        //hide next button when do exercise

        //set content for question view
        mQuestionText.setText(questions.get(position).getString(ParseConstants.EXERCISE_QUESTION));
        mAnswerInput.setVisibility(View.VISIBLE);
        mAnswerInput.setText("");

        mKeyText.setText(questions.get(position).getString(ParseConstants.EXERCISE_KEY));
        mKeyText.setVisibility(View.GONE);

        mSubmitButton.setVisibility(View.VISIBLE);
    }

    private void setFipProgressBar(){
        mFipProgressBar.setProgress(mQuestPos + 1);
        mFipProgressText.setText(mQuestPos + 1 + "/" + mQuestionList.size());
    }

    private void setEditTextFocusListener(){
        mAnswerInput.setFocusable(true);

        mAnswerInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                mFipProgressBar.setVisibility(View.GONE);
                                mFipProgressText.setVisibility(View.GONE);

                                mParams.setMargins(0, 0, 0, 30);
                                mRelativeLayout.setLayoutParams(mParams);
                            }
                        }, 200);
                } else {
                    new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                mFipProgressBar.setVisibility(View.VISIBLE);
                                mFipProgressText.setVisibility(View.VISIBLE);

                                mParams.setMargins(0, 0, 0, 150);
                                mRelativeLayout.setLayoutParams(mParams);
                            }
                        }, 300);
                }
            }
        });
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
        setQuestionView(mQuestionList, mQuestPos);

        //update progress bar
        setFipProgressBar();

        mAnswerInput.setEnabled(true);
        mAnswerInput.setTextColor(Color.BLACK);
    }
}
