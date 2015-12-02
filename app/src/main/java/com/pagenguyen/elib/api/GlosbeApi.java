package com.pagenguyen.elib.api;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Page on 02/12/2015.
 */
public class GlosbeApi {
	private static final int NUM_OF_SAMPLES = 3;
	public static void getTranslations(String phrase, Callback callbackTrans, Callback callbackExps) {
		// define source and destination languages
		String desLanguage = "vie";
		String srcLanguage = "eng";

		// glosbe url for translate function
		String glosbeTransUrl = "https://glosbe.com/gapi/translate?from=" + srcLanguage +
				"&dest=" + desLanguage + "&format=json&phrase=" + phrase;

		// glosbe url for translate memory function
		String glosbeExpUrl = "https://glosbe.com/gapi/tm?from=" + srcLanguage +
				"&dest=" + desLanguage + "&format=json&phrase=" + phrase;

		OkHttpClient client = new OkHttpClient();
		Request request1 = new Request.Builder()
				.url(glosbeTransUrl)
				.build();
		Request request2 = new Request.Builder()
				.url(glosbeExpUrl)
				.build();
		Call call1 = client.newCall(request1);
		call1.enqueue(callbackTrans);
		Call call2 = client.newCall(request2);
		call2.enqueue(callbackExps);
	}

	public static String[] callbackTransHelper(Response response) {
		try {
			if (response.isSuccessful()) {
				// this callback helper is for translations
				String data = response.body().string();
				JSONArray translations = new JSONObject(data).getJSONArray("tuc");
				String[] trans = new String[NUM_OF_SAMPLES];
				for (int i=0; i < NUM_OF_SAMPLES; i++) {
					JSONObject translation = translations.getJSONObject(i);
					JSONObject phrase = translation.getJSONObject("phrase");
					trans[i] = phrase.getString("text");
				}
				return trans;
			} else {
				// alert user about error
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] callbackExpHelper(Response response) {
		try {
			if (response.isSuccessful()) {
				// this callback helper is for examples
				String data = response.body().string();
				JSONArray examples = new JSONObject(data).getJSONArray("examples");
				String[] exps = new String[NUM_OF_SAMPLES];
				for (int i=0; i < NUM_OF_SAMPLES; i++) {
					JSONObject exp = examples.getJSONObject(i);
					exps[i] = exp.getString("second");
				}
				return exps;
			} else {
				// alert user about error
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
