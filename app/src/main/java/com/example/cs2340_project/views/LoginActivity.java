package com.example.cs2340_project.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.example.cs2340_project.R;
import com.example.cs2340_project.viewmodels.LoginViewModel;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;


import java.util.concurrent.atomic.AtomicReference;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        // Initialize ViewModel
        loginViewModel = new LoginViewModel();
        firebaseAuth = FirebaseAuth.getInstance();

        // Add code to handle the close button click
        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> finish());

        // Add code to handle the login button click
        Button loginButton = findViewById(R.id.signInBtn);
        loginButton.setOnClickListener(v -> {

            Log.d("LoginActivity", "Button clicked.");

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

            // Get username and password from ViewModel
            String username = loginViewModel.getUser().getUsername();
            String password = loginViewModel.getUser().getPassword();

            // Add Firebase authentication logic
            firebaseAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(LoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                        @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("LoginActivity", "User creation activated... (1/2)");

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                startActivity(intent);
                            } else {
                                Log.d("LoginActivity", "User creation activated... (2/2)");

                                Toast.makeText(LoginActivity.this,
                                        "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        // Add code to handle the sign up button click
        Button signUpButton = findViewById(R.id.signUpBtn);
        signUpButton.setOnClickListener(v -> {
            // Get username and password from ViewModel
            String username = loginViewModel.getUser().getUsername();
            String password = loginViewModel.getUser().getPassword();

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

            // Add Firebase authentication logic for account creation
            firebaseAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(LoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        startActivity(intent);
                                } else {
                                        Toast.makeText(LoginActivity.this,
                                                "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                            }
        });
        });
    }
}