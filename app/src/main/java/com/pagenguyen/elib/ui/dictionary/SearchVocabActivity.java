package com.pagenguyen.elib.ui.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pagenguyen.elib.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchVocabActivity extends Activity {
    @Bind(R.id.searchVocabField) EditText mSearchField;
    @Bind(R.id.searchVocabButton) Button mSearchButton;
    @Bind(R.id.microSearchButton) Button mMicroButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vocab);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        ButterKnife.bind(this);

        //Search vocabulary by voice
        mMicroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reset search field
                mSearchField.setText("");

                Intent intent = new Intent(SearchVocabActivity.this, VoiceSearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //Go to the vocabulary result
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vocab = mSearchField.getText().toString();

                //Nhap tu
                if(vocab.length() > 0) {
                    Intent intent = new Intent(SearchVocabActivity.this, VocabContentActivity.class);
                    // Success
                    // Go to Vocab Content Activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("vocab", mSearchField.getText().toString());
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
        });
    }

}
