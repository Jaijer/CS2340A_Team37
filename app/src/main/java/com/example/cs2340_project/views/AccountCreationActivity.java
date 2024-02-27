package com.example.cs2340_project.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import com.example.cs2340_project.R;
import com.google.firebase.auth.FirebaseAuth;

public class AccountCreationActivity extends AppCompatActivity {

    private FirebaseAuth newAuth;
    private EditText email;
    private EditText password;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation_screen); // Make sure you have a corresponding layout

        newAuth = FirebaseAuth.getInstance();

        // Initialize views
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUpBtn = findViewById(R.id.signUpBtn); // Ensure this ID exists in your account_creation_screen layout

        // Set onClick listener for the signUp button
        signUpBtn.setOnClickListener(v -> {
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            // Attempt to create a new user account
            newAuth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(AccountCreationActivity.this, task -> {
                        if (task.isSuccessful()) {
                            // On successful account creation, navigate to the HomeActivity
                            Intent intent = new Intent(AccountCreationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // On failure, show a toast message
                            Toast.makeText(AccountCreationActivity.this, "Account creation failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
