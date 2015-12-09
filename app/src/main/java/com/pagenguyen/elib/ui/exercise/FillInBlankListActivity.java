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

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.StoryAdapter;
import com.pagenguyen.elib.model.ParseConstants;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FillInBlankListActivity extends AppCompatActivity {
    @Bind(R.id.exerciseListView) ListView mListExercise;
    @Bind(R.id.loadExerciseListView) ProgressBar mLoadList;

    public List<ParseObject> mExerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        ButterKnife.bind(this);

        mLoadList.setVisibility(View.VISIBLE);

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
        Intent intent = getIntent();
        String storyId = intent.getStringExtra("story_id");

        //get current story by Id
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_STORY);
        query.getInBackground(storyId, new GetCallback<ParseObject>() {
            public void done(ParseObject storyObj, ParseException e) {
                if (e == null) {
                    //get exercises of current story
                    ParseQuery<ParseObject> exercises = ParseQuery.getQuery(ParseConstants.CLASS_FILL_IN_BLANK_EXERCISE);
                    exercises.whereEqualTo("belongTo", storyObj);

                    exercises.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> object, ParseException e) {
                            if (e == null) {
                                mExerciseList = object;
                                setStoryListView(mExerciseList);
                            } else {}
                        }
                    });
                } else {}
            }
        });
    }

    private void setStoryListView(List<ParseObject> exercises){
        StoryAdapter adapter = new StoryAdapter(FillInBlankListActivity.this,
                R.layout.item_one_textview,
                exercises);

        mListExercise.setAdapter(adapter);

        mLoadList.setVisibility(View.GONE);
    }

    private void setListItemClick() {
        mListExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String exerciseId = mExerciseList.get(position).getObjectId();

                Intent intent = new Intent(FillInBlankListActivity.this, FillInBlanksActivity.class);
                intent.putExtra("exercise_id", exerciseId);
                startActivity(intent);
            }
        });
    }
}