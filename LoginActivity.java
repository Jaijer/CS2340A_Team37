package com.example.cs2340_project.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.cs2340_project.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth newAuth;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        newAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button signInBtn = findViewById(R.id.signInBtn);
        Button signUpBtn = findViewById(R.id.signUpBtn);

        signInBtn.setOnClickListener(newView -> {

            String email2 = email.getText().toString().trim();
            String password2 = password.getText().toString().trim();


            newAuth.signInWithEmailAndPassword(email2, password2)
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Login authentication failed. Please try again."
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });
        });


        signUpBtn.setOnClickListener(v -> {
            String email2 = email.getText().toString().trim();
            String password2 = password.getText().toString().trim();

            // Sign up
            newAuth.createUserWithEmailAndPassword(email2, password2)
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
