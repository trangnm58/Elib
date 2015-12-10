package com.pagenguyen.elib.ui.exercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.FillInBlankAdapter;
import com.pagenguyen.elib.model.ExerciseResult;
import com.pagenguyen.elib.model.FillInBlankExercise;
import com.pagenguyen.elib.model.ParseConstants;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FillInBlanksActivity extends AppCompatActivity {
    @Bind(R.id.exerciseTitleView) TextView mExerciseTitle;
    @Bind(R.id.questionListView) ListView mQuestionListView;
    @Bind(R.id.exerciseStatusView) TextView mStatus;
    @Bind(R.id.exerciseScoreView) TextView mScore;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;

    public FillInBlankAdapter mAdapter;
    public FillInBlankExercise mExercise;
    public List<ParseObject> mQuestionList;
    public String mExerciseId;

    public ActionMenuItemView mSubmitItem;

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

        int i=0;
        int rightAnswers = 0;
        for(String answers:uAnswers){
            String key = mQuestionList.get(i).getString(ParseConstants.EXERCISE_KEY);
            boolean check = key.equals(answers);
            EditText editText = (EditText) findViewById(i + 900);

            //show key of question
            TextView keyView = (TextView) findViewById(i + 1000);
            keyView.setVisibility(View.VISIBLE);

            //disable all edit text
            editText.setEnabled(false);

            if(check){
                rightAnswers++;
            } else {
                editText.setTextColor(Color.RED);
            }

            i++;
        }

        float rateScore = ((float)rightAnswers)/i;

        //Toast.makeText(FillInBlanksActivity.this,rateScore+"",Toast.LENGTH_SHORT).show();
        ExerciseResult result = new ExerciseResult(rateScore);

        /*Snackbar snackbar = Snackbar
                .make(R.layout.exercise_result_snack_bar, "Welcome to AndroidHive", Snackbar.LENGTH_LONG)
                .setAction("OK", )

        snackbar.show();*/

        mScore.setText(result.getScore() + "%");
        mStatus.setText(result.getStatus());

        mScore.setVisibility(View.VISIBLE);
        mStatus.setVisibility(View.VISIBLE);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
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
                        } else {
                        }
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
        getListViewSize(mQuestionListView);
    }

    private void getListViewSize(ListView listView) {
        FillInBlankAdapter myListAdapter = (FillInBlankAdapter) listView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,
                        AbsListView.LayoutParams.MATCH_PARENT));

            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        //setting listview item in adapter
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight*2 + 230 + (listView.getDividerHeight() * (myListAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
