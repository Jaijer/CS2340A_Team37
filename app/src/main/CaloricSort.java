package com.example.cs2340_project.viewmodels;

import android.util.Log;

import com.example.cs2340_project.model.Ingredient;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CaloricSort implements SortingStrategies {

    @Override
    public List<Ingredient> sort(List<Ingredient> ingredients) {
        // Sort the list by calories
        Collections.sort(ingredients, new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient ingredient1, Ingredient ingredient2) {
                return ingredient1.getCalories().compareTo(ingredient2.getCalories());
            }
        });

        for (Ingredient ingredient : ingredients) {
            Log.d("Ingredient", "Name: " + ingredient.getName()
                    + ", Quantity: " + ingredient.getQuantity()
                    + ", Calories: " + ingredient.getCalories());
        }

        return ingredients;
    }
}
