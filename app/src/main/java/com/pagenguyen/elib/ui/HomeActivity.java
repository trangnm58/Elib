package com.pagenguyen.elib.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.pagenguyen.elib.R;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {
	private ParseUser mCurrentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mCurrentUser = ParseUser.getCurrentUser();

		// check if there is internet
		if (isNetworkAvailable()) {
			if (mCurrentUser == null) {
				// first time using app or logout
				navigateToLogIn();
			} else {
				// we have current user
			}
		}
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
