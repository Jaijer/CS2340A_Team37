package com.example.cs2340_project.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FoodDatabase {
    // Singleton FoodDatabase class.
    private volatile static FoodDatabase database;
    private final DatabaseReference foodRef;
    private FoodDatabase() {
        // Initialize firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Reference to the "meals" node in FireBase
        foodRef = firebaseDatabase.getReference("meals");
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

    public void addMeal(Meal meal) {
        if (meal != null) {
            String key = foodRef.push().getKey();
            if (key != null) {
                foodRef.child(key).setValue(meal);
            }
        }
    }
}
