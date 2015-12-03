package com.pagenguyen.elib.api;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Page on 03/12/2015.
 */
public class SpeechRecognitionHelper {
	public static final int SPEECH_REQUEST_CODE = 100;

	public static void onSpeech (Activity context) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
		try {
			context.startActivityForResult(intent, SPEECH_REQUEST_CODE);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(context.getApplicationContext(), "error",
					Toast.LENGTH_SHORT).show();
		}
	}
}
