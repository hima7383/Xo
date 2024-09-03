package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerNames extends AppCompatActivity {
    TextView Player1,Player2;
    Button b;
    ImageView settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_names);
        Player1=findViewById(R.id.Player1NameAi);
        Player2=findViewById(R.id.Player2Name);
        b=findViewById(R.id.AiGameButton);
        settings=findViewById(R.id.settings_icon);
        settings.setOnClickListener(view -> {
            SettingsBox.showSettingsDialog(this);
            onclicksound.clicksound(this);
        });
        b.setOnClickListener(view -> {
            onclicksound.clicksound(view.getContext());
            String s=Player1.getText().toString(),s1=Player2.getText().toString();
            if(s.isEmpty()||s1.isEmpty()){
                Toast.makeText(this,view.getContext().getString(R.string.Emptyfield),Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent(this, TheGame.class);
                i.putExtra("Player1", s);
                i.putExtra("Player2",s1);
                i.putExtra("GameMode","Human");
                startActivity(i);
            }
        });
    }
}