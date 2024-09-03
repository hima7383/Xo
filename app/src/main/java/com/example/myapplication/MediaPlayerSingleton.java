package com.example.myapplication;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerSingleton {

    private static MediaPlayerSingleton instance;
    private MediaPlayer mediaPlayer;
    private Context context;
    private boolean isPlaying = false;

    // Private constructor to prevent instantiation
    private MediaPlayerSingleton(Context context) {
        this.context = context.getApplicationContext();
    }

    // Public method to provide access to the singleton instance
    public static synchronized MediaPlayerSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MediaPlayerSingleton(context);
        }
        return instance;
    }

    public void start() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.strangerthings);
            mediaPlayer.setLooping(true);
        }
        if (!isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    public void stop() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }

    public void restart() {
        stop();  // Stop and release the existing player
        start(); // Reinitialize and start the player again
    }
}
