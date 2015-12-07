package com.pagenguyen.elib.model;

import android.support.v7.app.AppCompatActivity;

import com.pagenguyen.elib.R;

/**
 * Created by Kira on 12/6/2015.
 */
public class ExerciseResult extends AppCompatActivity {
    public float mScore;
    public String mStatus;

    public ExerciseResult(float score){
        mScore = score;
        setStatus(score);
    }

    public void setStatus(float score){
        int intScore = Math.round(score);

        if(intScore*100 <= 65){
            mStatus = getResources().getString(R.string.bad_result_status);
        } else if (intScore*100 > 65 && intScore <= 85){
            mStatus = getResources().getString(R.string.normal_result_status);
        } else if (intScore*100 > 85 && intScore <= 100){
            mStatus = getResources().getString(R.string.good_result_status);
        } else {
            mStatus = "";
        }
    }

    public float getScore(){
        return mScore;
    }
}
