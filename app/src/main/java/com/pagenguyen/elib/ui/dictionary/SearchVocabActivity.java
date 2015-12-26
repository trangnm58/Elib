package com.pagenguyen.elib.ui.dictionary;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.api.SpeechRecognitionHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchVocabActivity extends AppCompatActivity {
    @Bind(R.id.searchVocabField) EditText mSearchField;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.searchVocabButton) Button mSearchButton;
    @Bind(R.id.microSearchButton) ImageButton mMicroButton;
    @Bind(R.id.appName) TextView mAppName;

    /* Status of mMicroButton
     * start: click to read
     * record: recording voice
     */
    public String mOnClickStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vocab);
        ButterKnife.bind(this);
        mOnClickStatus = "start";

        setAppNameFont();
        setupToolbar();
        setupVoiceSearchButton();
        setupSeachButton();
        startSearchingByEnter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SpeechRecognitionHelper.SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK && null != data) {
                // results là mảng string các kết quả trả về
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if(results != null){
                    //get the first vocabulary in result
                    String vocab = results.get(0);

                    //Go to vocabulary search result
                    Intent intent = new Intent(SearchVocabActivity.this, VocabContentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("vocab", vocab);
                    startActivity(intent);
                } else {
                    //Code
                }
            }
        }
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

    private void startSearchingByEnter() {
        mSearchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    startSearching();

                    handled = true;
                }
                return handled;
            }
        });
    }

    private void setupSeachButton() {
        //Go to the vocabulary result
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearching();
            }
        });
    }

    private void startSearching(){
        String vocab = mSearchField.getText().toString();

        //Nhap tu
        if (vocab.length() > 0) {
            Intent intent = new Intent(SearchVocabActivity.this, VocabContentActivity.class);
            intent.putExtra("vocab", mSearchField.getText().toString().toLowerCase());
            startActivity(intent);

            //Reset search field
            mSearchField.setText("");
        }
        //Chua nhap tu
        else {
            Toast.makeText(SearchVocabActivity.this,
                    "Hãy nhập từ cần tìm!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void setupVoiceSearchButton() {
        //microphone onClickListener
        mMicroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickStatus.equals("start")) {
                    startRecording();
                } else if (mOnClickStatus.equals("record")) {
                    stopReading();
                }
            }
        });
    }

    private void startRecording() {
        mOnClickStatus = "record";

        //Start recordinng
        SpeechRecognitionHelper.onSpeech(SearchVocabActivity.this);
        stopReading();
    }

    private void stopReading() {
        mOnClickStatus = "start";
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

}
