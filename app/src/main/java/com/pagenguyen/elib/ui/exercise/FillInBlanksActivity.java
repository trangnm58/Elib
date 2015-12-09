package com.pagenguyen.elib.ui.exercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.FillInBlankAdapter;
import com.pagenguyen.elib.model.FillInBlankExercise;
import com.pagenguyen.elib.model.ParseConstants;
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
    @Bind(R.id.questionListView) ListView mQuestionListView;
    @Bind(R.id.exerciseStatusView) TextView mStatus;
    @Bind(R.id.exerciseScoreView) TextView mScore;

    public FillInBlankAdapter mAdapter;
    public FillInBlankExercise mExercise;
    public List<ParseObject> mQuestionList;
    public String mExerciseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_blanks);
        ButterKnife.bind(this);

        //hide score and status view
        mScore.setVisibility(View.GONE);
        mStatus.setVisibility(View.GONE);

        setupToolbar();

        //set exercise's title
        setExerciseTitle();

        //set exercise content
        setExcerciseContent();
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
            case (R.id.action_done):{
                //Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(FillInBlanksActivity.this);
                builder.setTitle(R.string.warn_title)
                        .setMessage(R.string.warn_message)
                        .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Done and submit user's answers
                                submitAnswers();
                            }
                        })
                        .setNegativeButton(R.string.cancel_button, null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void submitAnswers() {
        String[] uAnswers = mAdapter.getUAnswers();

        for(String s:uAnswers){

        }

        mScore.setText("50%");
        mStatus.setText(R.string.bad_result_status);

        mScore.setVisibility(View.VISIBLE);
        mStatus.setVisibility(View.VISIBLE);
    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setExerciseTitle() {
        mExercise = new FillInBlankExercise("Điền từ vào chỗ trống");
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
                            setmQuestionListView(mQuestionList);
                        } else {}
                    }
                });
            }
        });
    }

    private void setmQuestionListView(List<ParseObject> questions){
        mAdapter = new FillInBlankAdapter(FillInBlanksActivity.this,
                R.layout.fill_in_blanks_item,
                questions);

        mQuestionListView.setAdapter(mAdapter);
    }
}
