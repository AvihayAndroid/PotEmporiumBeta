package com.example.potemporiumbeta1.Objects;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.potemporiumbeta1.Activities.LoginScreen;
import com.example.potemporiumbeta1.Activities.ShopFront;
import com.example.potemporiumbeta1.Activities.TradeCenter;

public class NotificationHelper {
    private static final String CHANNEL_ID = "your_Channel_ID";
    private static final String CHANNEL_NAME = "your_Channel_Name";
    private static final int NOTIFICATION_ID = 1;

    public static void showNotificationBtn(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, ShopFront.class);
        intent.putExtra("notification_clicked", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, intent, PendingIntent.FLAG_IMMUTABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("You have not entered the game in quite a while")
                .setContentText("Enter now?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel,
                        "OK", pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void showNotificationBtn2(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, TradeCenter.class);
        intent.putExtra("notification_clicked", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, intent, PendingIntent.FLAG_IMMUTABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Your shop has been renewed")
                .setContentText("Enter now?")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel,
                        "OK", pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }


}