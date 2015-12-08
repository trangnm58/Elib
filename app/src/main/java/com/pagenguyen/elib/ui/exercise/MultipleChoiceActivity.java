package com.pagenguyen.elib.ui.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.MultipleChoiceAdapter;
import com.pagenguyen.elib.model.MultipleChoiceExercise;
import com.pagenguyen.elib.model.MultipleChoiceQuestion;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MultipleChoiceActivity extends AppCompatActivity {

    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.scrollView) ScrollView mScrollView;
    @Bind(R.id.listQuestion) ListView mList;
    @Bind(R.id.title) TextView mExerciseTitle;

    MultipleChoiceExercise mExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        ButterKnife.bind(this);

        setupToolbar();

        fetchData();

        setExerciseTitle();

        setListQuestion();
    }

    // set data to mExercises:

    private void fetchData(){
        // demo:

        MultipleChoiceQuestion question;

        String[] option={"Anandi", "Sandick", "Kevin", "Julia"};
        question = new MultipleChoiceQuestion("What is your name", option, 2);

        MultipleChoiceQuestion[] questions = new MultipleChoiceQuestion[2];
        questions[0]=questions[1]=question;

        mExercises=new MultipleChoiceExercise("Chọn phương án đúng", questions);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setExerciseTitle() {
        String title=mExercises.getTitle();
        mExerciseTitle.setText(title);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case (R.id.action_done):{
                // call function to compute result:

                Intent intent = new Intent(MultipleChoiceActivity.this, ExerciseResultActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setListQuestion(){
        mList.setDividerHeight(0);
        MultipleChoiceAdapter mMCA=new MultipleChoiceAdapter(MultipleChoiceActivity.this, mExercises);
        mList.setAdapter(mMCA);
        setListViewHeightBasedOnChildren(mList);
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        MultipleChoiceAdapter listAdapter = (MultipleChoiceAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));

            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
