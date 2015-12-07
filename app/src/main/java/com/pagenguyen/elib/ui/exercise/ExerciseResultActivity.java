package com.pagenguyen.elib.ui.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.OneTextviewAdapter;
import com.pagenguyen.elib.ui.main.HomeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExerciseResultActivity extends AppCompatActivity {
    @Bind(R.id.resultText) TextView mResultText;
    @Bind(R.id.resultStatusText) TextView mResultStatusText;
    @Bind(R.id.answerTitleText) TextView mAnswerTitleText;
    @Bind(R.id.resultBackHome) ImageView mHomeIcon;
    @Bind(R.id.answerListView) ListView mListAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);

        ButterKnife.bind(this);

        setAnswerListView();

        setListItemClick();

        setHomeIconClick();
    }

    private void setAnswerListView() {
        String[] mAnswers = { "Câu 1: a - Đúng",
                                "Câu 2: b - Đúng",
                                "Câu 3: b - Sai",
                                "Câu 4: c - Sai",
                                "Câu 5: d - Đúng",
                                "Câu 6: a - Sai",
                                "Câu 7: d - Đúng",
                                "Câu 8: c - Đúng",
                                "Câu 9: c - Sai",
                                "Câu 10: d - Sai" };

        OneTextviewAdapter adapter = new OneTextviewAdapter(ExerciseResultActivity.this,
                R.layout.item_one_textview,
                R.id.itemContent,
                mAnswers);

        mListAnswers.setAdapter(adapter);
    }

    private void setListItemClick() {

    }

    private void setHomeIconClick(){
        mHomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseResultActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
