package com.foreverdevelopers.doctors_directory_kenya.data;

import okhttp3.OkHttpClient;

public class HttpClient {
    public OkHttpClient secureClient;
    public OkHttpClient unsecureClient;
    public HttpClient(){}
    public HttpClient(OkHttpClient secureClient, OkHttpClient unsecureClient){
        this.secureClient =secureClient;
        this.unsecureClient = unsecureClient;
    }
}
