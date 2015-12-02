package com.pagenguyen.elib.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pagenguyen.elib.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
	@Bind(R.id.usernameField) EditText mUsername;
	@Bind(R.id.passwordField) EditText mPassword;
	@Bind(R.id.emailField) EditText mEmail;
	@Bind(R.id.signUpButton) Button mSignUpButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		// bind butterknife
		ButterKnife.bind(this);

		mSignUpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();
				String email = mEmail.getText().toString();

				// remove all white spaces
				username = username.trim();
				password = password.trim();
				email = email.trim();

				if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
					// invalid input
					// display message to warn user
				} else {
					// create new user
					// show progress bar
					setProgressBarIndeterminateVisibility(true);

					ParseUser newUser = new ParseUser();
					newUser.setUsername(username);
					newUser.setPassword(password);
					newUser.setEmail(email);

					newUser.signUpInBackground(new SignUpCallback() {
						@Override
						public void done(ParseException e) {
							// hide progress bar
							setProgressBarIndeterminateVisibility(false);

							if (e == null) {
								// Success
								// go to Home Activity
								Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							} else {
								// somethings wrong (checked by Parse)
								// display message
							}
						}
					});
				}
			}
		});
	}
}
