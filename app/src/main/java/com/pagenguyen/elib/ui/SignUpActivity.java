package com.pagenguyen.elib.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends Activity {
	@Bind(R.id.usernameField) EditText mUsername;
	@Bind(R.id.passwordField) EditText mPassword;
	@Bind(R.id.rePasswordField) EditText mRePassword;
	@Bind(R.id.emailField) EditText mEmail;
	@Bind(R.id.signUpButton) Button mSignUpButton;
	private ProgressBar progressBar;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{6,15}$";
    private static final String PASSWORD_PATTERN = "^[a-z0-9_-]{8,50}$";

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
        progressBar = (ProgressBar) findViewById(R.id.signUpProgress);
        changeLoadingState();
        // bind butterknife
		ButterKnife.bind(this);

		mSignUpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();
            String rePassword = mRePassword.getText().toString();
            String email = mEmail.getText().toString();

            // remove all white spaces
            username = username.trim();
            password = password.trim();
            email = email.trim();

            if (username.isEmpty() || !username.matches(USERNAME_PATTERN)){
                Toast.makeText(
                        SignUpActivity.this,
                        "Tên đăng nhập không hợp lệ!",
                        Toast.LENGTH_LONG
                ).show();
            } else if (password.isEmpty() || !password.matches(PASSWORD_PATTERN)) {
                Toast.makeText(
                        SignUpActivity.this,
                        "Mật khẩu không hợp lệ!",
                        Toast.LENGTH_LONG
                ).show();
            } else if (!rePassword.equals(password)) {
                Toast.makeText(
                        SignUpActivity.this,
                        "Mật khẩu không khớp!",
                        Toast.LENGTH_LONG
                ).show();
            } else if (email.isEmpty() || !email.matches(EMAIL_PATTERN)){
                Toast.makeText(
                        SignUpActivity.this,
                        "Email không hợp lệ!",
                        Toast.LENGTH_LONG
                ).show();
            } else {
                // create new user
                changeLoadingState();
                setProgressBarIndeterminateVisibility(true);

                ParseUser newUser = new ParseUser();
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setEmail(email);

                newUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        changeLoadingState();
                        setProgressBarIndeterminateVisibility(false);

                        if (e == null) {
                            // Success
                            // go to Home Activity
                            Toast.makeText(
                                    SignUpActivity.this,
                                    "Đăng ký thành công!",
                                    Toast.LENGTH_LONG
                            ).show();
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

    public void registered(View v) {
        // go to sign up activity
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void changeLoadingState() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
