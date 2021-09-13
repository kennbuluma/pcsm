package com.foreverdevelopers.doctors_directory_kenya.data;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

public class SSLItem {
    public SSLSocketFactory sslSocketFactory;
    public X509TrustManager trustManager;
    public SSLItem(){}
    public SSLItem(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager){
        this.sslSocketFactory = sslSocketFactory;
        this.trustManager =trustManager;
    }
}
