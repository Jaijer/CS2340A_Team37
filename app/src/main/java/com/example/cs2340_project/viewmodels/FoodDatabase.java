package com.example.cs2340_project.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.cs2340_project.model.Meal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    int counter = 0;
// re-implement to ensure compatibility
    public void addMeal(String name, int calorieCount) {
        Meal meal = new Meal(name, calorieCount);
        foodRef.child("meal" + counter).setValue(meal);
        counter++;
        }
    }

