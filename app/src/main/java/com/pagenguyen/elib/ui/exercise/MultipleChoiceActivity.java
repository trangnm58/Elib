package com.pagenguyen.elib.ui.exercise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.MultipleChoiceAdapter;
import com.pagenguyen.elib.model.MultipleChoiceExercise;
import com.pagenguyen.elib.model.MultipleChoiceQuestion;
import com.pagenguyen.elib.model.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MultipleChoiceActivity extends AppCompatActivity {

    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.listQuestion) ListView mList;
    @Bind(R.id.title) TextView mExerciseTitle;

    String title;

    MultipleChoiceExercise mExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        ButterKnife.bind(this);

        setupToolbar();

        title = getIntent().getStringExtra("topic_name");

        fetchData();

    }

    // set data to mExercises:

    private void fetchData(){

        // official:

        if (!title.equals("GIÁO DỤC") && !title.equals("GIA ĐÌNH")){
            title="Giáo Dục";
        }

        final String type=title;

        mExercises = null;

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_TOPIC);
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null && objects.size() != 0) {
                    ParseObject there=null;

                    boolean found= false;

                    for (int i=0; i<objects.size(); i++){
                        ParseObject here = objects.get(i);
                        String title=here.getString(ParseConstants.TITLE);

                        if (title.toLowerCase().equals(type.toLowerCase())){
                            there= here;
                            found= true;
                            break;
                        }
                    }

                    if (found) {
                        ParseQuery<ParseObject> exercises = ParseQuery.getQuery(ParseConstants.CLASS_MULTIPLE_CHOICE_EXERCISE);
                        exercises.whereEqualTo(ParseConstants.RELATION_BELONG_TO, there);

                        exercises.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (e == null && list.size() != 0) {
                                    ParseObject there = list.get(0);

                                    ParseQuery<ParseObject> question = ParseQuery.getQuery(ParseConstants.CLASS_MULTIPLE_CHOICE_QUESTION);
                                    question.whereEqualTo(ParseConstants.RELATION_BELONG_TO, there);

                                    question.findInBackground(new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> list, ParseException e) {
                                            if (e == null && list.size() != 0){

                                                MultipleChoiceQuestion[] questions = new MultipleChoiceQuestion[list.size()];

                                                for (int i=0; i< list.size(); i++){
                                                    ParseObject data=list.get(i);

                                                    int tempKey = data.getInt(ParseConstants.EXERCISE_KEY);
                                                    String tempQuestion = data.getString(ParseConstants.EXERCISE_QUESTION);

                                                    ArrayList<String> tempOption = (ArrayList<String>) data.get(ParseConstants.EXERCISE_OPTION);
                                                    String[] listOption = new String[tempOption.size()];

                                                    for (int k=0; k< tempOption.size();k++){
                                                        listOption[k] = tempOption.get(k);
                                                    }

                                                    MultipleChoiceQuestion temptemp = new MultipleChoiceQuestion(tempQuestion, listOption, tempKey);
                                                    questions[i] = temptemp;
                                                }

                                                mExercises= new MultipleChoiceExercise("Chọn phương án đúng: ", questions);

                                                setExerciseTitle();

                                                setListQuestion();


                                            } else{

                                            }
                                        }
                                    });

                                }else{

                                }
                            }
                        });
                    }

                    else {

                    }

                } else {

                }
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setExerciseTitle() {
        String title=mExercises.getTitle();
        mExerciseTitle.setText(title);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case (R.id.action_done):{
                AlertDialog.Builder builder = new AlertDialog.Builder(MultipleChoiceActivity.this);
                builder.setTitle(R.string.warn_title)
                        .setMessage(R.string.warn_message)
                        .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Done and submit user's answers
                                showResult();
                            }
                        })
                        .setNegativeButton(R.string.cancel_button, null);

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        }

        return super.onOptionsItemSelected(item);
    }

    // function to show key of exercise:

    private int showResult(){
        MultipleChoiceAdapter ans=(MultipleChoiceAdapter) mList.getAdapter();
        Integer[] userAnswer=ans.getUserAnswer();
        mExerciseTitle.setText("ĐÁP ÁN: ");

        int tempMark=0;

        // test user answer:

        /*String kq="Your answer is: ";
        for (int i=0; i<userAnswer.length; i++){
            int j=i+1;
            if (userAnswer[i] == 0) kq+=(""+j+".A  ");
            else if (userAnswer[i] == 1) kq+=(""+j+".B  ");
            else if (userAnswer[i] == 2) kq+=(""+j+".C  ");
            else if (userAnswer[i] == 3) kq+=(""+j+".D  ");
            else if (userAnswer[i] == -1) kq+=(""+j+".No_Answer  ");
        }*/

        //Toast.makeText(MultipleChoiceExercise.this, ""+ans.getCount(), Toast.LENGTH_SHORT).show();

        // set color of key and user answer:

        for (int i=0; i< ans.getCount(); i++) {
            View currentView = mList.getChildAt(i);
            MultipleChoiceQuestion currentQuestion = mExercises.getQuestionList()[i];

            if (currentView==null || currentQuestion==null) continue;

            CheckBox ans_1 = (CheckBox) currentView.findViewById(R.id.answer_1);
            CheckBox ans_2 = (CheckBox) currentView.findViewById(R.id.answer_2);
            CheckBox ans_3 = (CheckBox) currentView.findViewById(R.id.answer_3);
            CheckBox ans_4 = (CheckBox) currentView.findViewById(R.id.answer_4);

            ans_1.setClickable(false);
            ans_2.setClickable(false);
            ans_4.setClickable(false);
            ans_3.setClickable(false);

            if (currentQuestion.checkKey(userAnswer[i])){
                // normal: mark plus plus:

                tempMark++;

                // extend:
            }

            else{
                if (userAnswer[i] == 0){
                    ans_1.setTextColor(Color.RED);
                }
                else if (userAnswer[i] == 1){
                    ans_2.setTextColor(Color.RED);
                }
                else if (userAnswer[i] == 2){
                    ans_3.setTextColor(Color.RED);
                }
                else if (userAnswer[i] == 3){
                    ans_4.setTextColor(Color.RED);
                }
            }

            int key=currentQuestion.getKey();

            if (key == 0){
                ans_1.setTextColor(Color.BLUE);
            }
            else if (key == 1){
                ans_2.setTextColor(Color.BLUE);
            }
            else if (key == 2){
                ans_3.setTextColor(Color.BLUE);
            }
            else if (key == 3){
                ans_4.setTextColor(Color.BLUE);
            }
        }

        Toast.makeText(MultipleChoiceActivity.this, "Điểm của bạn là: "+ (tempMark*10) + "/100" , Toast.LENGTH_SHORT).show();
        return tempMark;
    }

    private void setListQuestion(){
        mList.setDividerHeight(0);
        MultipleChoiceAdapter mMCA=new MultipleChoiceAdapter(MultipleChoiceActivity.this, mExercises);
        mList.setAdapter(mMCA);
        setListViewHeightBasedOnChildren(mList);
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        MultipleChoiceAdapter listAdapter = (MultipleChoiceAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));

            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
