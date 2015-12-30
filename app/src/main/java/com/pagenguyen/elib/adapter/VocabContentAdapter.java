package com.pagenguyen.elib.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagenguyen.elib.R;

/**
 * Created by Kira on 12/30/2015.
 */
public class VocabContentAdapter extends BaseAdapter{
    private String[] mMeaning;
    private String[] mExample;
    private Context mContext;

    public VocabContentAdapter(Context context, String[] meaning, String[] example){
        mContext = context;
        mMeaning = meaning;
        mExample = example;
    }

    @Override
    public int getCount() {
        if(mMeaning == null){
            return mExample.length;
        } else {
            return mMeaning.length;
        }
    }

    @Override
    public Object getItem(int position) {
        if(mMeaning == null || mMeaning[position] == null){
            return mExample[position];
        } else {
            return mMeaning[position];
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            /* Create view by inflating it from the context
            a layout inflater is an Android obj that takes x amount of layouts and
            turns them into views in code */
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_vocab_content, parent, false);

            holder = new ViewHolder();
            holder.meaning = (TextView) convertView.findViewById(R.id.meaningTextView);
            holder.engExam = (TextView) convertView.findViewById(R.id.engExamTextView);
            holder.vietExam = (TextView) convertView.findViewById(R.id.vietExamTextView);
            holder.meaningIcon = (ImageView) convertView.findViewById(R.id.meaningIcon);
            holder.engExamIcon = (ImageView) convertView.findViewById(R.id.engExamIcon);

            convertView.setTag(holder);
        }
        else {
            //cast as view holder class
            holder = (ViewHolder) convertView.getTag();
        }

        //setting data

        //Set meaning
        if(mMeaning != null && mMeaning[position] != null){
            holder.meaning.setText(mMeaning[position]);
            holder.meaningIcon.setVisibility(View.VISIBLE);

            //set structure: vocab - english example - vietnamese example
            if(mExample != null && mExample[2*position] != null) {
                holder.engExamIcon.setVisibility(View.VISIBLE);
                holder.engExam.setText(mExample[2*position]);
                holder.vietExam.setText(mExample[2*position+1]);
            } else { //do not have example
                holder.engExamIcon.setVisibility(View.GONE);
                holder.engExam.setVisibility(View.GONE);
                holder.vietExam.setVisibility(View.GONE);
            }
        } else {
            holder.meaning.setVisibility(View.GONE);
            holder.meaningIcon.setVisibility(View.GONE);

            //set structure: english example - vietnamese example
            //set two examples at same time
            if (mExample != null && position % 2 == 0 && mExample[position] != null) {
                holder.engExamIcon.setVisibility(View.VISIBLE);
                holder.engExam.setText(mExample[position]);
                holder.vietExam.setText(mExample[position + 1]);
            } else { //do not have example
                holder.meaning.setVisibility(View.GONE);
                holder.engExamIcon.setVisibility(View.GONE);
                holder.engExam.setVisibility(View.GONE);
                holder.vietExam.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    private class ViewHolder{
        TextView meaning;
        TextView engExam;
        TextView vietExam;
        ImageView meaningIcon;
        ImageView engExamIcon;
    }
}
