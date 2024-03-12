package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;
import com.example.cs2340_project.viewmodels.FoodDatabase;

public class InputMealActivity extends AppCompatActivity {

    private FoodDatabase foodDatabase;
    private EditText mealName;
    private EditText mealCalorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_meal_activity_screen);
        TextView inputMealHeaderTextView = findViewById(R.id.inputMealHeaderTextView);
        Button homeActivityButton = findViewById(R.id.homeActivityButton);
        Button ingredientActivityButton = findViewById(R.id.ingredientActivityButton);
        Button inputMealActivityButton = findViewById(R.id.inputMealActivityButton);
        Button recipeActivityButton = findViewById(R.id.recipeActivityButton);
        Button shoppingListActivityButton = findViewById(R.id.shoppingListActivityButton);
        foodDatabase = FoodDatabase.getInstance();


        //Sprint 2 Task 2:
        mealName = findViewById(R.id.mealName);
        mealCalorie = findViewById(R.id.mealCalorie);
        Button addMealBtn = findViewById(R.id.addMealBtn);

        addMealBtn.setOnClickListener(v -> {
            String nameText = mealName.getText().toString().trim();
            String calorieText = mealCalorie.getText().toString().trim();

            //Input validation
            if (nameText.isEmpty() || calorieText.isEmpty()) {
                Toast.makeText(InputMealActivity.this, "Meal name or calories cannot be empty.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            int intCalorieText = Integer.parseInt(calorieText);

       //     try {
       //         intCalorieText = Integer.parseInt(calorieText);
        //    } catch (NumberFormatException e) {
        //        Toast.makeText(InputMealActivity.this, "Calories must include only numbers.",
        //                Toast.LENGTH_SHORT).show();
        //        return;
       //     }
            


            foodDatabase.addMeal(nameText, intCalorieText);
        });



        //Sprint 2 Task 5:
        Button showDailyCalorieIntakeOverPastMonthBtn = findViewById(R.id.showDailyCalorieIntakeOverPastMonthBtn);
        Button showComparisonBetweenIntakeAndGoalBtn = findViewById(R.id.showComparisonBetweenIntakeAndGoalBtn);

        showDailyCalorieIntakeOverPastMonthBtn.setOnClickListener(v -> {

        });
        showComparisonBetweenIntakeAndGoalBtn.setOnClickListener(v -> {

        });

        homeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMealActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ingredientActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMealActivity.this, IngredientActivity.class);
                startActivity(intent);

            }
        });

        inputMealActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMealActivity.this, InputMealActivity.class);
                startActivity(intent);

            }
        });

        recipeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMealActivity.this, RecipeActivity.class);
                startActivity(intent);

            }
        });

        shoppingListActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMealActivity.this, ShoppingListActivity.class);
                startActivity(intent);

            }
        });
    }
}
