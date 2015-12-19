package com.pagenguyen.elib.ui.exercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.FillInBlankAdapter;
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
    @Bind(R.id.exerciseTitleView) TextView mExerciseTitle;
    @Bind(R.id.questionListView) ListView mQuestionListView;
    @Bind(R.id.exerciseScoreView) TextView mScore;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.loadingQuestionView) ProgressBar mLoadQuestion;

    @Bind(R.id.coordinatorLayout) CoordinatorLayout mCoordinatorLayout;

    public FillInBlankAdapter mAdapter;
    public FillInBlankExercise mExercise;
    public List<ParseObject> mQuestionList;
    public String mExerciseId;

    public static Menu mFillInBlanksMenu;
    private int menuItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_blanks);
        ButterKnife.bind(this);

        //hide score and status view
        mScore.setVisibility(View.GONE);

        mLoadQuestion.setVisibility(View.VISIBLE);

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
        mFillInBlanksMenu = menu;

        menu.getItem(0).setEnabled(false);

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
                menuItemId = R.id.action_done;
                setDialog();
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



    private void setDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FillInBlanksActivity.this);
        builder.setTitle(R.string.warn_title)
                .setMessage(R.string.warn_message)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(menuItemId == R.id.action_done){
                            //done and sumbit user's answers
                            submitAnswers();
                        } else {
                            // Back to home page
                            Intent intent = new Intent(FillInBlanksActivity.this, HomeActivity.class);
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

    private void submitAnswers() {
        String[] uAnswers = mAdapter.getUAnswers();

        int i=0;
        int rightAnswers = 0;
        for(String answers:uAnswers){
            String key = mQuestionList.get(i).getString(ParseConstants.EXERCISE_KEY);
            boolean check = key.equals(answers);
            EditText editText = (EditText) findViewById(i + 900);

            //disable all edit text
            editText.setEnabled(false);

            if(check){
                rightAnswers++;
                //Right answer
                editText.setTextColor(Color.GREEN);
            } else {
                //Wrong answer
                editText.setTextColor(Color.RED);

                //show key of question
                TextView keyView = (TextView) findViewById(i + 1000);
                keyView.setVisibility(View.VISIBLE);
            }

            i++;
        }

        float rateScore = ((float)rightAnswers)/i;

        //Toast.makeText(FillInBlanksActivity.this,rateScore+"",Toast.LENGTH_SHORT).show();
        ExerciseResult result = new ExerciseResult(rateScore);

        mScore.setText(result.getScore() + "%");

        //Set snack bar
        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, result.getStatus(), Snackbar.LENGTH_LONG)
                .setAction("OK", null)
                .setActionTextColor(R.color.TextColorWhite);

        //set background color
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.TextColorBlue));

        //set text view style
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.TextColorWhite));
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);

        snackbar.show();

        mScore.setVisibility(View.VISIBLE);
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
                            setQuestionListView(mQuestionList);

                            mLoadQuestion.setVisibility(View.GONE);
                        } else {
                        }
                    }
                });
            }
        });
    }

    private void setQuestionListView(List<ParseObject> questions){
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
