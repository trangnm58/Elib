package com.pagenguyen.elib.ui.exercise;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.model.Story;
import com.pagenguyen.elib.model.Topic;
import com.pagenguyen.elib.ui.main.HomeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExerciseResultActivity extends AppCompatActivity {
    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.resultScoreText) TextView mScoreView;
    @Bind(R.id.resultStatusText) TextView mStatusView;
    @Bind(R.id.numberRightAnswer) TextView mRightAnswers;
    @Bind(R.id.iconImageView) ImageView mStatusIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);
        ButterKnife.bind(this);

        setupToolbar();

        setResultView();
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
            case (R.id.action_home):{
                // Back to home page
                Intent intent = new Intent(ExerciseResultActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
            case (android.R.id.home): {
                Intent intent = new Intent(ExerciseResultActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
    }

    private void setResultView() {
         Intent intent = getIntent();
         int rightAnswers = intent.getIntExtra("right_answers", 0);
         int numQuestion = intent.getIntExtra("num_questions", 0);

         float score = (float)rightAnswers /numQuestion;

         score = score * 100;

         String status = "";

         if(score < 65){
             status = getResources().getString(R.string.bad_status_result);
             mStatusIcon.setImageResource(R.mipmap.ic_star_border_white_48dp);
         }
         else if(score >= 65 && score < 85){
             status = getResources().getString(R.string.normal_status_result);
             mStatusIcon.setImageResource(R.mipmap.ic_star_half_white_48dp);
         }
         else if(score >= 85){
             status = getResources().getString(R.string.good_status_result);
             mStatusIcon.setImageResource(R.mipmap.ic_star_white_48dp);
         }
         else {}

         mScoreView.setText(Math.round(score) + "%");
         mRightAnswers.setText("Số câu đúng: " + rightAnswers + "/" + numQuestion);
         mStatusView.setText(status);
    }
}
