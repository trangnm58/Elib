package com.pagenguyen.elib.ui.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.VocabAdapter;
import com.pagenguyen.elib.api.GlosbeApi;
import com.pagenguyen.elib.model.GlosbeResult;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VocabContentActivity extends AppCompatActivity {
    @Bind(R.id.vocabView) TextView mVocabView;
    @Bind(R.id.definitionListView) ListView mListDefinition;
    @Bind(R.id.exampleListView) ListView mListExample;
    @Bind(R.id.emptyListView) TextView mEmptyTextView;

    public String mVocab;
    public static String[] mVocabDefinition;
    public static String[] mVocabExamples;
    public Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab_content);

        ButterKnife.bind(this);

        mIntent = getIntent();
        mVocab = mIntent.getStringExtra("vocab");
        mEmptyTextView.setVisibility(View.GONE);

        //get the vocabulary from Search Vocab Activity
        setVocabView();
        //get definition and examples of the vocabulary
        getVocabContent();
    }

    public void setVocabView(){
        mVocabView.setText(mVocab);
    }

    public void getVocabContent(){
        final GlosbeResult glosbeResult = new GlosbeResult();

        GlosbeApi.getTranslations(mVocab, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                glosbeResult.setTranslations(GlosbeApi.callbackTransHelper(response));

                // runOnUiThread -> cập nhật view cho phần Nghĩa
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVocabDefinition = glosbeResult.getTranslations();

                        //Set definitions list view
                        if (mVocabDefinition != null && mVocabDefinition.length > 0) {
                            VocabAdapter adapter = new VocabAdapter(VocabContentActivity.this,
                                    R.layout.vocab_content_item,
                                    mVocabDefinition);
                            mListDefinition.setAdapter(adapter);
                        }

                        else { mEmptyTextView.setVisibility(View.VISIBLE); }
                    }
                });
            }
        }, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                glosbeResult.setExamples(GlosbeApi.callbackExpHelper(response));

                // runOnUiThread -> cập nhật view cho phần Ví dụ
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVocabExamples = glosbeResult.getExamples();

                        //Set example list view
                        if (mVocabExamples != null && mVocabExamples.length > 0) {
                            VocabAdapter adapter = new VocabAdapter(VocabContentActivity.this,
                                    R.layout.vocab_content_item,
                                    mVocabExamples);
                            mListExample.setAdapter(adapter);
                        }

                        else { mEmptyTextView.setVisibility(View.VISIBLE); }
                    }
                });
            }
        });
    }
}
