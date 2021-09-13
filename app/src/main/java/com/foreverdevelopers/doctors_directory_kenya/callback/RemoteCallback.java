package com.foreverdevelopers.doctors_directory_kenya.callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public interface RemoteCallback {
    void onSuccess(String result);
    void onIOException(IOException ioException);
    void onServerError(JSONObject serverError);
    void onJSONException(JSONException jsonException);
}
