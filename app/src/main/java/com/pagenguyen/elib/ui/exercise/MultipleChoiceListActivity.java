package com.pagenguyen.elib.ui.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.OneTextviewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MultipleChoiceListActivity extends AppCompatActivity {
    @Bind(R.id.exerciseLView) ListView mListExercise;
    @Bind(R.id.loadExerciseListView) ProgressBar mLoadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_list);
        ButterKnife.bind(this);

        mLoadList.setVisibility(View.VISIBLE);

        setupToolbar();

        //set list of exercises view
        setExerciseListView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (android.R.id.home): {
                onBackPressed();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setExerciseListView() {
        String[] mExercises = { "Bài tập trắc nghiệm 1","Bài tập trắc nghiệm 2","Bài tập trắc nghiệm 3","Bài tập trắc nghiệm 4","Bài tập trắc nghiệm 5" };

        OneTextviewAdapter adapter = new OneTextviewAdapter(MultipleChoiceListActivity.this,
                R.layout.item_one_textview,
                R.id.itemContent,
                mExercises);

        mListExercise.setAdapter(adapter);

        setListItemClick();

        mLoadList.setVisibility(View.GONE);

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
