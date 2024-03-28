package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.model.Recipe;
import com.example.cs2340_project.viewmodels.FoodDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private FoodDatabase foodDatabase;
    private EditText recipeName;
    private EditText ingredientsNames;
    private  EditText ingredientsQuantities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity_screen);
        Button homeActivityButton = findViewById(R.id.homeActivityButton);
        Button ingredientActivityButton = findViewById(R.id.ingredientActivityButton);
        Button inputMealActivityButton = findViewById(R.id.inputMealActivityButton);
        Button recipeActivityButton = findViewById(R.id.recipeActivityButton);
        Button shoppingListActivityButton = findViewById(R.id.shoppingListActivityButton);
        foodDatabase = FoodDatabase.getInstance();

        recipeName = findViewById(R.id.name);
        ingredientsNames = findViewById(R.id.ingredients);
        ingredientsQuantities = findViewById(R.id.quantities);
        Button addRecipeBtn = findViewById(R.id.addRecipeBtn);


        addRecipeBtn.setOnClickListener(v -> {
            String nameText = recipeName.getText().toString().trim();
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            List<String> ingredientsNamesText = Arrays.asList(ingredientsNames.getText().toString().split(","));
            List<String> ingredientsQuantitiesText = Arrays.asList(ingredientsQuantities.getText().toString().split(","));

            if(nameText.isEmpty() || ingredientsNamesText.get(0).isEmpty() || ingredientsQuantitiesText.get(0).isEmpty()) {
                System.out.println(ingredientsNamesText.size());
                System.out.println(ingredientsNamesText);
                Toast.makeText(RecipeActivity.this, "Name, Lists of ingredients and quantities must not be empty",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if(ingredientsNamesText.size() != ingredientsQuantitiesText.size()) {
                Toast.makeText(RecipeActivity.this, "Lists of ingredients and quantities must be of the same size",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            for(int i = 0; i < ingredientsNamesText.size(); i++) {
                try {
                    int quantityText = Integer.parseInt(ingredientsQuantitiesText.get(i).trim());
                    if (quantityText <= 0) {
                        throw new NumberFormatException();
                    }

                    ingredients.add(new Ingredient(ingredientsNamesText.get(i).trim(), quantityText, 0));

                } catch (NumberFormatException e) {
                    Toast.makeText(RecipeActivity.this, "Quantities must positive numbers.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            foodDatabase.addRecipeToCookbook(nameText, ingredients);

            recipeName.getText().clear();
            ingredientsNames.getText().clear();
            ingredientsQuantities.getText().clear();
        });

        homeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ingredientActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, IngredientActivity.class);
                startActivity(intent);

            }
        });

        inputMealActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, InputMealActivity.class);
                startActivity(intent);

            }
        });

        recipeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, RecipeActivity.class);
                startActivity(intent);

            }
        });

        shoppingListActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, ShoppingListActivity.class);
                startActivity(intent);

            }
        });

    }
}
