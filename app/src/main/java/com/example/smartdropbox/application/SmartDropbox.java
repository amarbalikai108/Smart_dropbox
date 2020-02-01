package com.example.smartdropbox.application;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.smartdropbox.utils.SharedPreferencesImp;

public class SmartDropbox extends Application
{
    SharedPreferencesImp sharedPrefs;
    public static String appVersionName;
    private static SmartDropbox singleton;


    public static String appSDKVerion;
    @Override
    public void onCreate() {
        super.onCreate();
        singleton=this;
        sharedPrefs=new SharedPreferencesImp(this);
        appVersionName=getAppVersion();
        appSDKVerion=appSDKVersion();
    }
    public static SmartDropbox getInstance(){
        return singleton;
    }
    private String getAppVersion()
    {
        String version="";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    private String appSDKVersion()
    {
        return android.os.Build.VERSION.SDK_INT+"";
    }
    public SharedPreferencesImp getAppSharePreference()
    {
        return sharedPrefs;
    }
}
