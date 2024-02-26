package com.example.cs2340_project.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;

public class RecipeActivity extends AppCompatActivity {
    private TextView recipeHeaderTextView;
    private Button recipeActivityButton;
    private Button ingredientActivityButton;
    private Button inputMealActivityButton;
    private Button shoppingListActivityButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity_screen);
        recipeHeaderTextView = findViewById(R.id.recipeHeaderTextView);

    }
}
