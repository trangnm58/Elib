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
import com.pagenguyen.elib.adapter.TopicAdapter;
import com.pagenguyen.elib.database.ParseConstants;
import com.pagenguyen.elib.model.FillInBlankExercise;
import com.pagenguyen.elib.model.MultipleChoiceExercise;
import com.pagenguyen.elib.model.Story;
import com.pagenguyen.elib.ui.dictionary.VocabContentActivity;
import com.pagenguyen.elib.ui.exercise.ExerciseListActivity;
import com.parse.FindCallback;
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

    public Menu mStoryMenuBar;

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
        mStoryMenuBar = menu;
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
            case (R.id.action_exercises):{
                Intent intent = new Intent(StoryContentActivity.this, ExerciseListActivity.class);
                intent.putExtra("MyObj", mStory);

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

        String[] mStoryNameText = { "dây leo",
                                    "hân hoan",
                                    "làm chán nản",
                                    "bà tiên",
                                    "số kiếp",
                                    "dịp",
                                    "lan khắp",
                                    "việc cưới xin",
                                    "binh lính",
                                    "sợi len, chỉ" };

        int i = 0;
        for(Object s:words){
            mWords[i] = s.toString();
            i++;
        }

        TopicAdapter adapter = new TopicAdapter(StoryContentActivity.this,
                mWords,
                mStoryNameText);

        mWordList.setAdapter(adapter);

        //set item click action
        setListItemClick();
    }

    private void setListItemClick() {
        mWordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView vocabText = (TextView) view.findViewById(R.id.itemVocabulary);

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
                    mStory.setId(object.getObjectId());
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

                    //get exercises of current story
                    ParseQuery<ParseObject> fillInBlanks = ParseQuery.getQuery(ParseConstants.CLASS_FILL_IN_BLANK_EXERCISE);
                    fillInBlanks.whereEqualTo("belongType", "story");
                    fillInBlanks.whereEqualTo("belongTo", mStoryId);

                    //get fill in blanks exercises
                    fillInBlanks.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> obj, ParseException e) {
                            if (e == null) {
                                FillInBlankExercise[] exercises = new FillInBlankExercise[obj.size()];

                                int i=0;
                                for(ParseObject o:obj){
                                    exercises[i] = new FillInBlankExercise();
                                    exercises[i].setId(o.getObjectId());
                                    exercises[i].setTitle(o.getString(ParseConstants.TITLE));
                                    i++;
                                }

                                mStory.setFillInBlankExercises(exercises);
                            } else {}
                        }
                    });

                    ParseQuery<ParseObject> multipleChoice = ParseQuery.getQuery(ParseConstants.CLASS_MULTIPLE_CHOICE_EXERCISE);
                    multipleChoice.whereEqualTo("belongType", "story");
                    multipleChoice.whereEqualTo("belongTo", mStoryId);

                    //get multiple choice exercises
                    multipleChoice.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> obj, ParseException e) {
                            if (e == null) {
                                MultipleChoiceExercise[] exercises = new MultipleChoiceExercise[obj.size()];

                                int i=0;
                                for(ParseObject o:obj){
                                    exercises[i] = new MultipleChoiceExercise();
                                    exercises[i].setId(o.getObjectId());
                                    exercises[i].setTitle(o.getString(ParseConstants.TITLE));
                                    i++;
                                }

                                mStory.setMultipleChoiceExercises(exercises);
                            } else {}
                        }
                    });

                    mStoryMenuBar.getItem(0).setEnabled(true);
                }
                else {
                    Toast.makeText(StoryContentActivity.this,
                            "Tai khong thanh cong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
