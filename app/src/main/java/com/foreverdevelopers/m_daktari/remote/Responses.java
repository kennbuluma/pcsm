package com.foreverdevelopers.m_daktari.remote;

import org.json.JSONArray;

public class Responses {
    public void counties(){}
    public void facilities(){}
    public void services(){}
    public void doctors(){}

    public static class MainResponse{
        public Integer code;
        public String message, status;
        public JSONArray data;
        public MainResponse(){}
        public MainResponse(Integer code,
                            String message,
                            String status,
                            JSONArray data){
            this.code = code;
            this.message = message;
            this.status = status;
            this.data = data;
        }
    }
}
