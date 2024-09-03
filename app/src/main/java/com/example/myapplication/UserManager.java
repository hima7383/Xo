package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserManager {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context context;

    public UserManager(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Register a new user
    public boolean registerUser(String username, String password, boolean clickSound, boolean backgroundMusic, String language) {
        // Check if the user already exists
        if (getUser(username) != null) {
            return false; // User already exists
        }

        // Add new user
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("click_sound", clickSound ? 1 : 0);
        values.put("background_music", backgroundMusic ? 1 : 0);
        values.put("language", language);
        database.insert("users", null, values);
        return true; // User registered successfully
    }

    // Check login credentials
    public boolean checkLogin(String username, String password) {
        Cursor cursor = database.query("users", null, "username = ? AND password = ?", new String[]{username, password}, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    // Get user data
    public Cursor getUser(String username) {
        Cursor cursor = database.query("users", null, "username = ?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;
        }
        return null;
    }

    // Get click sound preference for the current user
    public boolean getClickSoundPreference(String username) {
        Cursor cursor = getUser(username);
        if (cursor != null) {
            int clickSoundIndex = cursor.getColumnIndex("click_sound");
            if (clickSoundIndex >= 0) {
                int clickSound = cursor.getInt(clickSoundIndex);
                cursor.close();
                return clickSound == 1;
            }
            cursor.close();
        }
        return false; // Default value if user not found
    }

    // Get background music preference for the current user
    public boolean getBackgroundMusicPreference(String username) {
        Cursor cursor = getUser(username);
        if (cursor != null) {
            int backgroundMusicIndex = cursor.getColumnIndex("background_music");
            if (backgroundMusicIndex >= 0) {
                int backgroundMusic = cursor.getInt(backgroundMusicIndex);
                cursor.close();
                return backgroundMusic == 1;
            }
            cursor.close();
        }
        return false; // Default value if user not found
    }

    // Get language preference for the current user
    public String getLanguagePreference(String username) {
        Cursor cursor = getUser(username);
        if (cursor != null) {
            int languageIndex = cursor.getColumnIndex("language");
            if (languageIndex >= 0) {
                String language = cursor.getString(languageIndex);
                cursor.close();
                return language;
            }
            cursor.close();
        }
        return "en"; // Default language if user not found
    }

    // Update click sound preference for the current user
    public void updateClickSoundPreference(String username, boolean clickSound) {
        ContentValues values = new ContentValues();
        values.put("click_sound", clickSound ? 1 : 0);
        database.update("users", values, "username = ?", new String[]{username});
    }

    // Update background music preference for the current user
    public void updateBackgroundMusicPreference(String username, boolean backgroundMusic) {
        ContentValues values = new ContentValues();
        values.put("background_music", backgroundMusic ? 1 : 0);
        database.update("users", values, "username = ?", new String[]{username});
    }

    // Update language preference for the current user
    public void updateLanguagePreference(String username, String language) {
        ContentValues values = new ContentValues();
        values.put("language", language);
        database.update("users", values, "username = ?", new String[]{username});
    }

    // Logout the user
    public void logoutUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.putString("currentUser", null);
        editor.apply();
    }

    // Check if the user is logged in
    public boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    // Set the user as logged in and save their username
    public void setUserLoggedIn(String username, boolean loggedIn) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", loggedIn);
        editor.putString("currentUser", username);
        editor.apply();
    }

    // Get the current logged-in user's username
    public String getCurrentUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("currentUser", null);
    }

    // Close the database when done
    public void close() {
        dbHelper.close();
    }
}