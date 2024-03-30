package com.example.cs2340_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340_project.R;
import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.viewmodels.AlphabeticSort;
import com.example.cs2340_project.viewmodels.ByDateSort;
import com.example.cs2340_project.viewmodels.CaloricSort;
import com.example.cs2340_project.viewmodels.SortingStrategies;

import java.util.List;

public class SortSelectionActivity extends AppCompatActivity {
    List<Ingredient> ingredients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_selection_activity);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ingredients")) {
            ingredients = intent.getParcelableArrayListExtra("ingredients");
        }

        // Initialize views
        RadioButton alphabeticalRadioButton = findViewById(R.id.alphabeticalRadioButton);
        RadioButton calorieRadioButton = findViewById(R.id.calorieRadioButton);
        Button setButton = findViewById(R.id.setButton);
        Button cancelButton = findViewById(R.id.cancelButton);
        RadioGroup sortRadioGroup = findViewById(R.id.sortRadioGroup);

        // Set OnClickListener for Set Button
        setButton.setOnClickListener(v -> {
            int checkedRadioButtonId = sortRadioGroup.getCheckedRadioButtonId();
            if (checkedRadioButtonId == -1) {
                // No radio button is selected
                Toast.makeText(SortSelectionActivity.this, "Please select a sorting option", Toast.LENGTH_SHORT).show();
            } else if (checkedRadioButtonId == R.id.alphabeticalRadioButton) {
                // Sort alphabetically

                Toast.makeText(SortSelectionActivity.this, "Sorted alphabetically", Toast.LENGTH_SHORT).show();

                SortingStrategies alphSort = new AlphabeticSort();
                ingredients = alphSort.sort(ingredients);

                Intent newIntent = new Intent(SortSelectionActivity.this, IngredientActivity.class);
                startActivity(newIntent);
            } else if (checkedRadioButtonId == R.id.calorieRadioButton) {
                // Sort by calorie

                Toast.makeText(SortSelectionActivity.this, "Sorted by calorie", Toast.LENGTH_SHORT).show();

                SortingStrategies caloricSort = new CaloricSort();
                ingredients = caloricSort.sort(ingredients);

                Intent newIntent = new Intent(SortSelectionActivity.this, IngredientActivity.class);
                startActivity(newIntent);
            } else if (checkedRadioButtonId == R.id.dateAddedRadioButton) {
                // Sort by date added.

                Toast.makeText(SortSelectionActivity.this, "Sorted by date added", Toast.LENGTH_SHORT).show();

                SortingStrategies byDateSort = new ByDateSort();
                ingredients = byDateSort.sort(ingredients);

                Intent newIntent = new Intent(SortSelectionActivity.this, IngredientActivity.class);
                startActivity(newIntent);

            }
        });

        // Set OnClickListener for Cancel Button
        cancelButton.setOnClickListener(v -> {
            // Finish the activity without performing any sorting action
            finish();
        });
    }
}
