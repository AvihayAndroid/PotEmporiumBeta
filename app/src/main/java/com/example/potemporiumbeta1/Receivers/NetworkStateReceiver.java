package com.example.potemporiumbeta1.Receivers;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
public class NetworkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent ri) {
        if (ri.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                // There is an active data connection

            } else {
                // There is no active data connection
                showMessage(context, false);
                Toast.makeText(context, "No data connection available", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showMessage(Context context, boolean isConnected) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Connection status")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform any action when the "OK" button is clicked
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform any action when the "Cancel" button is clicked
                        dialog.dismiss();
                    }
                });
        if (isConnected){
            builder.setMessage("Data connection available");
        } else {
            builder.setMessage("No data connection available,Many of the features will not work,Please sign out");
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}