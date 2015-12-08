package com.pagenguyen.elib.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.model.MultipleChoiceExercise;
import com.pagenguyen.elib.model.MultipleChoiceQuestion;

/**
 * Created by Numady Kuteko on 08/12/2015.
 */
public class MultipleChoiceAdapter extends BaseAdapter {

    private Context mContext;
    private MultipleChoiceExercise mExercise;
    private MultipleChoiceQuestion[] mQuestion;
    private Integer[] userAnswer;

    public MultipleChoiceAdapter(Context context, MultipleChoiceExercise exercise){
        mContext=context;
        mExercise=exercise;
        mQuestion=mExercise.getQuestionList();

        for (int i=0; i<mQuestion.length; i++){
            userAnswer[i]=-1;
        }
    }

    @Override
    public int getCount() {
        return mQuestion.length;
    }

    @Override
    public Object getItem(int position) {
        return mQuestion[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView= convertView;

        if (convertView==null){
            if (mQuestion[position] == null)     return convertView;

            String[] answer = mQuestion[position].getOption();
            String question=mQuestion[position].getQuestion();

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            rowView = inflater.inflate(R.layout.item_multiplechoice_question, null, true);

            TextView questions = (TextView) rowView.findViewById(R.id.question_content);
            CheckBox answer_1 = (CheckBox) rowView.findViewById(R.id.answer_1);
            CheckBox answer_2 = (CheckBox) rowView.findViewById(R.id.answer_2);
            CheckBox answer_3 = (CheckBox) rowView.findViewById(R.id.answer_3);
            CheckBox answer_4 = (CheckBox) rowView.findViewById(R.id.answer_4);

            questions.setText("Câu " + (position + 1) + ": " + question + " ?");
            answer_1.setText(answer[0]);
            answer_2.setText(answer[1]);
            answer_3.setText(answer[2]);
            answer_4.setText(answer[3]);

            setOnClickCheckbox(answer_1, answer_2, answer_3, answer_4, position);
        }

        return rowView;
    }

    private void setOnClickCheckbox(final CheckBox ans_1, final CheckBox ans_2,final CheckBox ans_3,final CheckBox ans_4, final int position){
        ans_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ans_1.isChecked()) {
                    userAnswer[position] = -1;
                }
                else{
                    userAnswer[position] = 0;
                }
                ans_2.setChecked(false);
                ans_3.setChecked(false);
                ans_4.setChecked(false);
            }
        });

        ans_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ans_2.isChecked()) {
                    userAnswer[position] = -1;
                }
                else{
                    userAnswer[position] = 1;
                }
                ans_1.setChecked(false);
                ans_3.setChecked(false);
                ans_4.setChecked(false);
            }
        });

        ans_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ans_3.isChecked()) {
                    userAnswer[position] = -1;
                }
                else{
                    userAnswer[position] = 2;
                }

                ans_2.setChecked(false);
                ans_1.setChecked(false);
                ans_4.setChecked(false);
            }
        });

        ans_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ans_4.isChecked()) {
                    userAnswer[position] = -1;
                }
                else{
                    userAnswer[position] = 3;
                }
                ans_2.setChecked(false);
                ans_3.setChecked(false);
                ans_1.setChecked(false);
            }
        });
    }

    public Integer[] getUserAnswer(){
        return this.userAnswer;
    }

}
