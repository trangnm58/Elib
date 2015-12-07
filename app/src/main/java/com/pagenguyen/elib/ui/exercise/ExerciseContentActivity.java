package com.pagenguyen.elib.ui.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pagenguyen.elib.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExerciseContentActivity extends AppCompatActivity {
    @Bind(R.id.exerciseTitleView) TextView mExerciseTitle;
    @Bind(R.id.submitAnswerButton) Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_content);
        ButterKnife.bind(this);

        setupToolbar();
        setExerciseTitle();
        submitExercise();
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
                Intent intent = new Intent(ExerciseContentActivity.this, ExerciseResultActivity.class);
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

    private void submitExercise() {
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseContentActivity.this, ExerciseResultActivity.class);
                startActivity(intent);
            }
        });
    }


}
