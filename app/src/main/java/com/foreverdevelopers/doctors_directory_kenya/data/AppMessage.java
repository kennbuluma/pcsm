package com.foreverdevelopers.doctors_directory_kenya.data;

import android.graphics.drawable.Drawable;

public class AppMessage {
    public String title, message;
    public Drawable icon;
    public AppMessage(){}
    public AppMessage(String title, String message){
        this.title = title;
        this.message = message;
    }
    public AppMessage(String title, String message, Drawable icon){
        this.title = title;
        this.message = message;
        this.icon = icon;
    }
}
