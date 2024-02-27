package com.example.cs2340_project.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cs2340_project.R;

import android.widget.Button;
import android.view.View;

import com.example.cs2340_project.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        // Add code to handle the close button click
        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> finish());
    }
}