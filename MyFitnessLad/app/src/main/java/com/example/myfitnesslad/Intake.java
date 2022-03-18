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

        EditText inputCarbs = findViewById(R.id.inputCarbs);
        EditText inputFats = findViewById(R.id.inputFats);
        EditText inputProtein = findViewById(R.id.inputProtein);
        EditText inputCalories = findViewById(R.id.inputCalories);
        TextView totalCalories = findViewById(R.id.totalCalories);
        TextView ocBox = findViewById(R.id.ocBox);

        int calorieInput=0;

        //int carbsInput = 0;
        //int fatsInput = 0;
        //int proteinInput = 0;

        int carbsInput = Integer.parseInt(inputCarbs.getText().toString());
        int fatsInput = Integer.parseInt(inputFats.getText().toString());
        int proteinInput = Integer.parseInt(inputProtein.getText().toString());
        if (!inputCalories.getText().toString().isEmpty()) {
           calorieInput = Integer.parseInt(inputCalories.getText().toString());
        }

        Meal meal = new Meal(carbsInput,fatsInput,proteinInput);
        int tempCalories = meal.calculateCalories();

        //override when user knows calories
        if(calorieInput<=0)
        {
            String s="";
            if(meal.ocCalories(tempCalories)) { s+="Overall high calorie intake! "; }
            if(meal.ocCarbs(carbsInput)) { s+="High carbohydrate intake! "; }
            if(meal.ocFats(fatsInput)) { s+="High fat intake! "; }
            if(meal.ocProtein(proteinInput)) { s+="High protein intake! "; }
            if(s==""){ s+="Balanced"; }
            ocBox.setText(s);
            totalCalories.setText("" + tempCalories);
        }
        else {
            totalCalories.setText("" + inputCalories.getText().toString());
            if (calorieInput < 2200) {
                ocBox.setText("Balanced");
            } else {
                ocBox.setText("Overall high calorie intake!");
            }
        }
        //have the calorie data sent to mainactivity for overall person health data
        //TODO: CLEAR FIELDS FUNCTION
        //TODO: SEND CALORIE DATA TO MAIN
    }
}
