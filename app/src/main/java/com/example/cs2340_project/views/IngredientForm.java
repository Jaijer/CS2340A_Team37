package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import com.example.cs2340_project.R;


import androidx.appcompat.app.AppCompatActivity;

public class IngredientForm extends AppCompatActivity {
    private EditText name;
    private EditText quantity;
    private EditText calories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_form);
        name = findViewById(R.id.nameIng);
        quantity = findViewById(R.id.quantityIng);
        calories = findViewById(R.id.caloriesIng);
        Button addBtn = findViewById(R.id.addIngBtn);
        Button backBtn = findViewById(R.id.backIngBtn);

        addBtn.setOnClickListener(v -> {
            addIngredient();
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(IngredientForm.this, IngredientActivity.class);
            startActivity(intent);
        });
    }

    private void addIngredient() {
    }
}
