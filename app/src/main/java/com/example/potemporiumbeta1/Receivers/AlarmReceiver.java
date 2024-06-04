package com.example.potemporiumbeta1.Receivers;

import com.example.potemporiumbeta1.Misc.NotificationHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent ri) {
        NotificationHelper.showNotificationBtn(context);
    }
}