package com.pagenguyen.elib.ui.dictionary;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.api.YandexApi;
import com.squareup.okhttp.Response;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Can on 09-Dec-15.
 */
public class TranslatorActivity extends AppCompatActivity {
    @Bind(R.id.inputTranslator) EditText mEditText;
    @Bind(R.id.outputTranslator) TextView mOutputText;
    @Bind(R.id.translatorLanguage) TextView mLanguageText;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;

    private String mFrom;
    private String mTo;

    private boolean onUpdate;

    private String mPrevInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);
        ButterKnife.bind(this);

        setSourceEnglish();
        stopUpdate();
        setupToolbar();
        mOutputText.setMovementMethod(new ScrollingMovementMethod());

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!onUpdate) {
                    updateOutputTranslator();
                }
            }
        });
    }

    private void updateOutputTranslator() {
        String text = mEditText.getText().toString();
        if (!text.equals(mPrevInput)) {
            mPrevInput = text;
            startUpdate();

            YandexApi.translate(text, mFrom, mTo, new YandexApi.TranslateCallback() {
                @Override
                public void onFailure(Response response, Throwable throwable) {
                    Log.d("Lỗi update", response.toString());
                    stopUpdate();
                    updateOutputTranslator();
                }

                @Override
                public void onSuccess(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mOutputText.setText(response);
                            stopUpdate();
                            updateOutputTranslator();
                        }
                    });
                }
            });
        }
    }

    private void stopUpdate() {
        onUpdate = false;
    }

    private void startUpdate() {
        onUpdate = true;
    }

    public void toogleSource(View v) {
        if (mFrom.equals("en")) {
            setSourceVietnamese();
        } else {
            setSourceEnglish();
        }
    }

    private void setSourceEnglish() {
        mFrom = "en";
        mTo = "vi";
        mLanguageText.setText("Chọn ngôn ngữ: Anh - Việt");
    }

    private void setSourceVietnamese() {
        mFrom = "vi";
        mTo = "en";
        mLanguageText.setText("Chọn ngôn ngữ: Việt - Anh");
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
