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
import com.pagenguyen.elib.model.Exercise;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Kira on 12/9/2015.
 */
public class ExerciseListAdapter extends BaseAdapter{
    private Exercise[] mList;
    private Context mContext;
    private int mLayoutResourceId;

    public ExerciseListAdapter(Context context, int layoutId, Exercise[] array){
        mContext = context;
        mLayoutResourceId = layoutId;
        mList = array;
    }

    @Override
    public int getCount() {
        return mList.length;
    }

    @Override
    public Object getItem(int position) {
        return mList[position];
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
            convertView = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.itemContent);

            convertView.setTag(holder);
        }
        else {
            //cast as view holder class
            holder = (ViewHolder) convertView.getTag();
        }

        //setting data
        holder.text.setText(mList[position].getTitle());

        return convertView;
    }

    public static class ViewHolder {
        TextView text;    //public by default
    }
}
