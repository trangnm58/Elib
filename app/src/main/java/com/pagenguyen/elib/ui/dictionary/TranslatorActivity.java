package com.pagenguyen.elib.ui.dictionary;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.api.YandexApi;
import com.squareup.okhttp.Response;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Can on 09-Dec-15.
 */
public class TranslatorActivity extends AppCompatActivity {
    @Bind(R.id.meditText) EditText mEditText;
    @Bind(R.id.enviButton) Button mEnviButton;
    @Bind(R.id.vienButton) Button mVienButton;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);
        ButterKnife.bind(this);

        setupToolbar();
        setupSeachButton();
    }

    private void setupSeachButton() {
        mVienButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();

                YandexApi.translate(text, "vi", "en", new YandexApi.TranslateCallback() {
                    @Override
                    public void onFailure(Response response, Throwable throwable) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TranslatorActivity.this,
                                        "Lỗi rồi!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(final String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TranslatorActivity.this,
                                        "Kết quả là: " + response,
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });
        mEnviButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();

                YandexApi.translate(text, "en", "vi", new YandexApi.TranslateCallback() {
                    @Override
                    public void onFailure(Response response, Throwable throwable) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TranslatorActivity.this,
                                        "Lỗi rồi!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(final String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TranslatorActivity.this,
                                        "Kết quả là: " + response,
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
