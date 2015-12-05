package com.pagenguyen.elib.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.pagenguyen.elib.R;

/**
 * Created by Kira on 12/3/2015.
 */
public class ElibDialog extends DialogFragment {

    public static ElibDialog newInstance(int type, String message){
        ElibDialog frag = new ElibDialog();
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("message", message);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int type = getArguments().getInt("type");
        String message = getArguments().getString("message");

        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        /*
        Three types of dialog
        int type = 0, dialog is success dialog
            type = 1, dialog is error dialog
            type = 2, dialog is warning dialog
         */

        if(type == 0){
            builder.setMessage(message)
                    .setPositiveButton(context.getString(R.string.ok_button), null);
        } else if(type == 1){
            builder.setMessage(message)
                    .setPositiveButton(context.getString(R.string.send_button), null)
                    .setNegativeButton(context.getString(R.string.no_button), null);
        } else if(type == 2){
            builder.setTitle(context.getString(R.string.warn_title))
                    .setMessage(message)
                    .setPositiveButton(context.getString(R.string.ok_button), null)
                    .setNegativeButton(context.getString(R.string.cancel_button), null);
        } else{
            Log.e("Error","Unknown Type");
        }

        return builder.create();
    }
}
