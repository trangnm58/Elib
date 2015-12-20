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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.model.MultipleChoiceExercise;
import com.pagenguyen.elib.model.MultipleChoiceQuestion;
import com.pagenguyen.elib.database.ParseConstants;
import com.pagenguyen.elib.ui.main.HomeActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MultipleChoiceActivity extends AppCompatActivity {
    @Bind(R.id.exerciseTitleView) TextView mExerciseTitle;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.loadingQuestionView) ProgressBar mLoadQuestion;
    @Bind(R.id.nextQuestionButton) Button mNextQuestion;
    @Bind(R.id.questionTextView) TextView mQuestionText;
    @Bind(R.id.answer_1) CheckBox answer_1;
    @Bind(R.id.answer_2) CheckBox answer_2;
    @Bind(R.id.answer_3) CheckBox answer_3;
    @Bind(R.id.answer_4) CheckBox answer_4;

    String title;

    MultipleChoiceExercise mExercises;

    public static Menu mMultipleChoiceMenu;
    private int menuItemId;
    private boolean finnish;

    private int questPos;
    private int rightAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        ButterKnife.bind(this);

        mLoadQuestion.setVisibility(View.VISIBLE);
        mQuestionText.setVisibility(View.INVISIBLE);
        mNextQuestion.setVisibility(View.INVISIBLE);

        answer_1.setVisibility(View.INVISIBLE);
        answer_2.setVisibility(View.INVISIBLE);
        answer_3.setVisibility(View.INVISIBLE);
        answer_4.setVisibility(View.INVISIBLE);

        setupToolbar();

        title = getIntent().getStringExtra("topic_name");

        fetchData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);
        mMultipleChoiceMenu=menu;

        menu.getItem(0).setVisible(false);
        menu.getItem(0).setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case (R.id.action_done):{
                menuItemId = R.id.action_done;
                showResult();
                return true;
            }

            case (R.id.action_home):{
                menuItemId = R.id.action_home;
                setDialog();

                return true;
            }

            case (android.R.id.home): {
                onBackPressed();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    // set data to mExercises:

    private void fetchData(){

        // official:

        if (!title.equals("Giáo dục") && !title.equals("Gia đình")){
            title="Giáo Dục";
        }

        final String type=title;

        mExercises = null;

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_TOPIC);
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null && objects.size() != 0) {
                    ParseObject there = null;

                    boolean found = false;

                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject here = objects.get(i);
                        String title = here.getString(ParseConstants.TITLE);

                        if (title.toLowerCase().equals(type.toLowerCase())) {
                            there = here;
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        ParseQuery<ParseObject> exercises = ParseQuery.getQuery(ParseConstants.CLASS_MULTIPLE_CHOICE_EXERCISE);
                        exercises.whereEqualTo(ParseConstants.RELATION_BELONG_TO, there);

                        exercises.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (e == null && list.size() != 0) {
                                    ParseObject there = list.get(0);

                                    ParseQuery<ParseObject> question = ParseQuery.getQuery(ParseConstants.CLASS_MULTIPLE_CHOICE_QUESTION);
                                    question.whereEqualTo(ParseConstants.RELATION_BELONG_TO, there);

                                    question.findInBackground(new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> list, ParseException e) {
                                            if (e == null && list.size() != 0) {

                                                MultipleChoiceQuestion[] questions = new MultipleChoiceQuestion[list.size()];

                                                for (int i = 0; i < list.size(); i++) {
                                                    ParseObject data = list.get(i);

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

                                                setExerciseTitle();

                                                setFirstQuestion();

                                                mMultipleChoiceMenu.getItem(0).setVisible(true);
                                                mMultipleChoiceMenu.getItem(0).setEnabled(true);

                                                mLoadQuestion.setVisibility(View.GONE);


                                            } else {

                                            }
                                        }
                                    });

                                } else {

                                }
                            }
                        });
                    } else {

                    }

                } else {

                }
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setExerciseTitle() {
        mExerciseTitle.setText(title);
    }

    private void setDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MultipleChoiceActivity.this);
        builder.setTitle(R.string.warn_title)
                .setMessage(R.string.warn_message)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (menuItemId != R.id.action_done) {

                            Intent intent = new Intent(MultipleChoiceActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
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

    // function to show key of exercise:

    private void showResult(){
        mMultipleChoiceMenu.getItem(0).setEnabled(false);

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
                answer_1.setTextColor(Color.RED);
            }
            else if (userAnswer == 1){
                answer_2.setTextColor(Color.RED);
            }
            else if (userAnswer == 2){
                answer_3.setTextColor(Color.RED);
            }
            else if (userAnswer == 3){
                answer_4.setTextColor(Color.RED);
            }
        }

        int key=mExercises.getQuestionList()[questPos-1].getKey();

        if (key == 0){
            answer_1.setTextColor(Color.BLUE);
        }
        else if (key == 1){
            answer_2.setTextColor(Color.BLUE);
        }
        else if (key == 2){
            answer_3.setTextColor(Color.BLUE);
        }
        else if (key == 3){
            answer_4.setTextColor(Color.BLUE);
        }

        mNextQuestion.setVisibility(View.VISIBLE);
    }

    private void setFirstQuestion(){
        mNextQuestion.setVisibility(View.INVISIBLE);

        rightAnswers=0;
        questPos = 1;

        title="Câu " + questPos +": Chọn phương án đúng";

        setExerciseTitle();

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
        mNextQuestion.setVisibility(View.INVISIBLE);
        mMultipleChoiceMenu.getItem(0).setEnabled(true);

        if (questPos == mExercises.getQuestionList().length){

            finnishExercise();
            return;

        }

        setOnlyOneSelected();

        questPos++ ;

        title="Câu " + questPos +": Chọn phương án đúng";

        setExerciseTitle();

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

        answer_1.setTextColor(Color.BLACK);
        answer_2.setTextColor(Color.BLACK);
        answer_3.setTextColor(Color.BLACK);
        answer_4.setTextColor(Color.BLACK);


        answer_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer_2.setChecked(false);
                answer_3.setChecked(false);
                answer_4.setChecked(false);
            }
        });

        answer_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer_1.setChecked(false);
                answer_3.setChecked(false);
                answer_4.setChecked(false);
            }
        });

        answer_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer_2.setChecked(false);
                answer_1.setChecked(false);
                answer_4.setChecked(false);
            }
        });

        answer_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer_2.setChecked(false);
                answer_3.setChecked(false);
                answer_1.setChecked(false);
            }
        });
    }

    private void finnishExercise(){
        Intent intent = new Intent(MultipleChoiceActivity.this, ExerciseResultActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("right_answers", rightAnswers);
        intent.putExtra("num_questions", mExercises.getQuestionList().length);
        startActivity(intent);
    }
}
