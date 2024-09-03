package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userManager = new UserManager(this); // Pass the context

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        Button registerButton = findViewById(R.id.Login_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                boolean clickSound = true;
                boolean backgroundMusic =true;
                String language = "en";

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, v.getContext().getString(R.string.Emptyfield), Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isRegistered = userManager.registerUser(username, password, clickSound, backgroundMusic, language);
                if (isRegistered) {
                    Toast.makeText(RegisterActivity.this, v.getContext().getString(R.string.RegisterSuc), Toast.LENGTH_SHORT).show();
                    finish(); // Close the registration activity
                } else {
                    Toast.makeText(RegisterActivity.this, v.getContext().getString(R.string.InvalidRegisterSuc), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userManager.close();
    }
}
