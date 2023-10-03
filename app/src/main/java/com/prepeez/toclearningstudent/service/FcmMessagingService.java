package com.prepeez.toclearningstudent.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by 2CLearning on 12/5/2017.
 */

public class FcmMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0){
            String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("message");
            Intent intent = new Intent("com.google.firebase.udacity.virtualclassroom_FCM-MESSAGE");
            intent.putExtra("title", title);
            intent.putExtra("message", message);
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(intent);
        }
    }
}
