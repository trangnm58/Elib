package com.pagenguyen.elib.model;

import com.pagenguyen.elib.R;

/**
 * Created by Can on 05-Dec-15.
 */
public class HomeMenuItem {
    private String mIcon;
    private String mLabel;
    private String mTarget;

    public HomeMenuItem() {
        mIcon = "";
        mLabel = "";
        mTarget = "";
    }

    public HomeMenuItem(String icon, String label, String target) {
        mIcon = icon;
        mLabel = label;
        mTarget = target;
    }

    public String getIcon() {
        return mIcon;
    }

    public int getIconInt() {
        switch (mIcon) {
            case ("book"): {
                return R.mipmap.ic_book_white_24dp;
            }
            case ("dictionary"): {
                return R.mipmap.ic_find_in_page_white_24dp;
            }
            case ("favorite"): {
                return R.mipmap.ic_favorite_white_24dp;
            }
            case ("game"): {
                return R.mipmap.ic_videogame_asset_white_24dp;
            }
            case ("grammar"): {
                return R.mipmap.ic_school_white_24dp;
            }
            case ("speak"): {
                return R.mipmap.ic_forum_white_24dp;
            }
            case ("topic"): {
                return R.mipmap.ic_filter_list_white_24dp;
            }
            case ("translator"): {
                return R.mipmap.ic_shuffle_white_24dp;
            }
            default: {
                return 0;
            }
        }
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getTarget() {
        return mTarget;
    }

    public void setTarget(String target) {
        mTarget = target;
    }
}
