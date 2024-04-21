package com.example.cs2340_project.viewmodels;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.model.Meal;
import com.example.cs2340_project.model.Recipe;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoodDatabase extends ViewModel {
    // Singleton FoodDatabase class.
    private volatile static FoodDatabase database;
    private final DatabaseReference foodRef;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private List<DataObserver<Recipe>> recipeObservers = new ArrayList<>();

    // We are using the observer pattern right here to listen for changes in the recipes list
    public void addRecipeObserver(DataObserver<Recipe> observer) {
        recipeObservers.add(observer);
    }

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

        // Add calories to user tracker
        addCaloriesToUser(intCalorieText);

        // Save the meal under the user's ID in Firebase
        String key = foodRef.push().getKey();
        return foodRef.child("Meals").child(userId).child(key).setValue(meal);
    }

    public void addRecipeToCookbook(String name, String ingredients, Integer totalCalories) {
        // Add recipe to cookbook database
        String recipeKey = foodRef.push().getKey();
        assert recipeKey != null;
        foodRef.child("Cookbook").child(recipeKey).child("name").setValue(name);
        foodRef.child("Cookbook").child(recipeKey).child("ingredients").setValue(ingredients);
        foodRef.child("Cookbook").child(recipeKey).child("totalCalories").setValue(totalCalories);
    }

    public Task<Void> addIngredient(String nameText, int intQuantityText, int intCalories, String userId) {
        Ingredient ingredient = new Ingredient(nameText, intQuantityText, intCalories);

        String ingredientKey = foodRef.push().getKey();
        return foodRef.child("Pantry").child(userId).child(ingredientKey).setValue(ingredient);
    }

    public Task<Void> addCart(String nameText, int intQuantityText, int intCalories, String userId) {
        Ingredient ingredient = new Ingredient(nameText, intQuantityText, intCalories);

        String ingredientKey = foodRef.push().getKey();
        return foodRef.child("Cart").child(userId).child(ingredientKey).setValue(ingredient);
    }

    public Task<DataSnapshot> getIngredientsForUser(String userId) {
        // Retrieve ingredients for the given user from the database
        return foodRef.child("Pantry").child(userId).get();
    }

    public void addCaloriesToUser(int calories) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userCalories = foodRef.child("Users").child(userId).child("calories");

        userCalories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long oldCalories;
                try {
                    oldCalories = (dataSnapshot.getValue(Long.class));
                    if(oldCalories == null) {
                        oldCalories = Long.parseLong("0");
                    }
                } catch (Exception err) {
                    resetUserCalories();
                    oldCalories = Long.parseLong("0");
                }
                Long newCalories = oldCalories + calories;
                foodRef.child("Users").child(userId).child("calories").setValue(newCalories);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void resetUserCalories() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        foodRef.child("Users").child(userId).child("calories").setValue(0);
    }

    //cook a recipe
    public void cook(Recipe recipe, View v) {
        System.out.println("COOKING STARTED");
        boolean taskCompleted = false;
        // double check for enough ingredients
        List<Ingredient> userIngredients = new ArrayList<>();
        List<Ingredient> recipeIngredients = new ArrayList<>();
        List<String> pantryKeys = new ArrayList<>();
        String[] recipeIngredientsArray = recipe.getIngredients().split(",");
        for (String ingredientStr : recipeIngredientsArray) {
            String[] parts = ingredientStr.trim().split("(?<=\\d)(?=\\D)");
            int quantity = Integer.parseInt(parts[0]);
            String name = parts[1].trim();
//            System.out.println(name);
            Ingredient ingredient = new Ingredient(name, quantity, 0);
            recipeIngredients.add(ingredient);
        }

        // Fetch user ingredients from Firebase
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getIngredientsForUser(userId)
                .addOnSuccessListener(snapshot -> {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        //here is the source warnings
                        Ingredient ingredient = childSnapshot.getValue(Ingredient.class);
//                        System.out.println(childSnapshot);
                        pantryKeys.add(childSnapshot.getKey());
                        userIngredients.add(ingredient);
                    }


                    boolean allRequirementsMet = true;
                    for (Ingredient recipeIng : recipeIngredients) {
                        boolean ingredientFound = false;
                        for (Ingredient userIng : userIngredients) {
                            if (recipeIng.getName().trim().equalsIgnoreCase(userIng.getName().trim())
                                    && recipeIng.getQuantity() <= userIng.getQuantity()) {
                                // If the required ingredient is found and the quantity is sufficient
                                ingredientFound = true;
                                break;
                            }
                        }
                        // If the required ingredient is not found or quantity is insufficient
                        if (!ingredientFound) {
                            allRequirementsMet = false;
                            break;
                        }
                    }


                    if (allRequirementsMet) {
                        // deduct the ingredients from user's pantry
                        for (Ingredient recipeIng : recipeIngredients) {
                            boolean ingredientFound = false;
                            for (int i = 0; i < userIngredients.size(); i++) {
                                if (recipeIng.getName().trim().equalsIgnoreCase(userIngredients.get(i).getName().trim())
                                        && recipeIng.getQuantity() <= userIngredients.get(i).getQuantity()) {
                                    // If the required ingredient is found and the quantity is sufficient
                                    ingredientFound = true;
                                    //deduct
                                    foodRef.child("Pantry").child(userId).child(pantryKeys.get(i)).child("quantity").setValue(userIngredients.get(i).getQuantity() - recipeIng.getQuantity());
                                    //remove ingredient if its quantity is 0
                                    if(userIngredients.get(i).getQuantity() == recipeIng.getQuantity()) {
                                        foodRef.child("Pantry").child(userId).child(pantryKeys.get(i)).removeValue();
                                    }
                                    //add calories to user's intake
                                    addCaloriesToUser(recipe.getTotalCalories());
                                    break;
                                }
                            }
                        }

                        Toast.makeText(v.getContext(), "Recipe cooked successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Failed to cook recipe", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

