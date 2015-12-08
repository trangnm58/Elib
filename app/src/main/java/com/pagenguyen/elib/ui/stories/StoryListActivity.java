package com.pagenguyen.elib.ui.stories;

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

public class StoryListActivity extends AppCompatActivity {
    @Bind(R.id.storyListView) ListView mListStories;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;

    public Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);
        ButterKnife.bind(this);

        setupToolbar();
        setStoryListView();
        setListItemClick();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setStoryListView() {
        // load from Parse
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

        OneTextviewAdapter adapter = new OneTextviewAdapter(StoryListActivity.this,
                R.layout.item_one_textview,
                R.id.itemContent,
                mStories);

        mListStories.setAdapter(adapter);
    }

    private void setListItemClick() {
        mListStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nameText = (TextView) view.findViewById(R.id.itemContent);
                String name = nameText.getText().toString();

                Intent intent = new Intent(StoryListActivity.this, StoryContentActivity.class);
                intent.putExtra("story_name", name);
                startActivity(intent);
            }
        });
    }

}
