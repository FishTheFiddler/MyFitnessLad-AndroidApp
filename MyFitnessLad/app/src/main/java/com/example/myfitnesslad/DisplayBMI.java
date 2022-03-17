package com.example.myfitnesslad;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayBMI extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        TextView results = findViewById(R.id.results);

        if (MainActivity.informationEntered) {
            // -------------   Get information from Main Page
            Bundle BMIBundle = getIntent().getExtras();
            int height = BMIBundle.getInt("Height");
            float weight = BMIBundle.getFloat("Weight");
            float bmi = calculateBMI(height, weight);
            String bmiState = calculateBMIState(bmi);

            // -------------   Change text to reflect valid information.
            results.setText("Your height is: " + (height / 12) + " feet, and " + (height % 12)
                    + " inches.\nYour weight is: " + weight + " pounds. " +
                    "\n\nThis gives you a BMI of " + bmi
                    + ".\nAccording to the CDC guidelines, you are " + bmiState);
        }
        else{
            results.setText("No Information to display.\nEnter body values first.");
        }
    }

    float calculateBMI(float height, float weight){
        // Calculate the BMI given the user's height and weight.
        float tempBmi = (weight * 703) / (float) Math.pow(height, 2);

        // Round the BMI to 2 decimal places and return it.
        return  (float) (Math.round(tempBmi * 100.0) / 100.0);
    }

    String calculateBMIState(float bmi){

        // Calculate the state of the person's health based on their weight.
        if (bmi < 18.5){
            return "Underweight";
        }
        else if (bmi >= 18.5 && bmi < 25){
            return "Healthy";
        }
        else if (bmi >= 25 && bmi < 30){
            return "Overweight";
        }
        else if (bmi >= 30 && bmi < 35){
            return "Obese: Class 1";
        }
        else if (bmi >= 35 && bmi < 40){
            return "Obese: Class 2";
        }
        else if (bmi > 40){
            return "Obese: Class 3";
        }
        else{
            return "Unknown";
        }
    }
}
