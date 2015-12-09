package com.pagenguyen.elib.ui.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.OneTextviewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExerciseListActivity extends AppCompatActivity {
    @Bind(R.id.exerciseListView) ListView mListExercise;
    @Bind(R.id.loadExerciseListView) ProgressBar mLoadList;

    public Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        ButterKnife.bind(this);

        mLoadList.setVisibility(View.VISIBLE);

        setupToolbar();
        setExerciseListView();
        setListItemClick();
    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setExerciseListView() {
        String[] mExercises = { "Bài tập 1",
                "Bài tập 2",
                "Bài tập 3" };

        OneTextviewAdapter adapter = new OneTextviewAdapter(ExerciseListActivity.this,
                R.layout.item_one_textview,
                R.id.itemContent,
                mExercises);

        mListExercise.setAdapter(adapter);

        mLoadList.setVisibility(View.GONE);
    }

    private void setListItemClick() {
        mListExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView titleText = (TextView) view.findViewById(R.id.itemContent);
                String title = titleText.getText().toString();

                Intent intent = new Intent(ExerciseListActivity.this, FillInBlanksActivity.class);
                intent.putExtra("exercise_title", title);
                startActivity(intent);
            }
        });
    }
}
