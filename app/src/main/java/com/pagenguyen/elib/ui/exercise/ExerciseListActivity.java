package com.pagenguyen.elib.ui.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.ElibAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExerciseListActivity extends AppCompatActivity {
    @Bind(R.id.exerciseListView) ListView mListExercise;

    public Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        ButterKnife.bind(this);

        setExerciseListView();

        setListItemClick();
    }

    private void setExerciseListView() {
        String[] mExercises = { "Bài tập 1",
                                "Bài tập 2",
                                "Bài tập 3" };

        ElibAdapter adapter = new ElibAdapter(ExerciseListActivity.this,
                R.layout.item_exercise_title,
                R.id.exerciseTitleText,
                mExercises);

        mListExercise.setAdapter(adapter);
    }

    private void setListItemClick() {
        mListExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView titleText = (TextView) view.findViewById(R.id.exerciseTitleText);
                String title = titleText.getText().toString();

                Intent intent = new Intent(ExerciseListActivity.this, ExerciseContentActivity.class);
                intent.putExtra("exercise_title", title);
                startActivity(intent);
            }
        });
    }
}
