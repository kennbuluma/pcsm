package com.csm.projectcsm.callback;

public interface StatusCallback {
    void onSuccess(String success);
    void onError(String error);
}
