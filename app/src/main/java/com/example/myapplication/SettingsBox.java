package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

public class SettingsBox {
    private static boolean isFirstLoad = true;
    public static void showSettingsDialog(Context context) {
         MediaPlayerSingleton media=MediaPlayerSingleton.getInstance(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_settings, null);
        builder.setView(dialogView);
        Button logout=dialogView.findViewById(R.id.button_logout);
        ImageView music=dialogView.findViewById(R.id.icon_music);
        ImageView sound=dialogView.findViewById(R.id.icon_sound);
        Spinner languageSpinner = dialogView.findViewById(R.id.spinner_language);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        UserManager userManager = new UserManager(context);
        String currentUser = userManager.getCurrentUser();
        String currentlang=userManager.getLanguagePreference(currentUser);



        logout.setOnClickListener(view -> {
            userManager.logoutUser();
            Intent i =new Intent(context, LoginActivity.class);
            setLocale("en",context);
            media.stop();
            context.startActivity(i);
        });
        if (currentUser != null) {
            String currentLanguage = userManager.getLanguagePreference(currentUser);
            setSpinnerSelection(languageSpinner, currentLanguage);
        }
        if(userManager.getBackgroundMusicPreference(currentUser)){
            music.setImageResource(R.drawable.music);
        }
        else{
            music.setImageResource(R.drawable.nomusic);
        }
        if(userManager.getClickSoundPreference(currentUser)){
            sound.setImageResource(R.drawable.speaker);
        }
        else{
            sound.setImageResource(R.drawable.nospeaker);
        }
        Log.d("checkdatabase", userManager.getClickSoundPreference(currentUser)+"");
        sound.setOnClickListener(view -> {
            if(userManager.getClickSoundPreference(currentUser)){
                sound.setImageResource(R.drawable.nospeaker);
                onclicksound.click=false;
                userManager.updateClickSoundPreference(currentUser,false);
            }
            else{
                onclicksound.click=true;
                sound.setImageResource(R.drawable.speaker);
                userManager.updateClickSoundPreference(currentUser,true);
            }
        });
        music.setOnClickListener(view -> {
            if(userManager.getBackgroundMusicPreference(currentUser)){
                music.setImageResource(R.drawable.nomusic);
                media.stop();
                userManager.updateBackgroundMusicPreference(currentUser,false);
            }
            else{
                music.setImageResource(R.drawable.music);
                userManager.updateBackgroundMusicPreference(currentUser,true);
                media.restart();
            }
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstLoad) {
                    isFirstLoad = false;
                    return;
                }

                String selectedLang = (String) parent.getItemAtPosition(position);
                String langCode = getLanguageCode(selectedLang);
                userManager.updateLanguagePreference(currentUser, langCode);

                // Delay locale change
                new Handler().postDelayed(() -> setLocale(langCode,context), 200);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setPositiveButton(context.getString(R.string.Ok), (dialog, which) -> RefreshActivity(context,userManager,currentlang));
        builder.setNegativeButton(context.getString(R.string.Cancel), (dialog, which) -> dialog.dismiss());


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    private static void setSpinnerSelection(Spinner spinner, String langCode) {
        int position = 0;
        switch (langCode) {
            case "en":
                position = 0;
                break;
            case "de":
                position = 1;
                break;
            case "es":
                position = 2;
                break;
            case "fr":
                position = 3;
                break;
            case "ar":
                position = 4;
                break;
        }
        spinner.setSelection(position);
    }

    private static String getLanguageCode(String language) {
        switch (language) {
            case "English":
                return "en";
            case "German":
                return "de";
            case "Spanish":
                return "es";
            case "French":
                return "fr";
            case "Arabic":
                return "ar";
            default:
                return "en";
        }
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
    private static void RefreshActivity(Context context,UserManager userManager,String curlang){
        if(!Objects.equals(userManager.getLanguagePreference(userManager.getCurrentUser()), curlang)){
            showbox.showRestartDialog(context);
        }

    }

}
