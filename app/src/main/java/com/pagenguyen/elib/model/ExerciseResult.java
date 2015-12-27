package com.pagenguyen.elib.model;

import android.support.v7.app.AppCompatActivity;

import com.pagenguyen.elib.R;

/**
 * Created by Kira on 12/6/2015.
 */
public class ExerciseResult extends AppCompatActivity {
    private float mScore;
    private String mStatus;
    private int mIcon;

    public ExerciseResult(float score){
        setStatusFromScore(score*100);
        mScore = Math.round(score*100);
    }

    public void setStatusFromScore(float score){
        if(score <= 65){
            mStatus = "Chưa đạt ! Bạn cần cố gắng hơn";
            mIcon = R.mipmap.ic_star_border_white_48dp;
        } else if (score > 65 && score <= 85){
            mStatus = "Bạn có thể làm tốt hơn nữa";
            mIcon = R.mipmap.ic_star_half_white_48dp;
        } else if (score > 85 && score <= 100){
            mStatus = "Tuyệt vời !";
            mIcon = R.mipmap.ic_star_white_48dp;
        } else {
            mStatus = "";
            mIcon = 0;
        }
    }

    public int getScore(){
        return Math.round(mScore);
    }

    public String getStatus(){
        return mStatus;
    }

    public int getIcon(){
        return mIcon;
    }
}