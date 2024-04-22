package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Recipe;
import com.example.cs2340_project.viewmodels.FoodDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private FoodDatabase foodDatabase;
    private ListView listView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity_screen);

        Button homeActivityButton = findViewById(R.id.homeActivityButton);
        Button ingredientActivityButton = findViewById(R.id.ingredientActivityButton);
        Button inputMealActivityButton = findViewById(R.id.inputMealActivityButton);
        Button recipeActivityButton = findViewById(R.id.recipeActivityButton);
        Button shoppingListActivityButton = findViewById(R.id.shoppingListActivityButton);
        TextView recipeHeaderTextView = findViewById(R.id.recipeHeaderTextView);
        Button sortByButton = findViewById(R.id.sortByButton);
        Button addRecipeBtn = findViewById(R.id.addRecipeBtn);
        listView = findViewById(R.id.listView);

        foodDatabase = FoodDatabase.getInstance();

        // Retrieve the sorting type from the intent
        String sortType = getIntent().getStringExtra("sortType");

        // Initialize recipes list
        recipes = new ArrayList<>();

        // Set empty onClickListeners
        sortByButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(RecipeActivity.this, SortSelectionActivity.class);
            newIntent.putExtra("sortType", "ByDate"); // Default sorting type
            newIntent.putParcelableArrayListExtra("recipes", (ArrayList<? extends Parcelable>) recipes);
            startActivity(newIntent);
        });

        addRecipeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeActivity.this, AddRecipeActivity.class);
            startActivity(intent);
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

        // Retrieve recipes based on the sorting type
        if (sortType != null && sortType.equals("ByDate")) {
            // Fetch recipes from Firebase
            foodDatabase.getFoodRef().child("Cookbook").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    recipes.clear();
                    for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                        Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                        recipes.add(recipe);
                    }
                    // Update the ListView
                    recipeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                    Toast.makeText(RecipeActivity.this, "Failed to retrieve recipes", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Use default sorting method
            // Fetch recipes from Firebase
            foodDatabase.getFoodRef().child("Cookbook").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    recipes.clear();
                    for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                        Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                        recipes.add(recipe);
                    }
                    // Update the ListView
                    recipeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                    Toast.makeText(RecipeActivity.this, "Failed to retrieve recipes", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Initialize the adapter and set it to the ListView
        recipeAdapter = new RecipeAdapter(this, R.layout.recipe_list_item, recipes);
        listView.setAdapter(recipeAdapter);
    }
}
