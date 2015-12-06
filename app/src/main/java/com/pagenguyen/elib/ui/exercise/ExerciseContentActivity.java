package com.pagenguyen.elib.ui.exercise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pagenguyen.elib.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExerciseContentActivity extends Activity {
    @Bind(R.id.exerciseTitleView) TextView mExerciseTitle;
    @Bind(R.id.submitAnswerButton) Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_content);

        ButterKnife.bind(this);

        //set exercise's title
        setExerciseTitle();

        //done and submit user's answers
        submitExercise();
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
