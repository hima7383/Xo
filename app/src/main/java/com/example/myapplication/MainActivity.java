package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=findViewById(R.id.button2);
        findViewById(R.id.logout).setOnClickListener(v -> SettingsBox.showSettingsDialog(this));
        b.setOnClickListener(view -> {
            Intent i=new Intent(this, LoginActivity.class);
            startActivity(i);
        });
    }

    private boolean isFirstLoad = true;

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_settings, null);
        builder.setView(dialogView);
        ImageView music=dialogView.findViewById(R.id.icon_music);
        ImageView sound=dialogView.findViewById(R.id.icon_sound);
        Spinner languageSpinner = dialogView.findViewById(R.id.spinner_language);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        UserManager userManager = new UserManager(this);
        String currentUser = userManager.getCurrentUser();
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
                userManager.updateClickSoundPreference(currentUser,false);
            }
            else{
                sound.setImageResource(R.drawable.speaker);
                userManager.updateClickSoundPreference(currentUser,true);
            }
        });
        music.setOnClickListener(view -> {
            if(userManager.getBackgroundMusicPreference(currentUser)){
                music.setImageResource(R.drawable.nomusic);
                userManager.updateBackgroundMusicPreference(currentUser,false);
            }
            else{
                music.setImageResource(R.drawable.music);
                userManager.updateBackgroundMusicPreference(currentUser,true);
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
                new Handler().postDelayed(() -> setLocale(langCode), 200);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    private void setSpinnerSelection(Spinner spinner, String langCode) {
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

    private String getLanguageCode(String language) {
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

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Restart the activity after a short delay
        new Handler().postDelayed(() -> {
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            finish();
        }, 200);  // Delay of 200ms
    }


}
