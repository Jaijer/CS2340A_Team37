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
import com.example.cs2340_project.model.Meal;
import com.example.cs2340_project.viewmodels.FoodDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class InputMealActivity extends AppCompatActivity {

    private FoodDatabase foodDatabase;
    private EditText mealName;
    private EditText mealCalorie;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

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

            try {
            intCalorieText = Integer.parseInt(calorieText);
            } catch (NumberFormatException e) {
              Toast.makeText(InputMealActivity.this, "Calories must include only numbers.",
                       Toast.LENGTH_SHORT).show();
               return;
            }


            if (auth.getCurrentUser() != null) {
                String userId = auth.getCurrentUser().getUid();
                Meal meal = new Meal(nameText, intCalorieText);

                // Save the meal under the user's ID in Firebase
                databaseReference.child("Meals").child(userId).push().setValue(meal)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(InputMealActivity.this, "Meal added successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InputMealActivity.this, "Failed to add meal", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(InputMealActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
            }
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