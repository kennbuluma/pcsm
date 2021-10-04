package com.foreverdevelopers.doctors_directory_kenya.util;

import android.Manifest;

import com.foreverdevelopers.doctors_directory_kenya.data.HttpClient;

import java.util.List;

import okhttp3.MediaType;

public class Common {
    public static final String SYSTAG = "MDkt";
    public static final int PERMISSION_REQUESTCODE = 5;
    public static final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS
    };
    public static final String RA_COUNTIES = "counties",
            RA_COUNTIES_BY_SERVICE = "countyServices",
            RA_COUNTIES_BY_FACILITY = "countyFacilities",
            RA_FACILITIES = "facilities",
            RA_FACILITIES_BY_COUNTY = "facilitiesCounty",
            RA_SERVICES = "services",
            RA_SERVICES_BY_COUNTY = "servicesCounty",
            RA_SERVICES_BY_FACILITY = "servicesFacility",
            RA_DOCTORS = "doctors",
            RA_DOCTORS_BY_FACILITY = "doctorsFacility",
            RA_DOCTORS_BY_SERVICE = "doctorsService",
            RA_DOCTOR_DETAILS = "doctordetails";

    public static int stringSearch(List<String> objects, String value){
        int low = 0;
        int high = objects.size() -1;
        while (low <= high){
            int mid = low + (high - low) / 2;
            int guess = value.compareToIgnoreCase(objects.get(mid));
            if(guess == 0) return mid;
            if(guess > 0){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }
        return -1;
    }

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
