package com.pagenguyen.elib.ui.exercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.FillInBlankAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FillInBlanksActivity extends AppCompatActivity {
    @Bind(R.id.exerciseTitleView) TextView mExerciseTitle;
    @Bind(R.id.questionListView) ListView mQuestionList;
    @Bind(R.id.exerciseStatusView) TextView mStatus;
    @Bind(R.id.exerciseScoreView) TextView mScore;

    public FillInBlankAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_blanks);
        ButterKnife.bind(this);

        //hide score and status view
        mScore.setVisibility(View.GONE);
        mStatus.setVisibility(View.GONE);

        setupToolbar();

        //set exercise's title
        setExerciseTitle();

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
                //Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(FillInBlanksActivity.this);
                builder.setTitle(R.string.warn_title)
                        .setMessage(R.string.warn_message)
                        .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Done and submit user's answers
                                submitAnswers();
                            }
                        })
                        .setNegativeButton(R.string.cancel_button, null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void submitAnswers() {
        String[] uAnswers = mAdapter.getUAnswers();

        for(String s:uAnswers){

        }

        mScore.setText("50%");
        mStatus.setText(R.string.bad_result_status);

        mScore.setVisibility(View.VISIBLE);
        mStatus.setVisibility(View.VISIBLE);
    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setExerciseTitle() {
        /*Intent intent = getIntent();
        String title = intent.getStringExtra("exercise_title");*/
        mExerciseTitle.setText("Bài tập điền từ vào chỗ trống");
    }

    private void setExcerciseContent() {
        String[] questions = {
                "She looked a bit  (1)  when they told her she didn't get the job.", //dejected
                "General Vo Nguyen Giap was a self-taught  (2)  who became one of the foremost military commanders of the 20th century",
                "My parents were celebrating 25 years of  (3).", //marrige
                "They warned that if no rain falls within the next two months, a crisis might  (4)  in the area", // prevail
                "The plant, a  (5)  of the arum lily family, is a native of Mexico and Guatemala.", //creeper
                "Thread is a type of  (6)  intended for sewing by hand or machine.", //yarn
                "I love magical things like  (7)  and goblins.",
                "The children squealed in  (8)  when they saw all the presents under the Christmas tree.", //delight
                "When we met again by chance in Cairo, I felt it must be  (9).",
                "Holidays and other special  (10)  are marked with singing and dancing."
        };

        mAdapter = new FillInBlankAdapter(FillInBlanksActivity.this,
                R.layout.fill_in_blanks_item,
                questions);

        mQuestionList.setAdapter(mAdapter);
    }
}
