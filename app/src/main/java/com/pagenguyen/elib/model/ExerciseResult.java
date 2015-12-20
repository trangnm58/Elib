package com.pagenguyen.elib.model;

import android.support.v7.app.AppCompatActivity;

import com.pagenguyen.elib.R;

/**
 * Created by Kira on 12/6/2015.
 */
public class ExerciseResult extends AppCompatActivity {
    private int mScore;
    private String mStatus;

    public ExerciseResult(float score){
        mScore = Math.round(score*100);
        setStatusFromScore(mScore);
    }

    public void setStatusFromScore(int score){
        if(score <= 65){
            mStatus = "Bạn cần cố gắng hơn";
        } else if (score > 65 && score <= 85){
            mStatus = "Bạn có thể làm tốt hơn nữa!";
        } else if (score > 85 && score <= 100){
            mStatus = "Tuyệt vời!";
        } else {
            mStatus = "";
        }
    }

    public int getScore(){
        return Math.round(mScore);
    }

    public String getStatus(){
        return mStatus;
    }
}