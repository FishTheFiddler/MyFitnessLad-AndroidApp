package com.example.myfitnesslad;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetValues extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

    }


    public void apply(View view){

        EditText heightField = findViewById(R.id.height);
        EditText weightField = findViewById(R.id.weight);
        RadioButton male = findViewById(R.id.radioMale);
        RadioButton female = findViewById(R.id.radioFemale);

        int heightValue = Integer.parseInt(heightField.getText().toString());
        float weightValue = Float.parseFloat(weightField.getText().toString());

        if (male.isChecked()){
            MainActivity.setGender(true);
        }
        else if (female.isChecked()){
            MainActivity.setGender(false);
        }
        else{
            MainActivity.setGender(true);
        }

        MainActivity.setHeight(heightValue);
        MainActivity.setWeight(weightValue);

        Toast.makeText(this, "Body Parameters Saved", Toast.LENGTH_SHORT).show();

        finish();
    }
}
