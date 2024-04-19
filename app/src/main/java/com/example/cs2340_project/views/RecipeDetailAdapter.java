package com.example.cs2340_project.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;

import java.util.List;

public class RecipeDetailAdapter extends ArrayAdapter<Ingredient> {
    private Context mContext;
    private List<Ingredient> mIngredients;

    public RecipeDetailAdapter(Context context, List<Ingredient> ingredients) {
        super(context, 0, ingredients);
        mContext = context;
        mIngredients = ingredients;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.ingredient_details_list, parent, false);
        }

        // Get the current ingredient
        Ingredient currentIngredient = mIngredients.get(position);

        // Set the ingredient name
        TextView ingredientNameTextView = listItem.findViewById(R.id.ingredientNameTextView);
        ingredientNameTextView.setText(currentIngredient.getName());

        // Set the ingredient quantity
        TextView ingredientQuantityTextView = listItem.findViewById(R.id.ingredientQuantityTextView);
        ingredientQuantityTextView.setText("Quantity: " + currentIngredient.getQuantity());

        return listItem;
    }
}
