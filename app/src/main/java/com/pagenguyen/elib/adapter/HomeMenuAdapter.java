package com.pagenguyen.elib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.dataStructure.HomeMenuItem;

/**
 * Created by Can on 05-Dec-15.
 */
public class HomeMenuAdapter extends BaseAdapter {

    private Context mContext;
    private HomeMenuItem[] mMenuItems;

    public HomeMenuAdapter(Context context, HomeMenuItem[] menuItems) {
        mContext = context;
        mMenuItems = menuItems;
    }
    @Override
    public int getCount() {
        return mMenuItems.length;
    }

    @Override
    public HomeMenuItem getItem(int position) {
        return mMenuItems[position];
    }

    @Override
    public long getItemId(int position) {
        return 0; // we aren't going to use this. Tag items for easy reference
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_list_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.menuItemLabel = (TextView) convertView.findViewById(R.id.menuItemLabel);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomeMenuItem menuItem = mMenuItems[position];

        holder.iconImageView.setImageResource(menuItem.getIconInt());
        holder.menuItemLabel.setText(menuItem.getLabel());

        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView; // public by default
        TextView menuItemLabel;
    }
}
