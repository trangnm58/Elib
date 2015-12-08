package com.pagenguyen.elib.ui.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.pagenguyen.elib.R;

import org.apmem.tools.layouts.FlowLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FillInBlanksActivity extends AppCompatActivity {
    @Bind(R.id.exerciseTitleView) TextView mExerciseTitle;
    @Bind(R.id.wordGridView) GridView mWordGrid;

    public FlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_blanks);
        ButterKnife.bind(this);

        setupToolbar();

        //set exercise's title
        setExerciseTitle();

        //set Word list on grid view
        setWordListView();

        //set exercise content
        setExcerciseContent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);
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
            case (R.id.action_done):{
                // TODO Dialog
                // TODO Check here do not use intent
                Intent intent = new Intent(FillInBlanksActivity.this, ExerciseResultActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setExerciseTitle() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("exercise_title");
        mExerciseTitle.setText(title);
    }

    private void setWordListView() {
        String[] words = {"a. rude",
                "b. keep",
                "c. happen"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                words);
        mWordGrid.setAdapter(adapter);
    }

    private void setExcerciseContent() {
        String[] sentences = {
                "I don't know who you are. But if you (1)",
                "",
                "I will say your mother to (2)",
                "",
                "you at home tonight.",
                "So that, please be polite to me if you don't want the bad thing (3) blablabla",
                "",
                "to you.",
                "Do you understand?",
        };

        mFlowLayout = (FlowLayout) findViewById(R.id.exerciseFlowLayout);

        int i=0;
        for(String s:sentences){
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);

            if(s == ""){
                EditText editText = new EditText(FillInBlanksActivity.this);
                editText.setId(i);
                editText.setText(s);

                //set style for EditText
                editText.setWidth(150);
                editText.setTop(100);
                editText.setGravity(17);       //center text
                params.setMargins(0,-30,0,0);

                editText.setLayoutParams(params);

                mFlowLayout.addView(editText);
            } else {
                TextView text = new TextView(FillInBlanksActivity.this);
                text.setId(i);
                text.setText(s + " ");

                //set style for EditText
                text.setTextSize(18);

                text.setLayoutParams(params);

                mFlowLayout.addView(text);
            }

            i++;
        }
    }
}
