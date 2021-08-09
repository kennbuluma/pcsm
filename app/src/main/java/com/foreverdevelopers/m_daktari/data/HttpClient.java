package com.foreverdevelopers.m_daktari.data;

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
