package com.example.myfitnesslad;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Intake extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake);

    }

    public void CreateMeal(View view){

        EditText testInput = findViewById(R.id.testInput);
        TextView totalCalories = findViewById(R.id.totalCalories);

        int userInput = Integer.parseInt(testInput.getText().toString());
        int tempCalories = Integer.parseInt(totalCalories.getText().toString());


        Meal meal = new Meal(userInput,30,13,20);
        tempCalories += meal.getCalories();

        totalCalories.setText("" + tempCalories);
    }

}
