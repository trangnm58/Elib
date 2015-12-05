package com.pagenguyen.elib.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.ui.dictionary.SearchVocabActivity;
import com.pagenguyen.elib.ui.stories.StoryListActivity;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;

public class HomeActivity extends Activity {
	private static final String TAG = HomeActivity.class.getSimpleName();
	private ParseUser mCurrentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		ParseAnalytics.trackAppOpened(getIntent());

		// check if there is internet
		if (isNetworkAvailable()) {
			mCurrentUser = ParseUser.getCurrentUser();
			if (mCurrentUser == null) {
				// first time using app or logout
				navigateToLogIn();
			} else {
				// we have current user
				Log.d(TAG, mCurrentUser.getUsername());
			}
		} else {
			// display message to user
			Log.d(TAG, "no internet");
		}

		Button mDictionaryFunc = (Button) findViewById(R.id.dictionaryFunc);
		Button mStoryFunc = (Button) findViewById(R.id.readStoryFunc);

		mDictionaryFunc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, SearchVocabActivity.class);
				startActivity(intent);
			}
		});

		mStoryFunc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, StoryListActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_logout) {
			// use ParseUser logout() function
			ParseUser.logOut();
			// go to login activity after logout
			navigateToLogIn();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void navigateToLogIn() {
		Intent intent = new Intent(this, LoginActivity.class);
		// clear activity stack -> user can't back when in login activity
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		return info != null && info.isConnected();
	}
}
