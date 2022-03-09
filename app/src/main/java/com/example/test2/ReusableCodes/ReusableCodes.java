package com.example.test2.ReusableCodes;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class ReusableCodes {

    public static void ShowAlert(Context context, String title, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        }).setTitle(title).setMessage(message).show();
    }
}
