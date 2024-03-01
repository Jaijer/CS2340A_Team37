package com.example.cs2340_project.views;

import android.os.Bundle;
import android.util.Log;
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
    }
}
