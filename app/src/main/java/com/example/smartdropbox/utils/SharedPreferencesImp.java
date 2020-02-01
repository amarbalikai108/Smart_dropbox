package com.example.smartdropbox.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesImp {
    private final String MY_PREFS = "MY_PREFS";
    private String REGISTERED = "registered";
    private String USERNAME = "user_name";
    private String PHONENO = "phone_no";
    private String ROUTE = "route";
    private String USER_CLASS = "CLASS";
    private String EMPID = "emp_id";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPreferencesImp(Context context) {
        sharedPreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean isRegistered() {
        return sharedPreferences.getBoolean(REGISTERED, false);
    }

    public void putRegistered(boolean isRegister) {
        editor.putBoolean(REGISTERED, isRegister);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(USERNAME, "");
    }

    public void putUserName(String userName) {

        editor.putString(USERNAME, userName);
        editor.apply();
    }

    public String getPhoneNo() {
        return sharedPreferences.getString(PHONENO, "");
    }

    public void putPhoneNo(String phoneNo) {
        editor.putString(PHONENO, phoneNo);
        editor.apply();
    }

    public String getRoute() {
        return sharedPreferences.getString(ROUTE, "");
    }

    public void putRoute(String route) {
        editor.putString(ROUTE, route);
        editor.apply();
    }
    public String getUserClass() {
        return sharedPreferences.getString(USER_CLASS, "");
    }

    public void putUserClass(String route) {
        editor.putString(USER_CLASS, route);
        editor.apply();
    }

    public String getEmpId() {
        return sharedPreferences.getString(EMPID, "");
    }

    public void putEmpId(String empId) {
        editor.putString(EMPID, empId);
        editor.apply();
    }

}

