package com.example.myfitnesslad;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
        TextView currentBmi = findViewById(R.id.currentBmi);

        // If the user has entered information, or there is information in "profile".txt,
        // we can calculate with those values
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
            currentBmi.setText("" + bmi);
        }

        // If there is no information in "profile.txt" they will need to go enter values first.
        else{
            results.setText("No Information to display.\nEnter body values first.");
        }
    }

    // Take in parameters and calculate the BMI of the user.
    float calculateBMI(float height, float weight){
        // Calculate the BMI given the user's height and weight.
        float tempBmi = (weight * 703) / (float) Math.pow(height, 2);

        // Round the BMI to 2 decimal places and return it.
        return  (float) (Math.round(tempBmi * 100.0) / 100.0);
    }

    // This function will calculate the overall state of the BMI
    // AKA - assign an output string message to the BMI and its color coded result.
    String calculateBMIState(float bmi){
        TextView currentBmi = findViewById(R.id.currentBmi);

        // Calculate the state of the person's health based on their BMI.
        if (bmi < 18.5){
            currentBmi.setTextColor(Color.YELLOW);
            return "Underweight";
        }
        else if (bmi >= 18.5 && bmi < 25){
            currentBmi.setTextColor(Color.GREEN);
            return "Healthy";
        }
        else if (bmi >= 25 && bmi < 30){
            currentBmi.setTextColor(Color.YELLOW);
            return "Overweight";
        }
        else if (bmi >= 30 && bmi < 35){
            currentBmi.setTextColor(Color.RED);
            return "Obese: Class 1";
        }
        else if (bmi >= 35 && bmi < 40){
            currentBmi.setTextColor(Color.RED);
            return "Obese: Class 2";
        }
        else if (bmi > 40){
            currentBmi.setTextColor(Color.RED);
            return "Obese: Class 3";
        }
        else{
            return "Unknown";
        }
    }
}
