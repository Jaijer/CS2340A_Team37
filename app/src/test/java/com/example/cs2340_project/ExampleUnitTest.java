package com.example.cs2340_project;

import com.example.cs2340_project.model.Recipe;
import com.example.cs2340_project.viewmodels.FoodDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RecipeActivityTest {

    private FoodDatabase foodDatabase;

    @Before
    public void setup() {
        foodDatabase = FoodDatabase.getInstance();
    }

    @Test
    public void testAddAndRetrieveRecipe() {
        // Add a recipe
        Recipe recipe = new Recipe("Test Recipe", "Test Ingredients", 5);
        foodDatabase.getFoodRef().child("Cookbook").push().setValue(recipe);

        // Retrieve the recipe
        foodDatabase.getFoodRef().child("Cookbook").orderByChild("name").equalTo("Test Recipe").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipe retrievedRecipe = recipeSnapshot.getValue(Recipe.class);

                    // Check that the retrieved recipe matches the one we added
                    assertEquals("Test Recipe", retrievedRecipe.getName());
                    assertEquals("Test Ingredients", retrievedRecipe.getIngredients());
                }
            }

            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}