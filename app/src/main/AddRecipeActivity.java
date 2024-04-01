package com.example.cs2340_project.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;
import com.example.cs2340_project.viewmodels.FoodDatabase;

import java.util.Arrays;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {
    private EditText recipeName;
    private EditText ingredientsNames;
    private EditText ingredientsQuantities;
    private EditText totalCalories; // Added EditText field
    private FoodDatabase foodDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe_activity);

        // Connect Database
        foodDatabase = FoodDatabase.getInstance();

        // Connect EditTexts
        recipeName = findViewById(R.id.name);
        ingredientsNames = findViewById(R.id.ingredients);
        ingredientsQuantities = findViewById(R.id.quantities);
        totalCalories = findViewById(R.id.totalCalories); // Connect totalCalories EditText

        // Connect Buttons
        Button addRecipeBtn = findViewById(R.id.addRecipeBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);

        // Set empty onClickListeners
        addRecipeBtn.setOnClickListener(v -> {
            String nameText = recipeName.getText().toString().trim();
            String ingredients = "";
            List<String> ingredientsNamesText =
                    Arrays.asList(ingredientsNames.getText().toString().split(","));
            List<String> ingredientsQuantitiesText =
                    Arrays.asList(ingredientsQuantities.getText().toString().split(","));

            if (nameText.isEmpty() || ingredientsNamesText.get(0).isEmpty()
                    || ingredientsQuantitiesText.get(0).isEmpty()) {
                Toast.makeText(AddRecipeActivity.this,
                        "Name, Lists of ingredients and quantities must not be empty",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (ingredientsNamesText.size() != ingredientsQuantitiesText.size()) {
                Toast.makeText(AddRecipeActivity.this,
                        "Lists of ingredients and quantities must be of the same size",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            for (int i = 0; i < ingredientsNamesText.size(); i++) {
                try {
                    int quantityText = Integer.parseInt(ingredientsQuantitiesText.get(i).trim());
                    if (quantityText <= 0) {
                        throw new NumberFormatException();
                    }

                    ingredients += quantityText + ingredientsNamesText.get(i).trim() + ",";

                } catch (NumberFormatException e) {
                    Toast.makeText(AddRecipeActivity.this,
                            "Quantities must positive numbers.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Get total calories from EditText
            String totalCaloriesText = totalCalories.getText().toString().trim();
            int totalCaloriesValue = 0;
            if (!totalCaloriesText.isEmpty()) {
                try {
                    totalCaloriesValue = Integer.parseInt(totalCaloriesText);
                    if (totalCaloriesValue <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(AddRecipeActivity.this,
                            "Total calories must be a valid number greater than 0.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(AddRecipeActivity.this, "Please enter total calories.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            foodDatabase.addRecipeToCookbook(nameText, ingredients, totalCaloriesValue);
            Toast.makeText(AddRecipeActivity.this,
                    "Recipe added successfully.", Toast.LENGTH_SHORT).show();

            recipeName.getText().clear();
            ingredientsNames.getText().clear();
            ingredientsQuantities.getText().clear();
            totalCalories.getText().clear(); // Clear totalCalories EditText
        });

        cancelBtn.setOnClickListener(v -> {
            finish();
        });
    }
}
