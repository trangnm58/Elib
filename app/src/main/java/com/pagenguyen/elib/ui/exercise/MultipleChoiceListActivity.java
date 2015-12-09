package com.pagenguyen.elib.ui.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.OneTextviewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MultipleChoiceListActivity extends AppCompatActivity {
    @Bind(R.id.exerciseLView) ListView mListExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_list);
        ButterKnife.bind(this);

        setupToolbar();

        //set list of exercises view
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
        String[] mExercises = { "Bài tập trắc nghiệm"};

        OneTextviewAdapter adapter = new OneTextviewAdapter(MultipleChoiceListActivity.this,
                R.layout.item_one_textview,
                R.id.itemContent,
                mExercises);

        mListExercise.setAdapter(adapter);
    }

    private void setListItemClick() {
        mListExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView titleText = (TextView) view.findViewById(R.id.itemContent);
                String title = getIntent().getStringExtra("topic_name");

                Intent intent = new Intent(MultipleChoiceListActivity.this, MultipleChoiceActivity.class);
                intent.putExtra("topic_name", title);
                startActivity(intent);
            }
        });
    }

}
