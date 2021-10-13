package com.mydev.reminderapplication;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class BackgroudTimerService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, NotificationClicked.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(context, 0,i,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifierID")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Notifer")
                .setContentText("asd asd asd")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pi);

        NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
        nmc.notify(1, builder.build());


    }
}