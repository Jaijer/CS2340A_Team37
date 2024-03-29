package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;

public class IngredientActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_activity_screen);
        TextView ingredientHeaderTextView = findViewById(R.id.ingredientHeaderTextView);
        Button homeActivityButton = findViewById(R.id.homeActivityButton);
        Button ingredientActivityButton = findViewById(R.id.ingredientActivityButton);
        Button inputMealActivityButton = findViewById(R.id.inputMealActivityButton);
        Button recipeActivityButton = findViewById(R.id.recipeActivityButton);
        Button shoppingListActivityButton = findViewById(R.id.shoppingListActivityButton);
        Button addIngredientButton = findViewById(R.id.addIngredientBtn);

        addIngredientButton.setOnClickListener(v -> {
            Intent intent = new Intent(IngredientActivity.this, IngredientForm.class);
            startActivity(intent);
        });

        homeActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(IngredientActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        ingredientActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(IngredientActivity.this, IngredientActivity.class);
            startActivity(intent);

        });

        inputMealActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(IngredientActivity.this, InputMealActivity.class);
            startActivity(intent);

        });

        recipeActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(IngredientActivity.this, RecipeActivity.class);
            startActivity(intent);

        });

        shoppingListActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(IngredientActivity.this, ShoppingListActivity.class);
            startActivity(intent);

        });

    }
}
