package com.example.cs2340_project.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.viewmodels.FoodDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecipeDetailsActivity extends AppCompatActivity {

    private FoodDatabase foodDatabase;

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

        ingButton.setOnClickListener(v -> {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference pantryRef = FirebaseDatabase.getInstance().getReference().child("Pantry").child(userId);

            // Fetch existing ingredients from Firebase
            pantryRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Map<String, Ingredient> currentIngredients = new HashMap<>();
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Ingredient ingredient = child.getValue(Ingredient.class);
                            if (ingredient != null) {
                                currentIngredients.put(child.getKey(), ingredient);
                            }
                        }
                    }

                    // Check each new ingredient against existing ones
                    for (Ingredient newIngredient : recipeIngredients) {
                        boolean found = false;
                        for (Map.Entry<String, Ingredient> entry : currentIngredients.entrySet()) {
                            Ingredient existingIngredient = entry.getValue();
                            if (existingIngredient.getName().equalsIgnoreCase(newIngredient.getName())) {
                                // Ingredient exists, update its quantity
                                int newQuantity = existingIngredient.getQuantity() + newIngredient.getQuantity();
                                pantryRef.child(entry.getKey()).child("quantity").setValue(newQuantity);
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            // Ingredient does not exist, add new
                            String key = pantryRef.push().getKey();
                            pantryRef.child(key).setValue(new Ingredient(newIngredient.getName(), newIngredient.getQuantity(), 100)); // Assuming calorie value is 100 for all new entries
                        }
                    }

                    Toast.makeText(RecipeDetailsActivity.this, "Ingredients added or updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RecipeDetailsActivity.this, "Failed to retrieve ingredients", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}
