package com.pagenguyen.elib.ui.stories;

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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StoryListActivity extends AppCompatActivity {
    @Bind(R.id.storyListView) ListView mListStories;
    @Bind(R.id.loadStoryListView) ProgressBar mLoadList;

    public Intent mIntent;
    public StoryAdapter mAdapter;
    public List<ParseObject> mStoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);
        ButterKnife.bind(this);

        mLoadList.setVisibility(View.VISIBLE);

        setupToolbar();
        getStoryList();

        //setStoryListView();
        setListItemClick();
    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void getStoryList() {
        //get Story Array from database
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_STORY);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    mStoryList = object;
                    setStoryListView(mStoryList);
                } else {

                }
            }
        });
    }

    private void setStoryListView(List<ParseObject> stories){
        mAdapter = new StoryAdapter(StoryListActivity.this,
                R.layout.item_one_textview,
                stories);

        mListStories.setAdapter(mAdapter);

        mLoadList.setVisibility(View.GONE);
    }

    private void setListItemClick() {
        mListStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String storyId = mStoryList.get(position).getObjectId();

                Intent intent = new Intent(StoryListActivity.this, StoryContentActivity.class);
                intent.putExtra("story_id", storyId);
                startActivity(intent);
            }
        });
    }

}
