package com.example.cs2340_project.views;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private TextView headerTextView;
    private Button recipeActivityButton;
    private Button ingredientActivityButton;
    private Button inputMealActivityButton;
    private Button shoppingListActivityButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_screen);
        headerTextView = findViewById(R.id.headerTextView);

    }

}
