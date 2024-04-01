package com.example.cs2340_project.views;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.model.Recipe;
import com.example.cs2340_project.viewmodels.FoodDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    private Context mContext;
    private int mResource;
    private List<Ingredient> userIngredients = new ArrayList<>();
    private List<Ingredient> recipeIngredients = new ArrayList<>();


    public RecipeAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource,
                    parent, false);
        }

        // Get the recipe at the current position
        Recipe recipe = getItem(position);

        // Get the TextViews from the layout
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView caloriesTextView = convertView.findViewById(R.id.caloriesTextView);
        TextView insufficientIngredientsTextView =
                convertView.findViewById(R.id.insufficientIngredientsTextView);

        if (recipe != null) {
            nameTextView.setText(recipe.getName());
            String ingredientString = recipe.getIngredients();

            String[] recipeIngredients2 = ingredientString.split(",");

            // Loop through each ingredient
            for (String ingredientStr : recipeIngredients2) {
                // Split each ingredient by the first digit
                // (assuming the quantity always starts with a digit)
                String[] parts = ingredientStr.split("(?<=\\d)(?=\\D)");

                int quantity = Integer.parseInt(parts[0]);
                String name = parts[1];

                Ingredient ingredient = new Ingredient(name, quantity, 0);
                recipeIngredients.add(ingredient);
            }

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FoodDatabase.getInstance().getIngredientsForUser(userId)
                    .addOnSuccessListener(snapshot -> {
                        userIngredients.clear(); // Clear the list before adding new data
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            Ingredient ingredient = childSnapshot.getValue(Ingredient.class);
                            userIngredients.add(ingredient);
                        }
                    });

            boolean allRequirementsMet = true;
            for (Ingredient recipeIng : recipeIngredients) {
                boolean ingredientFound = false; // Flag to check if the ingredient is found
                for (Ingredient userIng : userIngredients) {
                    if (recipeIng.getName().trim().
                            equalsIgnoreCase(userIng.getName().trim()) && recipeIng.getQuantity()
                            <= userIng.getQuantity()) {
                        // If the required ingredient is found and the quantity is sufficient
                        ingredientFound = true;
                        break; // No need to check further, move to the next required ingredient
                    }
                }
                // If the required ingredient is not found or quantity is insufficient
                if (!ingredientFound) {
                    allRequirementsMet = false;
                    break; // No need to check further, requirements are not met
                }
            }

            if (allRequirementsMet) {
                // All requirements are met, hide the text view
                insufficientIngredientsTextView.setVisibility(View.GONE);
            } else {
                // Requirements are not met, show the text view
                insufficientIngredientsTextView.setVisibility(View.VISIBLE);
            }

            // Logging userIngredients
            for (Ingredient ingredient : userIngredients) {
                Log.d("RecipeAdapter", "User Ingredient: "
                        + ingredient.getName() + ", Quantity: " + ingredient.getQuantity());
            }
            for (Ingredient ingredient : recipeIngredients) {
                Log.d("RecipeAdapter", "Recipe Ingredient: "
                        + ingredient.getName() + ", Quantity: " + ingredient.getQuantity());
            }

            caloriesTextView.setText(recipe.getTotalCalories() + " calories");

            // Set click listener for the item
            convertView.setOnClickListener(v -> {
                // Start RecipeDetailsActivity with appropriate data
                Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                // Pass individual ingredients as extras
                intent.putExtra("recipeIngredients", recipe.getIngredients());
                intent.putExtra("recipeName", recipe.getName());
                intent.putExtra("calories", String.valueOf(recipe.getTotalCalories()));
                mContext.startActivity(intent);
            });

        }

        return convertView;
    }
}
