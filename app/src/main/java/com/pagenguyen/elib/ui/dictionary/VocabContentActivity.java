package com.pagenguyen.elib.ui.dictionary;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.VocabContentAdapter;
import com.pagenguyen.elib.api.GlosbeApi;
import com.pagenguyen.elib.api.SpeechRecognitionHelper;
import com.pagenguyen.elib.api.TextToSpeechHelper;
import com.pagenguyen.elib.model.GlosbeResult;
import com.pagenguyen.elib.ui.main.HomeActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VocabContentActivity extends AppCompatActivity {
    @Bind(R.id.vocabView) TextView mVocabView;
    @Bind(R.id.vocabContentListView) ListView mVocabContentList;

    @Bind(R.id.emptyListView) TextView mEmptyTextView;
    @Bind(R.id.loadingContentView) ProgressBar mLoadingView;
    @Bind(R.id.volumeButton) ImageView mVolumeIcon;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;

    public String mVocab;
    public String[] mVocabDefinition;
    public String[] mVocabExamples;
    public Intent mIntent;

	private TextToSpeechHelper textToSpeech = new TextToSpeechHelper();
    private Menu mMenu;
    private boolean isListening = false;

    private boolean mMeaningDone = false;
    private boolean mExampleDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab_content);
        ButterKnife.bind(this);
        setupToolbar();

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

	@Override
	protected void onResume() {
		super.onResume();
		textToSpeech.initialize(VocabContentActivity.this);
	}

	@Override
	protected void onPause() {
		textToSpeech.onPause();
		super.onPause();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vocab_detail, mMenu);
        mMenu.getItem(0).setVisible(true);
        mMenu.getItem(1).setVisible(false);
        mMenu.getItem(2).setVisible(true);

        // Define the listener
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mMenu.getItem(1).setVisible(false);
                mMenu.getItem(2).setVisible(true);
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                mMenu.getItem(1).setVisible(true);
                mMenu.getItem(2).setVisible(false);
                return true;  // Return true to expand action view
            }
        };

        // Get the MenuItem for the action item
        MenuItem searchItem = mMenu.findItem(R.id.action_search);
        // Assign the listener to that action item
        MenuItemCompat.setOnActionExpandListener(searchItem, expandListener);

        // Associate searchable configuration with the SearchView
        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners...
        searchView.setQueryHint("Nhập từ cần tìm...");

        // set text color
        // traverse the view to the widget containing the hint text
        LinearLayout ll = (LinearLayout)searchView.getChildAt(0);
        LinearLayout ll2 = (LinearLayout)ll.getChildAt(2);
        LinearLayout ll3 = (LinearLayout)ll2.getChildAt(1);
        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete)ll3.getChildAt(0);
        // set the text color
        autoComplete.setTextColor(getResources().getColor(R.color.TextColorWhite));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mVocab = "" + searchView.getQuery();

                //set default value
                mLoadingView.setVisibility(View.VISIBLE);

                mVocabContentList.setVisibility(View.GONE);

                //get the vocabulary from Search Vocab Activity
                setVocabView();
                //get definition and examples of the vocabulary
                getVocabContent();

                mMenu.getItem(0).collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Do nothing
                return false;
            }
        });

        return super.onCreateOptionsMenu(mMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_home):{
                Intent intent = new Intent(VocabContentActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
            case (R.id.action_voice):{
                if(!isListening) {
                    startListening();
                } else {
                    stopListening();
                }
                return true;
            }
            case (android.R.id.home): {
                onBackPressed();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SpeechRecognitionHelper.SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK && null != data) {
                // results là mảng string các kết quả trả về
                ArrayList<String> results =
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if(results != null){
                    //get the first vocabulary in result
                    mVocab = results.get(0);

                    mLoadingView.setVisibility(View.VISIBLE);

                    mVocabContentList.setVisibility(View.GONE);

                    //get the vocabulary from Search Vocab Activity
                    setVocabView();
                    //get definition and examples of the vocabulary
                    getVocabContent();

                    mMenu.getItem(0).collapseActionView();
                } else {
                    // Do nothing
                }
            }
        }
    }

    private void startListening() {
        isListening = true;

        //Start listening
        SpeechRecognitionHelper.onSpeech(VocabContentActivity.this);
        stopListening();
    }

    private void stopListening() {
        isListening = false;
    }

    private void handleIntent(Intent intent) {
        mIntent = intent;

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mVocab = intent.getStringExtra(SearchManager.QUERY);
        }
        else {
            mVocab = mIntent.getStringExtra("vocab");
        }

        mLoadingView.setVisibility(View.VISIBLE);

        //initiate vocab content
        mVocabDefinition = new String[0];
        mVocabExamples = new String[0];

        //get the vocabulary from Search Vocab Activity
        setVocabView();
        //get definition and examples of the vocabulary
        getVocabContent();
        //set volume Icon  click
        setVolumeIconClick();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void setVocabView(){
        mVocabView.setText(mVocab);

        mEmptyTextView.setVisibility(View.GONE);
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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVocabDefinition = glosbeResult.getTranslations();

                        mMeaningDone = true;

                        setVocabContentView();
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

                        mExampleDone = true;

                        setVocabContentView();
                    }
                });
            }
        });
    }

    private void setVocabContentView(){
        if(mMeaningDone && mExampleDone){
            //Vocab is in dictionary
            if(mVocabDefinition != null || mVocabExamples != null){
                //Set definitions list view
                VocabContentAdapter adapter = new VocabContentAdapter(VocabContentActivity.this,
                        mVocabDefinition,
                        mVocabExamples);

                mVocabContentList.setAdapter(adapter);

                mLoadingView.setVisibility(View.GONE);
                mVocabContentList.setVisibility(View.VISIBLE);
            } else {
                mEmptyTextView.setVisibility(View.VISIBLE);
            }
        } else {}
    }

    private void setVolumeIconClick() {
        mVolumeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            textToSpeech.speak(mVocab);
            }
        });
    }
}
