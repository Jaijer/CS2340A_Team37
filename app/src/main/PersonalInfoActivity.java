package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalInfoActivity extends AppCompatActivity {

    private EditText height;
    private EditText weight;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_screen);

        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        maleRadioButton = findViewById(R.id.isMale);
        femaleRadioButton = findViewById(R.id.isFemale);
        Button infoSaveBtn = findViewById(R.id.personalInfoSave);

        infoSaveBtn.setOnClickListener(v -> {
            saveUserInfo();
        });

        // Navigation buttons setup here as previously

        Button homeActivityButton = findViewById(R.id.homeActivityButton);
        Button ingredientActivityButton = findViewById(R.id.ingredientActivityButton);
        Button inputMealActivityButton = findViewById(R.id.inputMealActivityButton);
        Button recipeActivityButton = findViewById(R.id.recipeActivityButton);
        Button shoppingListActivityButton = findViewById(R.id.shoppingListActivityButton);

        homeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ingredientActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this, IngredientActivity.class);
                startActivity(intent);

            }
        });

        inputMealActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this, InputMealActivity.class);
                startActivity(intent);

            }
        });

        recipeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this, RecipeActivity.class);
                startActivity(intent);

            }
        });

        shoppingListActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this, ShoppingListActivity.class);
                startActivity(intent);

            }
        });
    }

    private void saveUserInfo() {
        String userHeight = height.getText().toString().trim();
        String userWeight = weight.getText().toString().trim();
        String gender = maleRadioButton.isChecked() ? "Male" : "Female";

        if (userHeight.isEmpty() || userWeight.isEmpty()) {
            Toast.makeText(PersonalInfoActivity.this,
                    "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User user = new User();
        user.setHeight(userHeight);
        user.setWeight(userWeight);
        user.setGender(gender);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(PersonalInfoActivity.this,
                                "Info saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PersonalInfoActivity.this,
                                "Failed to save info", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}