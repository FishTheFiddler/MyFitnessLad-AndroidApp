package com.example.myfitnesslad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.InputMismatchException;

public class ResetValues extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
    }


    public void apply(View view){

        // Grab the fields
        EditText heightField = findViewById(R.id.height);
        EditText weightField = findViewById(R.id.weight);
        EditText ageField = findViewById(R.id.age);
        RadioButton male = findViewById(R.id.radioMale);
        RadioButton female = findViewById(R.id.radioFemale);
        RadioButton activityNone = findViewById(R.id.radioNone);
        RadioButton activity1to3 = findViewById(R.id.radio1to3);
        RadioButton activity4to5 = findViewById(R.id.radio4to5);
        RadioButton activity6to7 = findViewById(R.id.radio6to7);

        TextView errorMessage = findViewById(R.id.error);

        try {
            int heightValue = Integer.parseInt(heightField.getText().toString());
            int ageValue = Integer.parseInt(ageField.getText().toString());
            float weightValue = Float.parseFloat(weightField.getText().toString());
            if (weightValue <= 0 || heightValue <= 0 || ageValue <= 0) {
                throw new InputMismatchException();
            }

            // Checking and setting Gender
            if (male.isChecked()){
                MainActivity.setGender(true);
            }
            else if (female.isChecked()){
                MainActivity.setGender(false);
            }
            else{
                throw new Exception();
            }

            // Checking and setting Activity Level
            if (activityNone.isChecked()){
                MainActivity.activityLevel = 0;
            }
            else if (activity1to3.isChecked()){
                MainActivity.activityLevel = 1;
            }
            else if (activity4to5.isChecked()){
                MainActivity.activityLevel = 2;
            }
            else if (activity6to7.isChecked()){
                MainActivity.activityLevel = 3;
            }
            else{
                throw new Exception();
            }



            MainActivity.setHeight(heightValue);
            MainActivity.setWeight(weightValue);
            MainActivity.setAge(ageValue);
            MainActivity.informationEntered = true;
            Toast.makeText(this, "Body Parameters Saved", Toast.LENGTH_SHORT).show();

            // Get back to the Main Activity
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();

        } catch (InputMismatchException e){
            errorMessage.setText("ERROR: Height, weight, and age must be greater than 0.");
        } catch (Exception e){
            errorMessage.setText("ERROR: Some fields have either been left blank, or Height and Weight are not whole numbers.");
        }


    }
}
