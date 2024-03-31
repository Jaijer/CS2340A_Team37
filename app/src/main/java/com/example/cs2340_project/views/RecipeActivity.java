package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.model.Recipe;
import com.example.cs2340_project.viewmodels.FoodDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.widget.LinearLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


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
        displayRecipes();




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

        homeActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        ingredientActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeActivity.this, IngredientActivity.class);
            startActivity(intent);

        });

        inputMealActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeActivity.this, InputMealActivity.class);
            startActivity(intent);

        });

        recipeActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeActivity.this, RecipeActivity.class);
            startActivity(intent);

        });

        shoppingListActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeActivity.this, ShoppingListActivity.class);
            startActivity(intent);

        });

    }
    private void displayRecipes() {
        DatabaseReference cookbookRef = foodDatabase.getFoodRef().child("Cookbook");
        cookbookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LinearLayout recipeListLayout = findViewById(R.id.recipeListLayout);
                recipeListLayout.removeAllViews(); // Clear existing views if needed

                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    if (recipe != null) {
                        // Create a TextView for each recipe
                        TextView recipeNameTextView = new TextView(RecipeActivity.this);
                        recipeNameTextView.setText(recipe.getName());
                        recipeNameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        recipeNameTextView.setPadding(10, 10, 10, 10); // Set some padding if you like
                        // TODO: Set some styling or onClickListeners if needed

                        // Add TextView to the LinearLayout
                        recipeListLayout.addView(recipeNameTextView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RecipeActivity.this, "Failed to load recipes: " + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}
