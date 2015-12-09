package com.pagenguyen.elib.ui.exercise;

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
import android.widget.ScrollView;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.MultipleChoiceAdapter;
import com.pagenguyen.elib.model.MultipleChoiceExercise;
import com.pagenguyen.elib.model.MultipleChoiceQuestion;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MultipleChoiceActivity extends AppCompatActivity {

    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.listQuestion) ListView mList;
    @Bind(R.id.title) TextView mExerciseTitle;

    MultipleChoiceExercise mExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        ButterKnife.bind(this);

        setupToolbar();

        fetchData();

        setExerciseTitle();

        setListQuestion();
    }

    // set data to mExercises:

    private void fetchData(){
        // demo:

        MultipleChoiceQuestion question;

        String[] option={"Anandi", "Sandick", "Kevin", "Julia"};
        question = new MultipleChoiceQuestion("What is your name", option, 2);

        MultipleChoiceQuestion[] questions = new MultipleChoiceQuestion[1];
        questions[0]=question;

        mExercises=new MultipleChoiceExercise("Chọn phương án đúng", questions);

        // official:

    /*  ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_MULTIPLE_CHOICE_EXERCISE);
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null || objects.size() == 0) {
                    ParseObject there=null;
                    String rightTitle="EXMC_GiaoDuc_1";
                    boolean found= false;

                    for (int i=0; i<objects.size(); i++){
                        ParseObject here = objects.get(i);
                        String title=here.getString(ParseConstants.EXERCISE_TITLE);

                        if (title.equals(rightTitle)){
                            there= here;
                            found= true;
                            break;
                        }
                    }

                    if (found) {
                        ArrayList<String> hello = (ArrayList<String>) there.get(ParseConstants.EXERCISE_QUESTION_LIST);

                        final MultipleChoiceQuestion[] questions = new MultipleChoiceQuestion[15];

                        for (int i=0; i<hello.size(); i++){
                            String id= hello.get(i).toString();
                            final int j=i;

                            ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_MULTIPLE_CHOICE_QUESTION);
                            query.getInBackground(id, new GetCallback<ParseObject>() {
                               public void done(ParseObject object, com.parse.ParseException e){
                                   if (e==null){
                                       MultipleChoiceQuestion temp= new MultipleChoiceQuestion((String) object.get(ParseConstants.EXERCISE_QUESTION), (String[]) object.get(ParseConstants.EXERCISE_OPTION), (int) object.get(ParseConstants.EXERCISE_KEY));
                                       questions[j] = temp;
                                   } else{
                                       // error
                                   }
                               }
                            });
                        }

                        mExercises = new MultipleChoiceExercise(rightTitle, questions);
                    }

                    else{

                    }

                } else {

                }
            }
        });

        */
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
                // call function to compute result:

                Intent intent = new Intent(MultipleChoiceActivity.this, ExerciseResultActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    // function to show key of exercise:

    private int showResult(){
        MultipleChoiceAdapter ans=(MultipleChoiceAdapter) mList.getAdapter();
        Integer[] userAnswer=ans.getUserAnswer();
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
            String red="F44336";
            String blue="2196F3";

            if (currentQuestion.checkKey(userAnswer[i])){
                // normal: mark plus plus:

                tempMark++;

                // extend:
            }

            else{
                if (userAnswer[i] == 0){
                    ans_1.setTextColor(Color.parseColor(red));
                }
                else if (userAnswer[i] == 1){
                    ans_2.setTextColor(Color.parseColor(red));
                }
                else if (userAnswer[i] == 2){
                    ans_3.setTextColor(Color.parseColor(red));
                }
                else if (userAnswer[i] == 3){
                    ans_4.setTextColor(Color.parseColor(red));
                }
            }

            int key=currentQuestion.getKey();

            if (key == 0){
                ans_1.setTextColor(Color.parseColor(blue));
            }
            else if (key == 1){
                ans_2.setTextColor(Color.parseColor(blue));
            }
            else if (key == 2){
                ans_3.setTextColor(Color.parseColor(blue));
            }
            else if (key == 3){
                ans_4.setTextColor(Color.parseColor(blue));
            }
        }

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
