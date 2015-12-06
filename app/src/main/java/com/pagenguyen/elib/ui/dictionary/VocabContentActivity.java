package com.pagenguyen.elib.ui.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.ElibAdapter;
import com.pagenguyen.elib.api.GlosbeApi;
import com.pagenguyen.elib.model.GlosbeResult;
import com.pagenguyen.elib.ui.HomeActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VocabContentActivity extends Activity {
    @Bind(R.id.vocabView) TextView mVocabView;
    @Bind(R.id.definitionListView) ListView mListDefinition;
    @Bind(R.id.exampleListView) ListView mListExample;
    @Bind(R.id.emptyListView) TextView mEmptyTextView;
    @Bind(R.id.loadingContentView) ProgressBar mLoadingView;
    @Bind(R.id.vocabBackHome) ImageView mHomeIcon;
    @Bind(R.id.volumeButton) ImageView mVolumeIcon;

    public String mVocab;
    public String[] mVocabDefinition;
    public String[] mVocabExamples;
    public Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab_content);

        ButterKnife.bind(this);

        mIntent = getIntent();
        mVocab = mIntent.getStringExtra("vocab");

        //set default value
        mEmptyTextView.setText("Đang tải...");
        mLoadingView.setVisibility(View.VISIBLE);

        //get the vocabulary from Search Vocab Activity
        setVocabView();
        //get definition and examples of the vocabulary
        setVocabContentView();
        //set homeIcon click
        setHomeIconClick();
        //set volume Icon  click
        setVolumeIconClick();
    }

    public void setVocabView(){
        mVocabView.setText(mVocab);
    }

    public void setVocabContentView(){
        final GlosbeResult glosbeResult = new GlosbeResult();

        GlosbeApi.getTranslations(mVocab, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                glosbeResult.setTranslations(GlosbeApi.callbackTransHelper(response));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVocabDefinition = glosbeResult.getTranslations();
                        mLoadingView.setVisibility(View.GONE);

                        //Set definitions list view
                        if (mVocabDefinition != null && mVocabDefinition.length > 0) {
                            ElibAdapter adapter = new ElibAdapter(VocabContentActivity.this,
                                    R.layout.item_vocab_content,
                                    R.id.vocabContentView,
                                    mVocabDefinition);
                            mListDefinition.setAdapter(adapter);
                        }
                        mEmptyTextView.setText(R.string.no_vocab_content);
                        mListDefinition.setEmptyView(mEmptyTextView);
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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVocabExamples = glosbeResult.getExamples();
                        mLoadingView.setVisibility(View.GONE);

                        //Set example list view
                        if (mVocabExamples != null && mVocabExamples.length > 0) {
                            ElibAdapter adapter = new ElibAdapter(VocabContentActivity.this,
                                    R.layout.item_vocab_content,
                                    R.id.vocabContentView,
                                    mVocabExamples);
                            mListExample.setAdapter(adapter);
                        }

                        mEmptyTextView.setText(R.string.no_vocab_content);
                        mListExample.setEmptyView(mEmptyTextView);
                    }
                });
            }
        });
    }

    private void setHomeIconClick(){
        mHomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VocabContentActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setVolumeIconClick() {
        mVolumeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VocabContentActivity.this,
                        "Đang đọc phát âm",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
