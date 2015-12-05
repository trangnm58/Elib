package com.pagenguyen.elib.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pagenguyen.elib.R;

/**
 * Created by Kira on 12/4/2015.
 */
public class ElibAdapter extends BaseAdapter {
    private String[] mList;
    private Context mContext;
    private int mLayoutResourceId;
    private int mItemViewId;

    public ElibAdapter(Context context, int layoutId, int itemViewId, String[] array){
        mContext = context;
        mLayoutResourceId = layoutId;
        mItemViewId = itemViewId;
        mList = array;
    }

    @Override
    //The number you return in getCount()
    // is the times the getView() will be called
    public int getCount() {
        return mList.length;
    }

    @Override
    public Object getItem(int position) {
        return mList[position];
    }

    @Override
    //Tag items for easy reference
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
            holder.text = (TextView) convertView.findViewById(mItemViewId);

            convertView.setTag(holder);
        }
        else {
            //cast as view holder class
            holder = (ViewHolder) convertView.getTag();
        }

        //setting data
        holder.text.setText(mList[position]);


        return convertView;
    }

    public static class ViewHolder {
        TextView text;    //public by default
    }
}
