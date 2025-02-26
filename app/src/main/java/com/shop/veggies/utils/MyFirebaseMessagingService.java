package com.shop.veggies.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shop.veggies.R;
import com.shop.veggies.activity.OfferActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "Shopinn";

    public void onMessageReceived(RemoteMessage remoteMessage) {
        String str = this.TAG;
        Log.d(str, "From: " + remoteMessage.getFrom());
        try {
            sendNotification(new JSONObject(new JSONObject(remoteMessage.getData().toString()).getJSONObject("body").toString()).getString("title"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        String str2 = this.TAG;
        Log.d(str2, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        if (remoteMessage.getData().size() > 0) {
            String str3 = this.TAG;
            Log.d(str3, "Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            String str4 = this.TAG;
            Log.d(str4, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            try {
                sendNotification(new JSONObject(new JSONObject(remoteMessage.getData().toString()).getJSONObject("body").toString()).getString("title"));
            } catch (JSONException ex2) {
                ex2.printStackTrace();
            }
        }
    }

    public void onNewToken(String token) {
        String str = this.TAG;
        Log.d(str, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        new AppPreferences(this).Set_FCMToken(token);
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, OfferActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE  // Use FLAG_IMMUTABLE if intent doesn't need modification
        );
        String channelId = getString(R.string.default_notification_channel_id);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId).setSmallIcon(R.drawable.new_logo).setColor(getResources().getColor(R.color.colorPrimary)).setContentTitle(getString(R.string.fcm_message)).setContentText(messageBody).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(2)).setContentIntent(pendingIntent);
        notificationBuilder.setDefaults(3);
        notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT));
        }
        notificationManager.notify(0, notificationBuilder.build());
    }
}
