package com.csm.projectcsm.util;

import static com.csm.projectcsm.util.Common.SYSTAG;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class Messaging extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        final String msg = "Token received: "+s;
        Log.d(SYSTAG, msg);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(!remoteMessage.getData().isEmpty()){
            Log.d(SYSTAG, "Message received: \n-----Start message-----");
            /*for(Map<String, String> messageItem : remoteMessage.getData()){
                Log.d(SYSTAG, "${messageItem.key} : ${messageItem.value}")
            }
            Log.d(SYSTAG, "-----End message-----");*/
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d(SYSTAG, "Message deleted");
    }

    @Override
    public void onSendError(@NonNull String s, @NonNull Exception e) {
        super.onSendError(s, e);
        Log.d(SYSTAG, "Message Send Error: "+ s);
    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);
        Log.d(SYSTAG, "Message sent: "+s);
    }

    @NonNull
    @Override
    protected Intent getStartCommandIntent(@NonNull Intent intent) {
        return super.getStartCommandIntent(intent);
    }

    @Override
    public void handleIntent(@NonNull Intent intent) {
        super.handleIntent(intent);
    }
}
