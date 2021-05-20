package com.dumb.dumb_deaf_system.preferance;

import android.content.Context;
import android.content.SharedPreferences;

public class sharedpreferance {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor prefsEditor;
    private static final String MY_SHAREDPREFS = "MY_SHAREDPREFS";

    private static final String USER_ID  = "ID";
    private static final String USER_NAME  = "NAME";
    private static final String USER_EMAIL  = "EMAIL";
    private static final String USER_ADDRESS  = "ADDRESS";

    private static final String USER_PHONE  = "PHONE";
    private static final String USER_TYPE  = "TYPE";
    private static final String USER_GENDER  = "GENDER";
    private static final String USER_IMAGE  = "IMAGE";

    public sharedpreferance(Context context) {
        int PRIVATE_MODE = 0;
        sharedPreferences = context.getSharedPreferences(MY_SHAREDPREFS, PRIVATE_MODE);
        prefsEditor = sharedPreferences.edit();
    }

    public void setlogindata(){

    }





}
