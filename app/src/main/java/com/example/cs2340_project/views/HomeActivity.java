package com.example.cs2340_project.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;


public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_screen);
        TextView headerTextView = findViewById(R.id.headerTextView);
        Button homeActivityButton = findViewById(R.id.homeActivityButton);
        Button ingredientActivityButton = findViewById(R.id.ingredientActivityButton);
        Button inputMealActivityButton = findViewById(R.id.inputMealActivityButton);
        Button recipeActivityButton = findViewById(R.id.recipeActivityButton);
        Button shoppingListActivityButton = findViewById(R.id.shoppingListActivityButton);
        ImageView profilePic = findViewById(R.id.profilePic);


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PersonalInfoActivity.class);
                startActivity(intent);
            }
        });
        homeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ingredientActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, IngredientActivity.class);
                startActivity(intent);

            }
        });

        inputMealActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, InputMealActivity.class);
                startActivity(intent);

            }
        });

        recipeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RecipeActivity.class);
                startActivity(intent);

            }
        });

        shoppingListActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ShoppingListActivity.class);
                startActivity(intent);

            }
        });
    }
}
