package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

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
        Button sortByButton = findViewById(R.id.sortByButton);
        List<Ingredient> ingredients = new ArrayList<>();
        boolean defaultSort = true;


        // Retrieve the sorted ingredients from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("sortedIngredients")) {
            defaultSort = false;
            Log.d("IngredientActivity", "Ingredients passed");
            ingredients = intent.getParcelableArrayListExtra("sortedIngredients");

            // Clear the extra to avoid retaining the sorted list unnecessarily
            intent.removeExtra("sortedIngredients");

            // Log the list of ingredients
            for (Ingredient ingredient : ingredients) {
                Log.d("IngredientActivity", "Name: "
                        + ingredient.getName() + ", Quantity: " + ingredient.getQuantity()
                        + ", Calories: " + ingredient.getCalories());
            }
        }

        if (defaultSort) {
            // Retrieve ingredients for the current user
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            List<Ingredient> finalIngredients1 = ingredients;
            FoodDatabase.getInstance().getIngredientsForUser(userId)
                    .addOnSuccessListener(snapshot -> {
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            // Convert each ingredient data snapshot to an Ingredient object
                            Ingredient ingredient = childSnapshot.getValue(Ingredient.class);
                            finalIngredients1.add(ingredient);
                        }

                        IngredientAdapter ingredientAdapter =
                                new IngredientAdapter(this, finalIngredients1);

                        ListView listView = findViewById(R.id.listView);

                        listView.setAdapter(ingredientAdapter);
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors that occur while fetching ingredients
                        Toast.makeText(IngredientActivity.this,
                                "Failed to retrieve ingredients", Toast.LENGTH_SHORT).show();
                    });
        } else {
            if (ingredients != null && !ingredients.isEmpty()) {
                Log.d("IngredientActivity", "sortedIngredients is EMPTY!!");

                IngredientAdapter ingredientAdapter =
                        new IngredientAdapter(this, ingredients);

                ListView listView = findViewById(R.id.listView);

                listView.setAdapter(ingredientAdapter);
            } else if (ingredients.isEmpty()) {
                Log.d("IngredientActivity", "sortedIngredients is EMPTY!!");
            } else if (ingredients == null) {
                Log.d("IngredientActivity", "sortedIngredients is NULL!!");
            }
        }


        // Set OnClickListener for Sort By Button
        List<Ingredient> finalIngredients = ingredients;

        sortByButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(IngredientActivity.this,
                    SortSelectionActivity.class);
            newIntent.putParcelableArrayListExtra("ingredients",
                    (ArrayList<? extends Parcelable>) finalIngredients);
            startActivity(newIntent);
        });

        addIngredientButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(IngredientActivity.this,
                    IngredientForm.class);
            newIntent.putParcelableArrayListExtra("ingredients",
                    (ArrayList<? extends Parcelable>) finalIngredients);
            startActivity(newIntent);
        });

        homeActivityButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(IngredientActivity.this, HomeActivity.class);
            startActivity(newIntent);
        });

        ingredientActivityButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(IngredientActivity.this,
                    IngredientActivity.class);
            startActivity(newIntent);

        });

        inputMealActivityButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(IngredientActivity.this,
                    InputMealActivity.class);
            startActivity(newIntent);

        });

        recipeActivityButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(IngredientActivity.this,
                    RecipeActivity.class);
            startActivity(newIntent);

        });

        shoppingListActivityButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(IngredientActivity.this,
                    ShoppingListActivity.class);
            startActivity(newIntent);

        });

    }
}