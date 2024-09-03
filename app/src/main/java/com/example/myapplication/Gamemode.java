package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Locale;

public class Gamemode extends AppCompatActivity {
    Button Ai,Human;
    UserManager userManager;
    MediaPlayerSingleton music;
    ImageView settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemode);
        userManager=new UserManager(this);
        setLocale(userManager.getLanguagePreference(userManager.getCurrentUser()),this);
        Ai=findViewById(R.id.Aigame);
        settings=findViewById(R.id.settings_icon);
        Human=findViewById(R.id.Humangame);
        music=MediaPlayerSingleton.getInstance(this);
        if(userManager.getBackgroundMusicPreference(userManager.getCurrentUser())){
            music.start();
        }
        if(userManager.getClickSoundPreference(userManager.getCurrentUser())){
            onclicksound.click=true;
        }
        else{
            onclicksound.click=false;
        }
        settings.setOnClickListener(view -> {
            SettingsBox.showSettingsDialog(this);
            onclicksound.clicksound(view.getContext());
        });
        Human.setOnClickListener(view -> {
            Intent i =new Intent(this,PlayerNames.class);
           onclicksound.clicksound(view.getContext());
            startActivity(i);
        });
        Ai.setOnClickListener(view -> {
            Intent i = new Intent(this,PlayerNameAi.class);
           onclicksound.clicksound(view.getContext());
            startActivity(i);
        });
    }
    private static void setLocale(String langCode, Context context) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        // Restart the activity after a short delay
        // Delay of 200ms
    }
}