package com.pagenguyen.elib.api;

import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Can on 08-Dec-15.
 */
public class YandexApi {
    public static final String TAG = YandexApi.class.getSimpleName();
    private static final String[] API_KEY = {
            "trnsl.1.1.20151208T144320Z.e083aec14053275c.f27c53534226ddb594e32c246ce25cc81b2014f0"
    };
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static void translate(String text, String from, String to, final TranslateCallback cb) {
        String translateUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
                "key=" + API_KEY[0] +
                "&text=" + text +
                "&lang=" + from + "-" + to;

        Request request = new Request.Builder()
                .url(translateUrl)
                .build();

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                cb.onFailure(null, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d("response", response.toString());
                if (!response.isSuccessful()) {
                    cb.onFailure(response, null);
                    return;
                }
                String jsonData = response.body().string();
                try {
                    JSONObject data = new JSONObject(jsonData);
                    String outText = data.getString("text");
                    cb.onSuccess(outText);
                } catch (JSONException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }
            }
        });
    }

    public interface TranslateCallback {
        /**
         * called when the server response was not 2xx or when an exception was thrown in the process
         * @param response - in case of server error (4xx, 5xx) this contains the server response
         *                 in case of IO exception this is null
         * @param throwable - contains the exception. in case of server error (4xx, 5xx) this is null
         */
        public void onFailure(Response response, Throwable throwable);

        /**
         * contains the server response
         * @param response
         */
        public void onSuccess(String response);
    }
}
