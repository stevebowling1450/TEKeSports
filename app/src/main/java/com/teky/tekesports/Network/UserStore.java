//package com.teky.tekesports.Network;
//
//import android.content.SharedPreferences;
//
//import com.teky.tekesports.Components.Constants;
//import com.teky.tekesports.MainActivity;
//
//
///**
// * Created by lennyhicks on 11/29/16.
// */
//
//public class UserStore {
//    private static UserStore ourInstance = new UserStore();
//
//    public static UserStore getInstance() {
//        return ourInstance;
//    }
//
//    public String getToken() {
//        String theToken = MainActivity.sharedPrefs.getString(Constants.token, null);
//        return theToken;
//    }
//
//    public void setToken(String token) {
//        SharedPreferences.Editor editor = MainActivity.sharedPrefs.edit();
//        editor.putString(Constants.token, token);
//        editor.apply();
//    }
//
//
//}