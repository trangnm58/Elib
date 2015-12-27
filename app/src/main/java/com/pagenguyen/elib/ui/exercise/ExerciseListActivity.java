package com.pagenguyen.elib.ui.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.ExerciseListAdapter;
import com.pagenguyen.elib.model.Exercise;
import com.pagenguyen.elib.model.FillInBlankExercise;
import com.pagenguyen.elib.model.MultipleChoiceExercise;
import com.pagenguyen.elib.model.Story;
import com.pagenguyen.elib.model.Topic;
import com.pagenguyen.elib.ui.main.HomeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExerciseListActivity extends AppCompatActivity {
    @Bind(R.id.exerciseListView) ListView mListExercise;
    @Bind(R.id.loadExerciseListView) ProgressBar mLoadList;

    public FillInBlankExercise[] mFillInBlanks;
    public MultipleChoiceExercise[] mMultipleChoices;

    public Story mStory;
    public Topic mTopic;

    public Exercise[] mAllExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        ButterKnife.bind(this);

        mLoadList.setVisibility(View.VISIBLE);

        setupToolbar();

        //set list of exercises view
        setExerciseListView();

        setListItemClick();
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
                Intent intent = new Intent(ExerciseListActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
            case (android.R.id.home): {
                onBackPressed();
                return true;
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

    private void setExerciseListView() {
        Intent intent = getIntent();

        if(intent.getParcelableExtra("MyObj") instanceof Story){
            mStory = intent.getParcelableExtra("MyObj");
            mFillInBlanks = mStory.getFillInBlankExercises();
            mMultipleChoices = mStory.getMultipleChoiceExercises();

            //Toast.makeText(ExerciseListActivity.this, mFillInBlanks.length+"", Toast.LENGTH_SHORT).show();
            //Toast.makeText(ExerciseListActivity.this, mMultipleChoices.length+"", Toast.LENGTH_SHORT).show();
        }
        else {
            mTopic = intent.getParcelableExtra("MyObj");
            mFillInBlanks = mTopic.getFillInBlankExercises();
            mMultipleChoices = mTopic.getMultipleChoiceExercises();

            //Toast.makeText(ExerciseListActivity.this,"I'm a topic",Toast.LENGTH_SHORT).show();
        }

        setListView();
    }

    private void setListView(){
        int filLength = (mFillInBlanks != null ? mFillInBlanks.length:0);
        int mulLength = (mMultipleChoices != null ? mMultipleChoices.length:0);

        mAllExercises = new Exercise[filLength + mulLength];

        int i = 0;
        //If have fill in blank exercises
        if(filLength != 0){
            for(int j=0; j < mFillInBlanks.length;){
                mAllExercises[2*i] = new Exercise();
                mAllExercises[2*i].setId(j);
                mAllExercises[2*i].setTitle("Bài tập " + (2 * i + 1));
                i++;
                j++;
            }
        }

        i = 0;
        //If have fill in blank exercises
        if(mulLength != 0) {
            for (int j = 0; j < mMultipleChoices.length; ) {
                mAllExercises[2 * i + 1] = new Exercise();
                mAllExercises[2 * i + 1].setId(j);
                mAllExercises[2 * i + 1].setTitle("Bài tập " + (2 * i + 2));
                i++;
                j++;
            }
        }

        ExerciseListAdapter adapter = new ExerciseListAdapter(ExerciseListActivity.this,
                R.layout.item_one_textview,
                mAllExercises);

        mListExercise.setAdapter(adapter);

        mLoadList.setVisibility(View.GONE);
    }

    private void setListItemClick() {
        mListExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                String exerciseId = "";

                //Fill in blank exercise
                if(position % 2 == 0){
                    exerciseId = mFillInBlanks[mAllExercises[position].getId()].getId();
                    intent = new Intent(ExerciseListActivity.this, FillInBlanksActivity.class);
                }

                //Multiple choice exercise
                else {
                    exerciseId = mMultipleChoices[mAllExercises[position].getId()].getId();
                    intent = new Intent(ExerciseListActivity.this, MultipleChoiceActivity.class);
                }

                intent.putExtra("exercise_id", exerciseId);
                startActivity(intent);
            }
        });
    }
}