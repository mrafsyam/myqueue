package com.nextslides.queue;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";


    /**
     * Called when GCM message is received
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        //TODO : Cater for receiving real time counter update

        sendNotification(message);
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(this, DisplayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setWhen(0)
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .setContentTitle("MyQueue")
                        .setContentText(message)
                        .setTicker(message)
                        .setCategory(Notification.CATEGORY_ALARM)
                        .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setVibrate(new long[]{100, 250, 100, 250, 100, 250})
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message));
        notificationManager.notify(1, builder.build());
    }
}
