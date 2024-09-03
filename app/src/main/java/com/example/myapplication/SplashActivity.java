package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        userManager = new UserManager(this);

        // Delay the splash screen for a few seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, 1000); // 2-second delay
    }

    private void checkLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String currentUser = sharedPreferences.getString("currentUser", null);
        if (currentUser != null) {
            // User is logged in, navigate to MainActivity and pass the current user
            setLocale(userManager.getLanguagePreference(currentUser));
            Intent intent = new Intent(SplashActivity.this, Gamemode.class);
            intent.putExtra("currentUser", currentUser);
            startActivity(intent);
        } else {
            // No user is logged in, navigate to LoginActivity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        // Close the SplashActivity
        finish();
    }
    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
