package com.pagenguyen.elib.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.model.SavedWord;

/**
 * Created by Numady Kuteko on 19/12/2015.
 */
public class WordListAdapter extends BaseAdapter{
    private String[] mVocabulary;
    private String[] mMeaning;
    private Context mContext;
    private boolean del = false;

    public WordListAdapter(Context context, String[] vocabulary, String[] meaning){
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View rowView = convertView;

        if(convertView == null){
            /* Create view by inflating it from the context
            a layout inflater is an Android obj that takes x amount of layouts and
            turns them into views in code */
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            rowView = inflater.inflate(R.layout.item_word_list, parent, false);

            TextView voca = (TextView) rowView.findViewById(R.id.itemVocabulary);
            TextView mean = (TextView) rowView.findViewById(R.id.itemMeaning);
            ImageButton button = (ImageButton) rowView.findViewById(R.id.favButton);

            voca.setText(mVocabulary[position]);
            mean.setText(mMeaning[position]);
            if(isDel()) {
                button.setImageResource(R.mipmap.ic_delete_white_24dp);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isDel()) {
                        Toast.makeText(
                                mContext,
                                "Đã xoá: " + mVocabulary[position].toLowerCase(),
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        SavedWord newWord = new SavedWord(mVocabulary[position], mMeaning[position]);
                        newWord.createParseObject();

                        Toast.makeText(
                                mContext,
                                "Đã thêm: " + mVocabulary[position].toLowerCase(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            });
        }

        return rowView;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }
}
