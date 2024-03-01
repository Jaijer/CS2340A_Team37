package com.example.cs2340_project.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import com.example.cs2340_project.R;
import com.example.cs2340_project.viewmodels.LoginViewModel;

public class AccountCreationActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation_screen);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button signUpBtn = findViewById(R.id.signUpBtn);

        loginViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Toast.makeText(AccountCreationActivity.this,
                        "Account created successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccountCreationActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginViewModel.getAuthErrorLiveData().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(AccountCreationActivity.this,
                        errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        signUpBtn.setOnClickListener(v -> {
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            // Simple input validation example
            if (emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(AccountCreationActivity.this,
                        "Email and password cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            loginViewModel.createAccount(emailText, passwordText);
        });

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AccountCreationActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}