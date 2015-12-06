package com.pagenguyen.elib.ui.stories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.ElibAdapter;
import com.pagenguyen.elib.ui.ElibDialog;
import com.pagenguyen.elib.ui.dictionary.VocabContentActivity;
import com.pagenguyen.elib.ui.exercise.ExerciseContentActivity;
import com.pagenguyen.elib.ui.exercise.ExerciseListActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StoryContentActivity extends AppCompatActivity {
    @Bind(R.id.currentStoryName) TextView mStoryNameText;
    @Bind(R.id.doExerciseButton) TextView mDoExerciseButton;
    @Bind(R.id.storyContentText) TextView mStoryContentText;
    @Bind(R.id.tabHostStory) TabHost mTabHost;
    @Bind(R.id.storyWordListView) ListView mWordList;

    public String mStoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_content);

        ButterKnife.bind(this);

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

        //set doExerciseButton action
        setDoExerciseButton();
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
            ElibDialog dialog = ElibDialog.newInstance(2, "Lỗi khi tải truyện.");
            dialog.show(getFragmentManager(), "read_error");
        }

        mStoryContentText.setText(sbuffer.toString());
    }

    private void setWordListView(){
        String[] mWords = { "hello",
                            "you",
                            "background" };

        ElibAdapter adapter = new ElibAdapter(StoryContentActivity.this,
                R.layout.item_vocab_content,
                R.id.vocabContentView,
                mWords);

        mWordList.setAdapter(adapter);

        //set item click action
        setListItemClick();
    }

    private void setDoExerciseButton() {
        mDoExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoryContentActivity.this, ExerciseListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setListItemClick() {
        mWordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView vocabText = (TextView) view.findViewById(R.id.vocabContentView);

                Intent intent = new Intent(StoryContentActivity.this, VocabContentActivity.class);
                intent.putExtra("vocab", vocabText.getText().toString().toLowerCase());
                startActivity(intent);
            }
        });
    }
}
