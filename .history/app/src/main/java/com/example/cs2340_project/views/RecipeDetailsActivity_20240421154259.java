package com.example.cs2340_project.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.viewmodels.FoodDatabase;


import java.util.ArrayList;
import java.util.List;




public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);

        // Retrieve extras from the intent
        String ingredientString = getIntent().getStringExtra("recipeIngredients");
        String[] recipeIngredientsArray = ingredientString.split(",");

        // Create list to hold ingredients
        List<Ingredient> recipeIngredients = new ArrayList<>();

        // Loop through ingredient strings and create Ingredient objects
        for (String ingredientStr : recipeIngredientsArray) {
            // Split each ingredient by the first digit (assuming the quantity always starts with a digit)
            String[] parts = ingredientStr.split("(?<=\\d)(?=\\D)");
            int quantity = Integer.parseInt(parts[0]);
            String name = parts[1];
            Ingredient ingredient = new Ingredient(name.trim(), quantity, 0);
            recipeIngredients.add(ingredient);
        }

        // Initialize views
        TextView recipeNameTextView = findViewById(R.id.recipeNameTextView);
        TextView caloriesTextView = findViewById(R.id.caloriesTextView);
        ListView ingredientsListView = findViewById(R.id.ingredientsListView);
        Button closeButton = findViewById(R.id.closeButton);
        Button ingButton = findViewById(R.id.addRIngBtn);


        // Set recipe name and calories
        recipeNameTextView.setText(getIntent().getStringExtra("recipeName"));
        caloriesTextView.setText(getIntent().getStringExtra("calories"));

        // Create an adapter for the ingredients list view
        RecipeDetailAdapter adapter = new RecipeDetailAdapter(this, recipeIngredients);
        ingredientsListView.setAdapter(adapter);

        // Handle close button click
        closeButton.setOnClickListener(v -> finish());
        ingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private FoodDatabase foodDatabase;
}
