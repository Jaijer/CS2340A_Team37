package com.example.cs2340_project.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;

import java.util.List;

public class ShoppingAdapter extends ArrayAdapter<Ingredient> {
    private Context mContext;
    private List<Ingredient> mIngredientList;

    public ShoppingAdapter(Context context, List<Ingredient> ingredientList) {
        super(context, 0, ingredientList);
        mContext = context;
        mIngredientList = ingredientList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.shopping_checkbox_list, parent, false);
        }

        // Get the current ingredient
        Ingredient currentIngredient = mIngredientList.get(position);

        // Null check for currentIngredient
        if (currentIngredient != null) {
            // Populate the TextViews with ingredient information
            TextView nameTextView = listItem.findViewById(R.id.nameTextView);
            TextView quantityTextView = listItem.findViewById(R.id.quantityTextView);
            TextView caloriesTextView = listItem.findViewById(R.id.caloriesTextView);
            CheckBox checkBox = listItem.findViewById(R.id.checkBox); // Add this line

            nameTextView.setText(currentIngredient.getName());
            quantityTextView.setText(String.valueOf("Quantity: " + currentIngredient.getQuantity()));
            caloriesTextView.setText(String.valueOf(currentIngredient.getCalories() + " cal."));

            // Set Checkbox state based on your data (e.g., isChecked)
            checkBox.setChecked(currentIngredient.isSelected()); // Add this line

            // Set OnClickListener to the checkbox
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle checkbox click
                    currentIngredient.setSelected(checkBox.isChecked()); // Update the Ingredient's selected state
                }
            });

            // Set OnClickListener to the list item
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create an Intent to start AdjustIngredientActivity
                    Intent intent = new Intent(mContext, AdjustIngredientActivity.class);

                    // Pass the selected Ingredient object to AdjustIngredientActivity
                    intent.putExtra("ingredient", currentIngredient);

                    // Start the AdjustIngredientActivity
                    mContext.startActivity(intent);
                }
            });
        }

        return listItem;
    }
}
