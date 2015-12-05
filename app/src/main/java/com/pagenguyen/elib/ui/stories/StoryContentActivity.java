package com.pagenguyen.elib.ui.stories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.pagenguyen.elib.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StoryContentActivity extends Activity {
    @Bind(R.id.currentStoryName) TextView mStoryNameText;
    public String mStoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_content);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        mStoryName = intent.getStringExtra("story_name");

        setNameView();
    }

    private void setNameView() {
        mStoryNameText.setText(mStoryName);
    }

}
