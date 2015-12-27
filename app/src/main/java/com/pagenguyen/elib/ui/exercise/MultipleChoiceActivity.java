package com.pagenguyen.elib.ui.exercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.model.ExerciseResult;
import com.pagenguyen.elib.model.MultipleChoiceExercise;
import com.pagenguyen.elib.model.MultipleChoiceQuestion;
import com.pagenguyen.elib.database.ParseConstants;
import com.pagenguyen.elib.ui.main.HomeActivity;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MultipleChoiceActivity extends AppCompatActivity {
    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.loadingQuestionView) ProgressBar mLoadQuestion;
    @Bind(R.id.fibProgressBar) ProgressBar mFipProgressBar;
    @Bind(R.id.fibProgressText) TextView mFipProgressText;

    @Bind(R.id.nextQuestionButton) Button mNextQuestion;
    @Bind(R.id.questionTextView) TextView mQuestionText;
    @Bind(R.id.answer_1) CheckBox answer_1;
    @Bind(R.id.answer_2) CheckBox answer_2;
    @Bind(R.id.answer_3) CheckBox answer_3;
    @Bind(R.id.answer_4) CheckBox answer_4;
    @Bind(R.id.multipleChoiceLayout1) RelativeLayout mExerciseLayout;

    //Exercise result layout
    @Bind(R.id.resultScoreText) TextView mScoreView;
    @Bind(R.id.resultStatusText) TextView mStatusView;
    @Bind(R.id.numberRightAnswer) TextView mRightAnswersView;
    @Bind(R.id.iconImageView) ImageView mStatusIcon;

    @Bind(R.id.multipleChoiceLayout2) RelativeLayout mResultLayout;

    int black;
    int green;
    int red;

    MultipleChoiceExercise mExercises;

    private int menuItemId;
    private boolean finnish;

    private int questPos;
    private int rightAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        ButterKnife.bind(this);
        mResultLayout.setVisibility(View.GONE);
        finnish=false;

        mLoadQuestion.setVisibility(View.VISIBLE);
        mQuestionText.setVisibility(View.INVISIBLE);
        mNextQuestion.setVisibility(View.INVISIBLE);
        mFipProgressBar.setVisibility(View.INVISIBLE);

        answer_1.setVisibility(View.INVISIBLE);
        answer_2.setVisibility(View.INVISIBLE);
        answer_3.setVisibility(View.INVISIBLE);
        answer_4.setVisibility(View.INVISIBLE);

        black = getResources().getColor(R.color.TextColorBlack);
        green = getResources().getColor(R.color.TextColorGreen);
        red = getResources().getColor(R.color.TextColorRed);

        setupToolbar();

        fetchData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case (R.id.action_home):{
                if (!finnish) {
                    menuItemId = R.id.action_home;
                    setDialog();
                }
                else{
                    Intent intent = new Intent(MultipleChoiceActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                return true;
            }

            case (android.R.id.home): {
                if (!finnish) {
                    menuItemId = R.id.home;
                    setDialog();
                }
                else{
                    onBackPressed();
                }

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    // set data to mExercises:

    private void fetchData(){

        Intent intent = getIntent();
        final String mExerciseId = intent.getStringExtra("exercise_id");

        ParseQuery<ParseObject> question = ParseQuery.getQuery(ParseConstants.CLASS_MULTIPLE_CHOICE_EXERCISE);
        question.getInBackground(mExerciseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject exerciseObj, ParseException e) {
                ParseQuery<ParseObject> exercises = ParseQuery.getQuery(ParseConstants.CLASS_MULTIPLE_CHOICE_QUESTION);
                exercises.whereEqualTo(ParseConstants.RELATION_BELONG_TO, exerciseObj);

                exercises.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> object, ParseException e) {
                        MultipleChoiceQuestion[] questions = new MultipleChoiceQuestion[object.size()];

                        for (int i = 0; i < object.size(); i++) {
                            ParseObject data = object.get(i);

                            int tempKey = data.getInt(ParseConstants.EXERCISE_KEY);
                            String tempQuestion = data.getString(ParseConstants.EXERCISE_QUESTION);

                            ArrayList<String> tempOption = (ArrayList<String>) data.get(ParseConstants.EXERCISE_OPTION);
                            String[] listOption = new String[tempOption.size()];

                            for (int k = 0; k < tempOption.size(); k++) {
                                listOption[k] = tempOption.get(k);
                            }

                            MultipleChoiceQuestion temptemp = new MultipleChoiceQuestion(tempQuestion, listOption, tempKey);
                            questions[i] = temptemp;
                        }

                        mExercises = new MultipleChoiceExercise("Chọn phương án đúng: ", questions);

                        setFirstQuestion();

                        mLoadQuestion.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MultipleChoiceActivity.this);
        builder.setTitle(R.string.warn_title)
                .setMessage(R.string.warn_message)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (menuItemId == R.id.action_home) {
                            Intent intent = new Intent(MultipleChoiceActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        else if (menuItemId == R.id.home){
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

    private void setFipProgressBar(){
        mFipProgressBar.setVisibility(View.VISIBLE);
        mFipProgressBar.setProgress(questPos);
        mFipProgressText.setText(questPos + "/" + mExercises.getQuestionList().length);
    }

    // function to show key of exercise:

    private void showResult(){

        int userAnswer=-1;

        if (answer_1.isChecked()) {
            userAnswer=0;
        }

        else if (answer_2.isChecked()) {
            userAnswer=1;
        }

        else if (answer_3.isChecked()) {
            userAnswer=2;
        }

        else if (answer_4.isChecked()) {
            userAnswer=3;
        }

        answer_1.setClickable(false);
        answer_2.setClickable(false);
        answer_3.setClickable(false);
        answer_4.setClickable(false);

        if (mExercises.getQuestionList()[questPos-1].checkKey(userAnswer)){
            // normal: mark plus plus:

            rightAnswers++;

            // extend:
        }

        else{
            if (userAnswer == 0){
                answer_1.setTextColor(red);
            }
            else if (userAnswer == 1){
                answer_2.setTextColor(red);
            }
            else if (userAnswer == 2){
                answer_3.setTextColor(red);
            }
            else if (userAnswer == 3){
                answer_4.setTextColor(red);
            }
        }

        int key=mExercises.getQuestionList()[questPos-1].getKey();

        if (key == 0){
            answer_1.setTextColor(green);
        }
        else if (key == 1){
            answer_2.setTextColor(green);
        }
        else if (key == 2){
            answer_3.setTextColor(green);
        }
        else if (key == 3){
            answer_4.setTextColor(green);
        }

        mNextQuestion.setVisibility(View.VISIBLE);
    }

    private void setFirstQuestion(){
        questPos = 1;
        mNextQuestion.setVisibility(View.INVISIBLE);
        mFipProgressBar.setMax(mExercises.getQuestionList().length);
        setFipProgressBar();

        rightAnswers=0;

        MultipleChoiceQuestion here=mExercises.getQuestionList()[0];

        mQuestionText.setVisibility(View.VISIBLE);
        answer_1.setVisibility(View.VISIBLE);
        answer_2.setVisibility(View.VISIBLE);
        answer_3.setVisibility(View.VISIBLE);
        answer_4.setVisibility(View.VISIBLE);

        mQuestionText.setText(here.getQuestion()+"?");

        answer_1.setText(here.getOption()[0]);
        answer_2.setText(here.getOption()[1]);
        answer_3.setText(here.getOption()[2]);
        answer_4.setText(here.getOption()[3]);

        answer_1.setChecked(false);
        answer_2.setChecked(false);
        answer_3.setChecked(false);
        answer_4.setChecked(false);

        setOnlyOneSelected();
        setNextButtonClick();
    }

    private void setNextButtonClick() {
        mNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextQuestion();
            }
        });
    }

    private void setNextQuestion(){

        if (questPos == mExercises.getQuestionList().length){

            finnishExercise();
            return;

        }

        questPos++ ;
        mNextQuestion.setVisibility(View.INVISIBLE);
        setFipProgressBar();

        setOnlyOneSelected();

        MultipleChoiceQuestion here=mExercises.getQuestionList()[questPos-1];

        mQuestionText.setText(here.getQuestion()+"?");

        answer_1.setText(here.getOption()[0]);
        answer_2.setText(here.getOption()[1]);
        answer_3.setText(here.getOption()[2]);
        answer_4.setText(here.getOption()[3]);

        answer_1.setChecked(false);
        answer_2.setChecked(false);
        answer_3.setChecked(false);
        answer_4.setChecked(false);

        setNextButtonClick();
    }

    private void setOnlyOneSelected(){

        answer_1.setTextColor(black);
        answer_2.setTextColor(black);
        answer_3.setTextColor(black);
        answer_4.setTextColor(black);

        answer_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });

        answer_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });

        answer_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });

        answer_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });
    }

    private void finnishExercise(){
        //hide exercise view
        mExerciseLayout.setVisibility(View.GONE);
        //show result view
        mResultLayout.setVisibility(View.VISIBLE);

        int numQuestion = mExercises.getQuestionList().length;

        float score = (float) rightAnswers / numQuestion;

        ExerciseResult result = new ExerciseResult(score);

        String status = result.getStatus();
        int icon = result.getIcon();

        mScoreView.setText(result.getScore() + "%");
        mRightAnswersView.setText("Số câu đúng: " + rightAnswers + "/" + numQuestion);
        mStatusIcon.setImageResource(icon);
        mStatusView.setText(status);

        finnish=true;
    }
}
