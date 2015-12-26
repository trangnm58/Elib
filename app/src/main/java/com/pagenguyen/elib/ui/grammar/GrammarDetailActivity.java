package com.pagenguyen.elib.ui.grammar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.ui.main.HomeActivity;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GrammarDetailActivity extends AppCompatActivity {
    private static final String TAG = GrammarDetailActivity.class.getSimpleName();

    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.grammarContent) TextView mTextView;
    @Bind(R.id.grammarLoading) ProgressBar mProgressBar;

    private String mLink;
    private String mTitle;
    private String mData;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_detail);
        ButterKnife.bind(this);

        mIntent = getIntent();
        mLink = mIntent.getStringExtra("link");
        mTitle = mIntent.getStringExtra("title");

        setupToolbar();

        mProgressBar.setVisibility(ProgressBar.VISIBLE);

        setData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grammar_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_download):{
                Toast.makeText(
                        GrammarDetailActivity.this,
                        "Chức năng chưa hỗ trợ!",
                        Toast.LENGTH_SHORT
                ).show();
                // TODO intent to my profile
                return true;
            }
            case (R.id.action_favorite):{
                Toast.makeText(
                        GrammarDetailActivity.this,
                        "Chức năng chưa hỗ trợ!",
                        Toast.LENGTH_SHORT
                ).show();
                // TODO intent to settings
                return true;
            }
            case (R.id.action_home):{
                Intent intent = new Intent(GrammarDetailActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            case (android.R.id.home): {
                onBackPressed();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(mTitle);
    }

    private void setData() {
        Request request = new Request.Builder()
                .url(mLink)
                .build();

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(
                        GrammarDetailActivity.this,
                        "Không thể tải bài học.",
                        Toast.LENGTH_LONG
                ).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(TAG, response.toString());
                if (!response.isSuccessful()) {
                    Toast.makeText(
                            GrammarDetailActivity.this,
                            "Không thể tải bài học.",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }
                mData = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

                        mTextView.setText(Html.fromHtml(mData));
                    }
                });
            }
        });
    }
}
