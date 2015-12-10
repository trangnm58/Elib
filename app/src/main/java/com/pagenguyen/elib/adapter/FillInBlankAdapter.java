package com.pagenguyen.elib.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.model.ParseConstants;
import com.pagenguyen.elib.ui.exercise.FillInBlanksActivity;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Kira on 12/8/2015.
 */
public class FillInBlankAdapter extends BaseAdapter{

    private List<ParseObject> mList;
    private Context mContext;
    private int mLayoutResourceId;

    //Array of user's answers
    private String[] uListAnswers;

    public FillInBlankAdapter(Context context, int layoutId, List<ParseObject> questions){
        mContext = context;
        mLayoutResourceId = layoutId;
        mList = questions;
        uListAnswers = new String[questions.size()];
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            /* Create view by inflating it from the context
            a layout inflater is an Android obj that takes x amount of layouts and
            turns them into views in code */
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.question = (TextView) convertView.findViewById(R.id.questionTextView);
            holder.uAnswer = (EditText) convertView.findViewById(R.id.answerInputField);
            holder.rAnswer = (TextView) convertView.findViewById(R.id.resultTextView);

            convertView.setTag(holder);
        }
        else {
            //cast as view holder class
            holder = (ViewHolder) convertView.getTag();
        }

        //setting data
        holder.question.setText(mList.get(position).getString(ParseConstants.EXERCISE_QUESTION));
        holder.rAnswer.setText(mList.get(position).getString(ParseConstants.EXERCISE_KEY));
        holder.rAnswer.setVisibility(View.GONE);

        //set Id for edit text and key text view
        holder.rAnswer.setId(position + 1000);
        holder.uAnswer.setId(position + 900);

        holder.uAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                uListAnswers[position] = s.toString();

                FillInBlanksActivity.mFillInBlanksMenu.getItem(0).setEnabled(true);

            }
        });

        //setting TextWatcher to EditText

        return convertView;
    }

    public static class ViewHolder {
        TextView question;    //public by default
        EditText uAnswer;
        TextView rAnswer;
    }

    //get user's answers
    public String[] getUAnswers(){
        return uListAnswers;
    }
}
