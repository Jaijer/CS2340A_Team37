package com.example.cs2340_project.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;

public class IngredientActivity extends AppCompatActivity {
    private TextView ingredientHeaderTextView;
    private Button recipeActivityButton;
    private Button ingredientActivityButton;
    private Button inputMealActivityButton;
    private Button shoppingListActivityButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_activity_screen);
        ingredientHeaderTextView = findViewById(R.id.ingredientHeaderTextView);

    }
}
