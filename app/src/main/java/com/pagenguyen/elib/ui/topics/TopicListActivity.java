package com.pagenguyen.elib.ui.topics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.OneTextviewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TopicListActivity extends AppCompatActivity {
    @Bind(R.id.topicListView) ListView mListTopics;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);
        ButterKnife.bind(this);

        setupToolbar();
        setTopicListView();
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

    private void setTopicListView() {
        String[] mTopics={"GIÁO DỤC", "GIA ĐÌNH", "TÍNH CÁCH", "NGHỀ NGHIỆP", "NẤU ĂN", "THỂ THAO"};

        OneTextviewAdapter adapter=new OneTextviewAdapter(TopicListActivity.this,
                R.layout.item_one_textview,
                R.id.itemContent,
                mTopics);

        mListTopics.setAdapter(adapter);
    }

    private void setListItemClick() {
        mListTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nameText = (TextView) view.findViewById(R.id.itemContent);
                String name = nameText.getText().toString();

                Intent intent = new Intent(TopicListActivity.this, TopicContentActivity.class);
                intent.putExtra("topic_name", name);
                startActivity(intent);
            }
        });
    }

}
