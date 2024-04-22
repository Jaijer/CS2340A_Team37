package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_activity_screen);
        TextView shoppingListHeaderTextView = findViewById(R.id.shoppingListHeaderTextView);
        Button homeActivityButton = findViewById(R.id.homeActivityButton);
        Button ingredientActivityButton = findViewById(R.id.ingredientActivityButton);
        Button inputMealActivityButton = findViewById(R.id.inputMealActivityButton);
        Button recipeActivityButton = findViewById(R.id.recipeActivityButton);
        Button shoppingListActivityButton = findViewById(R.id.shoppingListActivityButton);
        Button addCartButton = findViewById(R.id.addCartBtn);
        Button sortCartButton = findViewById(R.id.sortCartBtn);
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
                Log.d("IngredientActivity", "Name: " + ingredient.getName() + ", Quantity: " + ingredient.getQuantity() + ", Calories: " + ingredient.getCalories());
            }
        }

        if (defaultSort) {
            // Retrieve ingredients for the current user
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            List<Ingredient> finalIngredients1 = ingredients;
            FoodDatabase.getInstance().getIngredientsForCart(userId)
                    .addOnSuccessListener(snapshot -> {
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            // Convert each ingredient data snapshot to an Ingredient object
                            Ingredient ingredient = childSnapshot.getValue(Ingredient.class);
                            finalIngredients1.add(ingredient);
                        }

                        ShoppingAdapter shoppingAdapter = new ShoppingAdapter(this, finalIngredients1);

                        ListView listView = findViewById(R.id.cartListView);

                        listView.setAdapter(shoppingAdapter);
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors that occur while fetching ingredients
                        Toast.makeText(ShoppingListActivity.this, "Failed to retrieve ingredients", Toast.LENGTH_SHORT).show();
                    });
        } else {
            if (ingredients != null && !ingredients.isEmpty()) {
                Log.d("IngredientActivity", "sortedIngredients is EMPTY!!");

                ShoppingAdapter shoppingAdapter = new ShoppingAdapter(this, ingredients);

                ListView listView = findViewById(R.id.cartListView);

                listView.setAdapter(shoppingAdapter);
            } else if (ingredients.isEmpty()) {
                Log.d("IngredientActivity", "sortedIngredients is EMPTY!!");
            } else if (ingredients == null) {
                Log.d("IngredientActivity", "sortedIngredients is NULL!!");
            }
        }


        // Set OnClickListener for Sort By Button
        List<Ingredient> finalCart = ingredients;

        sortCartButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(ShoppingListActivity.this, SortSelectionActivity.class);
            newIntent.putParcelableArrayListExtra("ingredients", (ArrayList<? extends Parcelable>) finalCart);
            startActivity(newIntent);
        });

        addCartButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(ShoppingListActivity.this, ShoppingForm.class);
            newIntent.putParcelableArrayListExtra("cartIngredients", (ArrayList<? extends Parcelable>) finalCart);
            startActivity(newIntent);
        });



        homeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingListActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ingredientActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingListActivity.this, IngredientActivity.class);
                startActivity(intent);

            }
        });

        inputMealActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingListActivity.this, InputMealActivity.class);
                startActivity(intent);

            }
        });

        recipeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingListActivity.this, RecipeActivity.class);
                startActivity(intent);

            }
        });

        shoppingListActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingListActivity.this, ShoppingListActivity.class);
                startActivity(intent);

            }
        });

    }
}
