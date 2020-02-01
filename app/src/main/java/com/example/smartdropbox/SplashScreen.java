package com.example.smartdropbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.smartdropbox.application.SmartDropbox;
import com.example.smartdropbox.home.view.HomeScreen;
import com.example.smartdropbox.registration.view.RegistrationScreen;
import com.example.smartdropbox.utils.SharedPreferencesImp;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    private SharedPreferencesImp sharedPreferencesImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferencesImp= SmartDropbox.getInstance().getAppSharePreference();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferencesImp.isRegistered()) {
                    Intent i = new Intent(SplashScreen.this,
                            HomeScreen.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(SplashScreen.this,
                            RegistrationScreen.class);
                    startActivity(i);
                }
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}


