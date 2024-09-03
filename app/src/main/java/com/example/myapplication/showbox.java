package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

public class showbox {

    public static void showRestartDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.activity_alertbox, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button restartButton = dialogView.findViewById(R.id.button_restart);
        Button notNowButton = dialogView.findViewById(R.id.button_not_now);

        restartButton.setOnClickListener(v -> {
            restartApp(context); // Call method to restart the app
            dialog.dismiss();
        });

        notNowButton.setOnClickListener(v -> {
            dialog.dismiss(); // Just close the dialog
        });

        dialog.show();
    }

    private static void restartApp(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        Runtime.getRuntime().exit(0); // Forcefully close the app
    }
}
