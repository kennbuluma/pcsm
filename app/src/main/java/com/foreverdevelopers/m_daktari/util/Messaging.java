package com.foreverdevelopers.m_daktari.util;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.foreverdevelopers.m_daktari.AppViewModel;
import com.foreverdevelopers.m_daktari.data.FireMessageSendError;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Messaging extends FirebaseMessagingService {
    private AppViewModel viewModel = null;
    public Messaging(){}
    public Messaging(AppViewModel viewModel){
        this.viewModel = viewModel;
    }
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        if(null!=viewModel) viewModel.setFireMessageToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(null!=viewModel) viewModel.setFireMessage(remoteMessage);
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onSendError(@NonNull String s, @NonNull Exception e) {
        super.onSendError(s, e);
        if(null!=viewModel) viewModel.setFireMessageSendError(new FireMessageSendError(s,e));
    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);
        if(null!=viewModel) viewModel.setFireMessageSent(s);
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
