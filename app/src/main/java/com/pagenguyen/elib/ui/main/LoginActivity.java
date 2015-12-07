package com.pagenguyen.elib.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.pagenguyen.elib.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
	@Bind(R.id.userNameField) EditText mUsername;
	@Bind(R.id.passwordField) EditText mPassword;
	@Bind(R.id.loginButton) Button mLoginButton;
    private ProgressBar progressBar;

    public void signUp(View v) {
        // go to sign up activity
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.logInProgress);
        changeLoadingState();
		// bind butterknife
		ButterKnife.bind(this);

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
                    changeLoadingState();
					ParseUser.logInInBackground(username, password, new LogInCallback() {
						@Override
						public void done(ParseUser user, ParseException e) {
                            changeLoadingState();
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

    private void changeLoadingState() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
