package com.example.cs2340_project.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShoppingAdapter extends ArrayAdapter<Ingredient> {
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    DatabaseReference pantryRef = FirebaseDatabase.getInstance().getReference().child("Pantry").child(userId);

    private Context mContext;
    private List<Ingredient> mIngredientList;
    public ShoppingAdapter(Context context, List<Ingredient> ingredientList) {
        super(context, 0, ingredientList);
        mContext = context;
        mIngredientList = ingredientList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, parent, false);
        }

        Ingredient currentIngredient = mIngredientList.get(position);

        if (currentIngredient != null) {
            TextView nameTextView = listItem.findViewById(R.id.nameTextView);
            TextView quantityTextView = listItem.findViewById(R.id.quantityTextView);
            TextView caloriesTextView = listItem.findViewById(R.id.caloriesTextView);
            Button shopButton = listItem.findViewById(R.id.shopButton);

            nameTextView.setText(currentIngredient.getName());
            quantityTextView.setText("Quantity: " + currentIngredient.getQuantity());
            caloriesTextView.setText(currentIngredient.getCalories() + " cal.");

            shopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key = pantryRef.push().getKey();
                    pantryRef.child(key).setValue(new Ingredient(currentIngredient.getName(), currentIngredient.getQuantity(), 100)); // Assuming calorie value is 100 for all new entries
                    Toast.makeText(mContext, "Added " + currentIngredient.getName() + " to cart", Toast.LENGTH_SHORT).show();
                    mIngredientList.remove(currentIngredient);
                    notifyDataSetChanged();


                }
            });
        }

        return listItem;
    }
}
