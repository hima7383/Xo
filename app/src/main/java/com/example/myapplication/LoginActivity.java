package com.example.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userManager = new UserManager(this); // Pass the context

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.Login_button);
        Button regButton=findViewById(R.id.register_button2);
        regButton.setOnClickListener(view -> {
            Intent intent=new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

       loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this,v.getContext().getString(R.string.Emptyfield) , Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isValid = userManager.checkLogin(username, password);
                if (isValid) {
                    setLocale(userManager.getLanguagePreference(username));
                    userManager.setUserLoggedIn(username,true);
                    Toast.makeText(LoginActivity.this, v.getContext().getString(R.string.Logintoast), Toast.LENGTH_SHORT).show();
                    Log.d("theusernow", userManager.getCurrentUser());
                    // Proceed to the next activity or main screen
                     Intent intent = new Intent(LoginActivity.this, Gamemode.class);
                     startActivity(intent);
                   // Close the login activity
                } else {
                    Toast.makeText(LoginActivity.this, v.getContext().getString(R.string.InvalidLogintoast), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userManager.close();
    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Restart the activity after a short delay
        // Delay of 200ms
    }


}
