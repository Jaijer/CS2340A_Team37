package com.example.cs2340_project.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.model.Meal;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FoodDatabase extends ViewModel {
    // Singleton FoodDatabase class.
    private static volatile FoodDatabase database;
    private final DatabaseReference foodRef;

    private FoodDatabase() {
        // Initialize firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Reference to the "meals" node in FireBase
        foodRef = firebaseDatabase.getReference();
    }

    public static FoodDatabase getInstance() {
        if (database == null) {
            synchronized (FoodDatabase.class) {
                if (database == null) {
                    database = new FoodDatabase();
                }
            }
        }
        return database;
    }

    public DatabaseReference getFoodRef() {
        return foodRef;
    }


    public Task<Void> addMeal(String nameText, int intCalorieText, String userId) {
        Meal meal = new Meal(nameText, intCalorieText);

        // Save the meal under the user's ID in Firebase
        String key = foodRef.push().getKey();
        return foodRef.child("Meals").child(userId).child(key).setValue(meal);
    }

    public void addRecipeToCookbook(String name, String ingredients, Integer totalCalories) {
        // Add recipe to cookbook database
        String recipeKey = foodRef.push().getKey();
        assert recipeKey != null;
        foodRef.child("Cookbook").child(recipeKey).child("name").setValue(name);
        foodRef.child("Cookbook").child(recipeKey).
                child("ingredients").setValue(ingredients);
        foodRef.child("Cookbook").child(recipeKey).
                child("totalCalories").setValue(totalCalories);
    }

    public Task<Void> addIngredient(String nameText, int intQuantityText, int intCalories,
                                    String userId) {
        Ingredient ingredient = new Ingredient(nameText, intQuantityText, intCalories);

        String ingredientKey = foodRef.push().getKey();
        return foodRef.child("Pantry").child(userId).
                child(ingredientKey).setValue(ingredient);
    }

    public Task<DataSnapshot> getIngredientsForUser(String userId) {
        // Retrieve ingredients for the given user from the database
        return foodRef.child("Pantry").child(userId).get();
    }
}

