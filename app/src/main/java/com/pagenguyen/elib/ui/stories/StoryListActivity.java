package com.pagenguyen.elib.ui.stories;

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

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.StoryAdapter;
import com.pagenguyen.elib.database.ParseConstants;
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
    @Bind(R.id.my_toolbar) Toolbar mToolbar;

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
        setSupportActionBar(mToolbar);
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

                    //set demo list view - oflines
                    //setDemoStoryList();

                    //hide progress bar
                    mLoadList.setVisibility(View.GONE);
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
    }

    /*private void setDemoStoryList(){
        ListView demoListView = (ListView) findViewById(R.id.storyListDemo);

        String[] demoStories = {
                "The God of Love",
                "The Irreverent Devotee",
                "The Key to Heaven",
                "The Man Who Could not Die",
                "The Origin of Coconut Tree",
                "The Story of Lord Ganesha",
                "The Sun-Goddess of Korea",
                "The Two Bachelors",
                "Tricksters Humbled",
                "Why Snakes Have Forked Tongues"
        };
        OneTextviewAdapter adapter = new OneTextviewAdapter(StoryListActivity.this,
                R.layout.item_one_textview,
                R.id.itemContent,
                demoStories);

        demoListView.setAdapter(adapter);
    }*/

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
