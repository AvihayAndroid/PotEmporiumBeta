package com.example.potemporiumbeta1.Objects;

import com.example.potemporiumbeta1.Objects.NotificationHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent ri) {
        NotificationHelper.showNotificationBtn(context);
    }
}