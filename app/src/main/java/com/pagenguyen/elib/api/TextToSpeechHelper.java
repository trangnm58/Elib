package com.pagenguyen.elib.api;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by Page on 07/12/2015.
 */
public class TextToSpeechHelper implements TextToSpeech.OnInitListener {
	private TextToSpeech textToSpeech;
	public void initialize(Context context) {
		// initialize new TextToSpeech object
		 textToSpeech = new TextToSpeech(context, this);
	}

	public void speak(String toSpeak) {
		// speak toSpeak
		textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
	}

	public void onPause() {
		if(textToSpeech != null){
			textToSpeech.stop();
			textToSpeech.shutdown();
		}
	}

	@Override
	public void onInit(int status) {
		if(status != TextToSpeech.ERROR) {
			textToSpeech.setLanguage(Locale.UK);
		}
	}
}
