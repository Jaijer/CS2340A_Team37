package com.example.cs2340_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.views.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Set a delay for the splash screen before transitioning to the login activity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 3000);

        findViewById(R.id.Exit).setOnClickListener(view -> finish());
    }
}
