package com.pagenguyen.elib.ui.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.api.SpeechRecognitionHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VoiceSearchActivity extends Activity {
    @Bind(R.id.voiceGuidingText) TextView mGuideText;
    @Bind(R.id.microButton) Button mMicroButton;

    /* Status of mMicroButton
     * start: click to read
     * record: recording voice
     */
    public String mOnClickStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_search);
        // bind butterknife
        ButterKnife.bind(this);

        //Set default value
        stopReading();

        //microphone onClickListener
        mMicroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickStatus == "start") {
                    startRecording();
                } else if (mOnClickStatus == "record") {
                    stopReading();
                }
            }
        });
    }

    private void startRecording() {
        mOnClickStatus = "record";
        mGuideText.setText(R.string.voice_recording_text);

        //Start recordinng
        SpeechRecognitionHelper.onSpeech(VoiceSearchActivity.this);
        stopReading();
    }

    private void stopReading() {
        mOnClickStatus = "start";
        mGuideText.setText(R.string.voice_guiding_text_1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SpeechRecognitionHelper.SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK && null != data) {
                // results là mảng string các kết quả trả về
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                String vocab = results.get(0);  //get the first vocabulary in result
                if(results != null){

                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("vocab", vocab);
                    startActivity(intent);
                } else {
                    //Code
                }
            }
        }
    }
}
