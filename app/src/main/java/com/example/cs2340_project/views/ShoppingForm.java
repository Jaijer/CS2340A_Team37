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


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
public class ShoppingForm extends AppCompatActivity{
    private EditText name;
    private EditText quantity;
    private EditText calories;
    private FoodDatabase foodDatabase;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_form);
        name = findViewById(R.id.nameCart);
        quantity = findViewById(R.id.quantityCart);
        calories = findViewById(R.id.caloriesCart);
        Button addBtn = findViewById(R.id.addIngCartBtn);
        Button backBtn = findViewById(R.id.backCartBtn);
        foodDatabase = FoodDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        addBtn.setOnClickListener(v -> {
            addCart();
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingForm.this, ShoppingListActivity.class);
            startActivity(intent);
        });
    }

    private void addCart() {
        String nameText = name.getText().toString().trim();
        String quantityText = quantity.getText().toString().trim();
        String calorieText = calories.getText().toString().trim();

        if (nameText.isEmpty() || quantityText.isEmpty() || calorieText.isEmpty()) {
            Toast.makeText(ShoppingForm.this, "Please fill in all fields.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        

        int intQuantityText;

        try {
            intQuantityText = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            Toast.makeText(ShoppingForm.this, "Quantity must include only numbers.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (intQuantityText <= 0) {
            Toast.makeText(ShoppingForm.this, "Quantity must be positive.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int intCalories;

        try {
            intCalories = Integer.parseInt(calorieText);
        } catch (NumberFormatException e) {
            Toast.makeText(ShoppingForm.this, "Calories must include only numbers.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (intCalories <= 0) {
            Toast.makeText(ShoppingForm.this, "Calories must be positive.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();

            foodDatabase.addCart(nameText, intQuantityText, intCalories, userId)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ShoppingForm.this, "Ingredient added successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ShoppingForm.this, "Failed to add ingredient", Toast.LENGTH_SHORT).show();
                        }
                    });;
        } else {
            Toast.makeText(ShoppingForm.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}
