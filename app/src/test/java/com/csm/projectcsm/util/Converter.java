package com.csm.projectcsm.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converter {
    @Test
    public static String bytesToString(byte[] input){
        if(null==input || input.length == 0) return null;
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }
    @Test
    public static byte[] stringToBytes(String input){
        if(null==input || input.trim().length() == 0) return null;
        return Base64.decode(input, Base64.DEFAULT);
    }
    @Test
    public static String bitmapToString(Bitmap input){
        if(null == input) return null;
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            input.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] bytes = outputStream.toByteArray();
            return bytesToString(bytes);
        }catch(IllegalArgumentException ex){
            return null;
        }
    }
    @Test
    public static Bitmap stringToBitmap(String input){
        if(null==input || input.trim().length() == 0) return null;
        try{
            byte[] bytes = stringToBytes(input);
            if(null== bytes || bytes.length == 0) return null;
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }catch(IllegalArgumentException ex){
            return null;
        }
    }
    @Test
    public static String dateToString(Date input){
        if(null==input) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ROOT);
        return sdf.format(input);
    }
    @Test
    public static Date stringToDate(String input){
        if(null==input || input.trim().length() == 0) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ROOT);
        try{
            return sdf.parse(input);
        }catch(ParseException ex){
            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
            try{
                return sdf.parse(input);
            }catch(ParseException ey){
                return null;
            }
        }
    }
    @Test
    public static boolean stringToBoolean(String input) throws IllegalArgumentException{
        if(null==input || input.trim().length() == 0) throw new IllegalArgumentException("Invalid boolean string");
        if("true".equalsIgnoreCase(input.trim())) return true;
        if("false".equalsIgnoreCase(input.trim())) return false;
        throw new IllegalArgumentException("Invalid boolean string");
    }
}
