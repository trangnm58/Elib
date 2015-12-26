package com.pagenguyen.elib.ui.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.WordListAdapter;
import com.pagenguyen.elib.api.TextToSpeechHelper;
import com.pagenguyen.elib.database.ParseConstants;
import com.pagenguyen.elib.ui.dictionary.VocabContentActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyNotebookActivity extends AppCompatActivity {
	@Bind(R.id.wordListView) ListView mVocabularyListView;
	@Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.loadingProgress) ProgressBar mProgressBar;

	private TextToSpeechHelper tts = new TextToSpeechHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_notebook);
		ButterKnife.bind(this);

		tts.initialize(MyNotebookActivity.this);
        mProgressBar.setVisibility(View.VISIBLE);

		setupToolbar();
		setVocabularyListView();
	}

	@Override
	protected void onPause() {
		tts.onPause();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_my_notebook, menu);
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
			case (R.id.action_settings): {
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

	private void setVocabularyListView(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_SAVED_WORD);
		query.whereEqualTo(ParseConstants.SAVED_WORD_USER, ParseUser.getCurrentUser());

		query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                mProgressBar.setVisibility(View.INVISIBLE);
                if (e == null) {
                    int num = scoreList.size();
                    String[] words = new String[num];
                    String[] means = new String[num];

                    for (int i = 0; i < num; i++) {
                        words[i] = scoreList.get(i).get(ParseConstants.SAVED_WORD) + "";
                        means[i] = scoreList.get(i).get(ParseConstants.SAVED_WORD_MEANING) + "";
                    }
                    WordListAdapter adapter = new WordListAdapter(MyNotebookActivity.this,
                            words, means);
                    adapter.setDel(true);

                    mVocabularyListView.setAdapter(adapter);
                    setItemOnClick();
                } else {
                    Toast.makeText(
                            MyNotebookActivity.this,
                            "Không thể tải sổ tay.",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
	}

	private void setItemOnClick(){
		mVocabularyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				TextView vocabText = (TextView) view.findViewById(R.id.itemVocabulary);
				String name = vocabText.getText().toString().toLowerCase();

				Intent intent = new Intent(MyNotebookActivity.this, VocabContentActivity.class);
				intent.putExtra("vocab", name);
				startActivity(intent);
				return false;
			}
		});
		mVocabularyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				tts.speak(
						((TextView) view.findViewById(R.id.itemVocabulary))
								.getText().toString().toLowerCase()
				);
			}
		});
	}
}
