package com.pagenguyen.elib.ui.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
	@Bind(R.id.my_toolbar) Toolbar mToolbar;
	@Bind(R.id.userNameField) EditText mUsername;
	@Bind(R.id.passwordField) EditText mPassword;
	@Bind(R.id.loginButton) Button mLoginButton;
	@Bind(R.id.logInProgress) ProgressBar mProgressBar;
	@Bind(R.id.appName)	TextView mAppName;

    public void signUp(View v) {
        // go to sign up activity
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);

		setupToolbar();
        setAppNameFont();
		stopLoading();
		setSignInButton();
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

    private void setAppNameFont() {
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/the-rave-is-in-your-pants.regular.otf");
        mAppName.setTypeface(customFont, Typeface.BOLD);
    }

    private void setSignInButton() {
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();

				// remove all white spaces
				username = username.trim();
				password = password.trim();

				if (username.isEmpty() || password.isEmpty()) {
					// display message to warn user
				} else {
					// login
					startLoading();
					ParseUser.logInInBackground(username, password, new LogInCallback() {
						@Override
						public void done(ParseUser user, ParseException e) {
							stopLoading();
							if (e == null) {
								// Success
								// Go to Home Activity
								Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							} else {
								// somethings wrong (checked by Parse)
								// display message to user
							}
						}
					});
				}
			}
		});
	}

	private void startLoading() {
		mProgressBar.setVisibility(View.VISIBLE);
	}
	private void stopLoading() {
		mProgressBar.setVisibility(View.INVISIBLE);
	}
}
