package com.foreverdevelopers.m_daktari.util;

import com.foreverdevelopers.m_daktari.data.HttpClient;

import okhttp3.MediaType;

public class Common {
    public static final String SYSTAG = "PCSM";
    public static class Network{
        public static String AUTH_HEADER = "Authorization",
            AUTH_CONTENT = "Bearer ";
        public static MediaType JSON_CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8"),
            MULTIPART_CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded"),
            OCTET_STREAM_TYPE = MediaType.parse("application/octet-stream");
    }
    public static HttpClient initializeClient(){
        return null;
    }
}
