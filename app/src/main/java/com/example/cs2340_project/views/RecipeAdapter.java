package com.example.cs2340_project.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.model.Recipe;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    private Context mContext;
    private int mResource;

    public RecipeAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        // Get the recipe at the current position
        Recipe recipe = getItem(position);

        // Get the TextViews from the layout
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView caloriesTextView = convertView.findViewById(R.id.caloriesTextView);

        // Set the recipe information to the TextViews
        if (recipe != null) {
            nameTextView.setText(recipe.getName());
            // Construct the string for ingredients
            StringBuilder ingredientsString = new StringBuilder("Ingredients: ");
            List<Ingredient> ingredients = recipe.getIngredients();
            if (ingredients != null) {
                for (int i = 0; i < ingredients.size(); i++) {
                    Ingredient ingredient = ingredients.get(i);
                    ingredientsString.append(ingredient.getName());
                    if (i < ingredients.size() - 1) {
                        ingredientsString.append(", ");
                    }
                }
            }
            caloriesTextView.setText(recipe.getTotalCalories() + " calories");
        }

        return convertView;
    }
}
