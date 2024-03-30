package com.example.cs2340_project.viewmodels;

import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.views.IngredientActivity;
import com.example.cs2340_project.views.IngredientAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class ByDateSort implements SortingStrategies {

    @Override
    public List<Ingredient> sort(List<Ingredient> ingredients) {
        // Retrieve ingredients for the current user
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FoodDatabase.getInstance().getIngredientsForUser(userId)
                .addOnSuccessListener(snapshot -> {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Ingredient ingredient = childSnapshot.getValue(Ingredient.class);
                        ingredients.add(ingredient);
                    }
                });

        for (Ingredient ingredient : ingredients) {
            Log.d("Ingredient", "Name: " + ingredient.getName() + ", Quantity: " + ingredient.getQuantity() + ", Calories: " + ingredient.getCalories());
        }

        return ingredients;
    }
}
