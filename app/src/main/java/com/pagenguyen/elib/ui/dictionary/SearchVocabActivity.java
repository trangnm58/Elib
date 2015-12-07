package com.pagenguyen.elib.ui.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pagenguyen.elib.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchVocabActivity extends AppCompatActivity {
    @Bind(R.id.searchVocabField) EditText mSearchField;
    @Bind(R.id.searchVocabButton) Button mSearchButton;
    @Bind(R.id.microSearchButton) Button mMicroButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vocab);
        ButterKnife.bind(this);

        setupToolbar();

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
        });
    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

}
