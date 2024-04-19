package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.viewmodels.FoodDatabase;
import com.google.firebase.auth.FirebaseAuth;


import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class IngredientForm extends AppCompatActivity {
    private EditText name;
    private EditText quantity;
    private EditText calories;
    private FoodDatabase foodDatabase;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_form);
        name = findViewById(R.id.nameIng);
        quantity = findViewById(R.id.quantityIng);
        calories = findViewById(R.id.caloriesIng);
        Button addBtn = findViewById(R.id.addIngBtn);
        Button backBtn = findViewById(R.id.backIngBtn);
        foodDatabase = FoodDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        addBtn.setOnClickListener(v -> {
            addIngredient();
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(IngredientForm.this, IngredientActivity.class);
            startActivity(intent);
        });
    }

    private void addIngredient() {
        String nameText = name.getText().toString().trim();
        String quantityText = quantity.getText().toString().trim();
        String calorieText = calories.getText().toString().trim();

        if (nameText.isEmpty() || quantityText.isEmpty() || calorieText.isEmpty()) {
            Toast.makeText(IngredientForm.this, "Please fill in all fields.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("ingredients")) {
            List<Ingredient> ingredients = intent.getParcelableArrayListExtra("ingredients");
            for (Ingredient ingredient : ingredients) {
                if (ingredient.getName().trim().toLowerCase().equals(nameText.toLowerCase())) {
                    Toast.makeText(IngredientForm.this, "Duplicate ingredients not allowed.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        int intQuantityText;

        try {
            intQuantityText = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            Toast.makeText(IngredientForm.this, "Quantity must include only numbers.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (intQuantityText <= 0) {
            Toast.makeText(IngredientForm.this, "Quantity must be positive.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int intCalories;

        try {
            intCalories = Integer.parseInt(calorieText);
        } catch (NumberFormatException e) {
            Toast.makeText(IngredientForm.this, "Calories must include only numbers.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (intCalories <= 0) {
            Toast.makeText(IngredientForm.this, "Calories must be positive.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();

            foodDatabase.addIngredient(nameText, intQuantityText, intCalories, userId)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(IngredientForm.this, "Ingredient added successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(IngredientForm.this, "Failed to add ingredient", Toast.LENGTH_SHORT).show();
                        }
                    });;
        } else {
            Toast.makeText(IngredientForm.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

}
