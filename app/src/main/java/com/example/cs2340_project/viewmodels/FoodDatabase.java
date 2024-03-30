package com.example.cs2340_project.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.model.Meal;
import com.example.cs2340_project.model.Recipe;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FoodDatabase extends ViewModel {
    // Singleton FoodDatabase class.
    private volatile static FoodDatabase database;
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

    public void addRecipeToCookbook(String name, ArrayList<Ingredient> ingredients) {
        //add recipe to cookbook database
        String recipeKey = foodRef.push().getKey();
        foodRef.child("Cookbook").child(recipeKey).child("name").setValue(name);
        for(int i = 0; i< ingredients.size(); i++) {
            foodRef.child("Cookbook").child(recipeKey).child("Ingredients").child("Ingredient " + (i+1)).child("name").setValue(ingredients.get(i).getName());
            foodRef.child("Cookbook").child(recipeKey).child("Ingredients").child("Ingredient " + (i+1)).child("quantity").setValue(ingredients.get(i).getQuantity());
        }
    }

    public void addRecipeToUser(String name, ArrayList<Ingredient> ingredients) {
        //add recipe to usersRecipes database
    }

    public Task<Void> addIngredient(String nameText, int intQuantityText, int intCalories, String userId) {
        Ingredient ingredient = new Ingredient(nameText, intQuantityText, intCalories);

        String ingredientKey = foodRef.push().getKey();
        return foodRef.child("Pantry").child(userId).child(ingredientKey).setValue(ingredient);
    }

    public Task<DataSnapshot> getIngredientsForUser(String userId) {
        // Retrieve ingredients for the given user from the database
        return foodRef.child("Pantry").child(userId).get();
    }
}

