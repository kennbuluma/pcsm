package com.foreverdevelopers.doctors_directory_kenya.callback;

public interface StatusCallback {
    void onSuccess(String success);
    void onError(String error);
}
