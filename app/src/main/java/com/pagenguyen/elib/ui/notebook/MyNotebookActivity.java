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

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.OneTextviewAdapter;
import com.pagenguyen.elib.ui.dictionary.VocabContentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyNotebookActivity extends AppCompatActivity {
	@Bind(R.id.exerciseListView) ListView mListExercise;
	@Bind(R.id.my_toolbar) Toolbar mToolbar;

	String[] mWords = { "Hello",
			"Beautiful",
			"Goodbye",
			"Ugly",
			"Excellent"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_notebook);
		ButterKnife.bind(this);

		setupToolbar();
		setExerciseListView();
		setListItemClick();
	}

	private void setupToolbar() {
		setSupportActionBar(mToolbar);
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
	}

	private void setExerciseListView() {
		OneTextviewAdapter adapter = new OneTextviewAdapter(MyNotebookActivity.this,
				R.layout.item_one_textview,
				R.id.itemContent,
				mWords);

		mListExercise.setAdapter(adapter);
	}

	private void setListItemClick() {
		mListExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MyNotebookActivity.this, VocabContentActivity.class);
				intent.putExtra("vocab", mWords[position]);
				startActivity(intent);
			}
		});
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
}
