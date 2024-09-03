package com.example.myapplication;

import android.content.Context;
import android.media.MediaPlayer;

public class onclicksound {
    public static boolean click=true;
    public static void clicksound(Context context){
        if(click) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.clicksound);
            mediaPlayer.start();
        }
    }
}
