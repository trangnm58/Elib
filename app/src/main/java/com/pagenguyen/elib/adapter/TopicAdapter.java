package com.pagenguyen.elib.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.database.ParseConstants;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Numady Kuteko on 19/12/2015.
 */
public class TopicAdapter extends BaseAdapter{
    private String[] mVocabulary;
    private String[] mMeaning;
    private Context mContext;

    public TopicAdapter(Context context, String[] vocabulary, String[] meaning){
        mContext=context;
        mMeaning=meaning;
        mVocabulary=vocabulary;
    }

    @Override
    public int getCount() {
        return mVocabulary.length;
    }

    @Override
    public Object getItem(int position) {
        return mVocabulary[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(convertView == null){
            /* Create view by inflating it from the context
            a layout inflater is an Android obj that takes x amount of layouts and
            turns them into views in code */
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            rowView = inflater.inflate(R.layout.item_topic_content, parent, false);

            TextView voca = (TextView) rowView.findViewById(R.id.itemVocabulary);
            TextView mean = (TextView) rowView.findViewById(R.id.itemMeaning);

            voca.setText(mVocabulary[position]);
            mean.setText(mMeaning[position]);
        }

        return rowView;
    }
}
