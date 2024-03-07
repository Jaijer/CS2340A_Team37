package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;

public class PersonalInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_screen);

        EditText height = findViewById(R.id.height);
        EditText weight = findViewById(R.id.weight);
        Button infoSaveBtn = findViewById(R.id.personalInfoSave);

        RadioButton maleRadioButton = findViewById(R.id.isMale);
        RadioButton femaleRadioButton = findViewById(R.id.isFemale);
        //to check if the user is male or female
        final boolean[] isMale = new boolean[1];
        final boolean[] isFemale = new boolean[1];



        infoSaveBtn.setOnClickListener(v -> {
            //Handle saving info
        });






        maleRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isMale[0] = true;
                }

                else if (!isChecked) {
                    isMale[0] = false;
                }
            }
        });
        femaleRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isFemale[0] = true;
                }

                else if (!isChecked) {
                    isFemale[0] = false;
                }
            }
        });


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
}
