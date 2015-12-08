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
import android.widget.TabHost;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.OneTextviewAdapter;
import com.pagenguyen.elib.ui.dictionary.VocabContentActivity;
import com.pagenguyen.elib.ui.exercise.ExerciseListActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StoryContentActivity extends AppCompatActivity {
    @Bind(R.id.currentStoryName) TextView mStoryNameText;
    @Bind(R.id.storyContentText) TextView mStoryContentText;
    @Bind(R.id.tabHostStory) TabHost mTabHost;
    @Bind(R.id.storyWordListView) ListView mWordList;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;

    public String mStoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_content);
        ButterKnife.bind(this);

        setupToolbar();

        //Load and set Tab
        loadTabs();

        //get StoryName and set View
        setNameView();

        //set Story content
        try {
            setStoryContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set word list view
        setWordListView();
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
                Intent intent = new Intent(StoryContentActivity.this, ExerciseListActivity.class);
                startActivity(intent);
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

    private void setNameView() {
        //get StoryName
        Intent intent = getIntent();
        mStoryName = intent.getStringExtra("story_name");
        mStoryNameText.setText(mStoryName);
    }

    private void setStoryContent() throws IOException {
        InputStream input = this.getResources().openRawResource(R.raw.sleeping_beauty);
        StringBuilder sbuffer = new StringBuilder();
        String data = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        if(input != null){
            while((data = reader.readLine()) != null){
                sbuffer.append(data).append("\n");
            }
            input.close();
        } else {
        }

        mStoryContentText.setText(Html.fromHtml(sbuffer.toString()));
    }

    private void setWordListView(){
        String[] mWords = { "creeper",
                            "delight",
                            "deject",
                            "fairy",
                            "fate",
                            "occasion",
                            "prevail",
                            "marriage",
                            "soldier",
                            "yarn" };

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
}
