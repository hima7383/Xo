package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerNameAi extends AppCompatActivity {
  TextView t;
  Button b;
  ImageView settings;
  UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name_ai);
        userManager=new UserManager(this);
        boolean click=userManager.getClickSoundPreference(userManager.getCurrentUser());
        t=findViewById(R.id.Player1NameAi);
        b=findViewById(R.id.AiGameButton);
        settings=findViewById(R.id.settings_icon);
        settings.setOnClickListener(view -> {
            SettingsBox.showSettingsDialog(view.getContext());
            onclicksound.clicksound(this);
        });
        b.setOnClickListener(view -> {
            onclicksound.clicksound(view.getContext());
            if(t.getText().toString().isEmpty()){
                Toast.makeText(this, view.getContext().getString(R.string.Emptyfield), Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent=new Intent(this, TheGame.class);
                intent.putExtra("GameMode","Ai");
                intent.putExtra("Player1",t.getText().toString());
                startActivity(intent);

            }
        });
    }
}