package com.example.cs2340_project.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.viewmodels.FoodDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AdjustIngredientActivity extends AppCompatActivity {

    private EditText customQuantityEditText;
    private Ingredient ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjust_ingredient_activity);

        // Retrieve the Ingredient object from the intent
        ingredient = getIntent().getParcelableExtra("ingredient");

        // Initialize views
        customQuantityEditText = findViewById(R.id.customQuantityEditText);
        Button setButton = findViewById(R.id.setButton);
        Button addButton = findViewById(R.id.addButton);
        Button removeButton = findViewById(R.id.removeButton);
        Button closeButton = findViewById(R.id.closeButton);

        // Set text for ingredient name, quantity, and calorie count TextViews
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView quantityTextView = findViewById(R.id.quantityTextView);
        TextView calorieCountTextView = findViewById(R.id.calorieCountTextView);

        nameTextView.setText(ingredient.getName());
        quantityTextView.setText(String.valueOf(ingredient.getQuantity()));
        calorieCountTextView.setText(String.valueOf(ingredient.getCalories()));

        // Set OnClickListener for Set Button
        setButton.setOnClickListener(v -> setCustomQuantity());

        // Set OnClickListener for Add Button
        addButton.setOnClickListener(v -> addIngredient());

        // Set OnClickListener for Remove Button
        // Set OnClickListener for Remove Button
        removeButton.setOnClickListener(v -> {
            // Decrement the quantity
            int currentQuantity = ingredient.getQuantity();
            if (currentQuantity > 0) {
                // If quantity is greater than 0, decrement it
                ingredient.setQuantity(currentQuantity - 1);
                updateIngredientQuantity();
            } else {
                // If quantity is already 0, remove the ingredient from the database
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference databaseReference = FoodDatabase.getInstance().getFoodRef()
                        .child("Pantry").child(userId);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Ingredient storedIngredient = childSnapshot.getValue(Ingredient.class);
                            if (storedIngredient.getName().equals(ingredient.getName())) {
                                // Remove the ingredient from the database
                                childSnapshot.getRef().removeValue()
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(AdjustIngredientActivity.this,
                                                    "Ingredient removed successfully",
                                                    Toast.LENGTH_SHORT).show();
                                            // Close the activity after successful removal
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(AdjustIngredientActivity.this,
                                                    "Failed to remove ingredient",
                                                    Toast.LENGTH_SHORT).show();
                                        });
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle database error
                        Toast.makeText(AdjustIngredientActivity.this,
                                "Failed to fetch ingredients", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Set OnClickListener for Close Button
        closeButton.setOnClickListener(v -> finish());
    }

    private void setCustomQuantity() {
        String customQuantityString = customQuantityEditText.getText().toString();
        if (!customQuantityString.isEmpty()) {
            int customQuantity = Integer.parseInt(customQuantityString);
            if (customQuantity == 0) {
                // Remove the ingredient from Firebase
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference databaseReference =
                        FoodDatabase.getInstance().getFoodRef().
                                child("Pantry").child(userId);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Ingredient storedIngredient = childSnapshot.getValue(Ingredient.class);
                            if (storedIngredient.getName().equals(ingredient.getName())) {
                                // Remove the ingredient from the database
                                childSnapshot.getRef().removeValue()
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(AdjustIngredientActivity.this,
                                                    "Ingredient removed successfully",
                                                    Toast.LENGTH_SHORT).show();
                                            // Close the activity after successful removal
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(AdjustIngredientActivity.this,
                                                    "Failed to remove ingredient",
                                                    Toast.LENGTH_SHORT).show();
                                        });
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle database error
                        Toast.makeText(AdjustIngredientActivity.this,
                                "Failed to fetch ingredients", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                ingredient.setQuantity(customQuantity);
                updateIngredientQuantity();
            }
        } else {
            Toast.makeText(AdjustIngredientActivity.this,
                    "Please enter a custom quantity", Toast.LENGTH_SHORT).show();
        }
    }


    private void addIngredient() {
        int currentQuantity = ingredient.getQuantity();
        ingredient.setQuantity(currentQuantity + 1);
        updateIngredientQuantity();
    }

    private void updateIngredientQuantity() {
        // Fetch the list of ingredients for the user
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference =
                FoodDatabase.getInstance().getFoodRef().child("Pantry").child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Loop through each child (ingredient) in the dataSnapshot
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Ingredient storedIngredient = childSnapshot.getValue(Ingredient.class);

                    // Check if the retrieved ingredient matches the current ingredient
                    if (storedIngredient.getName().equals(ingredient.getName())) {
                        // Update the quantity of the stored ingredient
                        storedIngredient.setQuantity(ingredient.getQuantity());

                        // Set the updated ingredient back in the database
                        childSnapshot.getRef().setValue(storedIngredient)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(AdjustIngredientActivity.this,
                                            "Ingredient quantity updated successfully",
                                            Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(AdjustIngredientActivity.this,
                                            "Failed to update ingredient quantity",
                                            Toast.LENGTH_SHORT).show();
                                });

                        // Exit the loop since the ingredient is found and updated
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(AdjustIngredientActivity.this,
                        "Failed to fetch ingredients", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
