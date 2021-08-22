package com.foreverdevelopers.m_daktari.util;

import android.content.Context;

import com.foreverdevelopers.m_daktari.R;
import com.foreverdevelopers.m_daktari.data.SSLItem;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLOps {
    public SSLItem getSSLFactory(Context context, InputStream inputStream) throws CertificateException,
            IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        SSLSocketFactory factory = null;
        X509TrustManager trustManager = null;
        final CertificateFactory cf = CertificateFactory.getInstance("x.509");
        final InputStream certInputStream = (null != inputStream) ? inputStream : context.getResources().openRawResource(R.raw.cert);
        final Certificate certificate = cf.generateCertificate(certInputStream);
        certInputStream.close();
        String keysStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keysStoreType);
        keyStore.load(null,null);
        keyStore.setCertificateEntry("ca",certificate);
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        TrustManager[] trustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
        if(null == trustManagers) return null;
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagers, null);
        if(trustManagers.length > 0 && trustManagers[0] instanceof X509TrustManager){
            trustManager = (X509TrustManager) trustManagers[0];
            factory = sslContext.getSocketFactory();
        }
        SSLItem returnItem = new SSLItem();
        returnItem.sslSocketFactory = factory;
        returnItem.trustManager = trustManager;
        return returnItem;

    }
    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers){
        X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        //TODO: Iplmenet X509 Validator
        return null;
    }
 }
