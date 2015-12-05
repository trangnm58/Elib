package com.pagenguyen.elib.ui.stories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.ElibAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StoryListActivity extends Activity {
    @Bind(R.id.storyListView) ListView mListStories;


    public Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);
        ButterKnife.bind(this);

        setStoryListView();

        mListStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nameText = (TextView) view.findViewById(R.id.storyNameText);
                String name = nameText.getText().toString();

                Intent intent = new Intent(StoryListActivity.this, StoryContentActivity.class);
                intent.putExtra("story_name", name);
                startActivity(intent);
            }
        });
    }

    private void setStoryListView() {
        String[] mStories = { "Sleeping Beauty",
                "The God of Love",
                "The Irreverent Devotee",
                "The Key to Heaven",
                "The Man Who Could not Die",
                "The Origin of Coconut Tree",
                "The Story of Lord Ganesha",
                "The Sun-Goddess of Korea",
                "The Two Bachelors",
                "Tricksters Humbled",
                "Why Snakes Have Forked Tongues",
                "The Origin of Coconut Tree 2",
                "The Story of Lord Ganesha 2",
                "The Sun-Goddess of Korea 2",
                "The Two Bachelors 2",
                "Tricksters Humbled 2",
                "Why Snakes Have Forked Tongues 2" };

        ElibAdapter adapter = new ElibAdapter(StoryListActivity.this,
                R.layout.story_name_item,
                R.id.storyNameText,
                mStories);

        mListStories.setAdapter(adapter);
    }

}
