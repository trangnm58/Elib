package com.pagenguyen.elib.ui.stories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.OneTextviewAdapter;
import com.pagenguyen.elib.database.ParseConstants;
import com.pagenguyen.elib.model.Story;
import com.pagenguyen.elib.ui.dictionary.VocabContentActivity;
import com.pagenguyen.elib.ui.exercise.StoryExerciseListActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StoryContentActivity extends AppCompatActivity {
    @Bind(R.id.currentStoryName) TextView mStoryNameText;
    @Bind(R.id.storyContentText) TextView mStoryContentText;
    @Bind(R.id.tabHostStory) TabHost mTabHost;
    @Bind(R.id.storyWordListView) ListView mWordList;
    @Bind(R.id.loadStoryContentView) ProgressBar mLoadContent;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;

    public Story mStory;
    public String mStoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_content);
        ButterKnife.bind(this);

        setupToolbar();

        //Load and set Tab
        loadTabs();

        mLoadContent.setVisibility(View.VISIBLE);

        //get story from id and set story content
        getStoryFromId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercises, menu);
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
            case (R.id.action_exercises):{
                ParseQuery<ParseObject> exercises = ParseQuery.getQuery(ParseConstants.CLASS_FILL_IN_BLANK_EXERCISE);
                exercises.whereEqualTo("belongTo",mStory);


                Intent intent = new Intent(StoryContentActivity.this, StoryExerciseListActivity.class);
                intent.putExtra("story_id",mStoryId);
                startActivity(intent);
                return true;
            }
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

    private void loadTabs() {
        mTabHost.setup();
        TabHost.TabSpec spec;

        //Create story content tab
        spec = mTabHost.newTabSpec("t1");
        spec.setContent(R.id.storyContentView);
        spec.setIndicator("Truyện");
        mTabHost.addTab(spec);

        //Create story word list tab
        spec = mTabHost.newTabSpec("t2");
        spec.setContent(R.id.wordListView);
        spec.setIndicator("Từ vựng");
        mTabHost.addTab(spec);

        //set default chosen tab
        mTabHost.setCurrentTab(0);

        //set tab action
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //code
            }
        });
    }

    private void setWordListView(List<String> words){
        String[] mWords = new String[words.size()];

        int i = 0;
        for(Object s:words){
            mWords[i] = s.toString();
            i++;
        }

        OneTextviewAdapter adapter = new OneTextviewAdapter(StoryContentActivity.this,
                R.layout.item_one_textview,
                R.id.itemContent,
                mWords);

        mWordList.setAdapter(adapter);

        //set item click action
        setListItemClick();
    }

    private void setListItemClick() {
        mWordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView vocabText = (TextView) view.findViewById(R.id.itemContent);

                Intent intent = new Intent(StoryContentActivity.this, VocabContentActivity.class);
                intent.putExtra("vocab", vocabText.getText().toString().toLowerCase());
                startActivity(intent);
            }
        });
    }

    public void getStoryFromId() {
        Intent intent = getIntent();
        mStoryId = intent.getStringExtra("story_id");

        mStory = new Story();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_STORY);
        query.getInBackground(mStoryId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    mStory.setTitle(object.getString(ParseConstants.TITLE));
                    mStory.setContent(object.getString(ParseConstants.CONTENT));

                    //set story name
                    mStoryNameText.setText(mStory.getTitle());

                    //set story content
                    mStoryContentText.setText(Html.fromHtml(mStory.getContent()));

                    mLoadContent.setVisibility(View.GONE);

                    //set word list view
                    List<String> words = object.getList(ParseConstants.NEW_WORDS);
                    setWordListView(words);
                } else {
                    Toast.makeText(StoryContentActivity.this,
                            "Tai khong thanh cong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
