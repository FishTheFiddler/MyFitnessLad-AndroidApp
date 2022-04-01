package com.example.myfitnesslad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
>>>>>>> Stashed changes
import android.graphics.Color;
import android.icu.util.Calendar;
>>>>>>> Stashed changes
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static int height = 0;
    static float weight = 0;
    static boolean isMale = true;
    static int age = 0;
    static int activityLevel = 0;

    static boolean informationEntered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayInformation();
    }

    public void BMI(View view) {
       // Create Intent for DisplayBMI Activity
       Intent BMIIntent = new Intent(this, DisplayBMI.class);

       BMIIntent.putExtra("Height", height);
       BMIIntent.putExtra("Weight", weight);

       Log.d(this.getLocalClassName(),"Creating intent with a height of " +
                       height + " inches, and a weight of " + weight + " pounds");

       // Start the next activity
       startActivity(BMIIntent);
   }
    public void IBW(View view) {
        // Create Intent for DisplayIBW Activity
        Intent IBWIntent = new Intent(this, DisplayIBW.class);

        String gender = IdentifyGender();
        IBWIntent.putExtra("Height", height);
        IBWIntent.putExtra("Weight", weight);
        IBWIntent.putExtra("Gender", gender);

        Log.d(this.getLocalClassName(),"Creating intent with a height of " +
                height + " inches, a weight of " + weight + " pounds, for a " + gender +".");

        // Start the next activity
        startActivity(IBWIntent);
    }

    public void Intake(View view) {
        // Create Intent for DisplayIBW Activity
        Intent IntakeIntent = new Intent(this, Intake.class);

        String gender = IdentifyGender();
        IntakeIntent.putExtra("Height", height);
        IntakeIntent.putExtra("Weight", weight);
        IntakeIntent.putExtra("Gender", gender);
        IntakeIntent.putExtra("Age", age);

        Log.d(this.getLocalClassName(),"Creating intent with a height of " +
                height + " inches, a weight of " + weight + " pounds, for a " + gender +".");

        // Start the next activity
        startActivity(IntakeIntent);
    }

    public void resetValues(View view) {
        // Create Intent for Reset Activity
        Intent resetIntent = new Intent(this, ResetValues.class);

        // Start the next activity
        startActivity(resetIntent);

        finish();
    }

    // Height getters and setters
    public static void setHeight(int height2){
        height = height2;
    }
    public int getHeight(){
        return this.height;
    }

    // weight getters and setters
    public static void setWeight(float weight2){
        weight = weight2;
    }
    public float getWeight(){
        return this.weight;
    }

    // gender getters and setters
    public static void setGender(boolean isMale2){
        isMale = isMale2;
    }
    public boolean getGender(){
        return this.isMale;
    }

    // age getters and setters
    public static void setAge(int age2){
        age = age2;
    }
    public float getAge(){
        return this.age;
    }

    // activityLevel getters and setters
    public static void setActivityLevel(int activityLevel2){
        activityLevel = activityLevel2;
    }
    public int getActivityLevel(){
        return this.activityLevel;
    }

    public void DisplayInformation(){

        TextView heightValue = findViewById(R.id.heightValue);
        TextView weightValue = findViewById(R.id.weightValue);
        TextView ageValue = findViewById(R.id.ageValue);
        TextView genderValue = findViewById(R.id.genderValue);
        TextView activityLevelValue = findViewById(R.id.activityLevel);

        if (informationEntered){
<<<<<<< Updated upstream
<<<<<<< Updated upstream
            heightValue.setText("Height: " + getHeight() + " inches.");
            weightValue.setText("Weight: " + getWeight() + " pounds.");
            ageValue.setText("Age: " + getAge() + " years old.");
            genderValue.setText("Gender: " + IdentifyGender() + ".");
            activityLevelValue.setText("Activity Level (0-3): " + getActivityLevel());
=======
=======
            //heightValue.setText("Height: " + getHeight() + " inches.");
            //weightValue.setText("Weight: " + getWeight() + " pounds.");
            //ageValue.setText("Age: " + getAge() + " years old.");
            //genderValue.setText("Gender: " + IdentifyGender() + ".");
            //activityLevelValue.setText("Activity Level (0-3): " + getActivityLevel());
>>>>>>> Stashed changes
            int maintenanceCalories = (int) (getWeight() * 15);
            loseValue.setText("" + (maintenanceCalories - 450));
            maintainValue.setText("" + (maintenanceCalories));
            gainValue.setText("" + (maintenanceCalories + 450));
            consumeValue.setText("" + caloriesConsumed);

            loseValueRemaining.setText("" + ((maintenanceCalories - 450) - caloriesConsumed));
            maintainValueRemaining.setText("" + (maintenanceCalories - caloriesConsumed));
            gainValueRemaining.setText("" + ((maintenanceCalories + 450) - caloriesConsumed));
            int val1 = Integer.parseInt(loseValueRemaining.getText().toString());
            if(val1<0)
                loseValueRemaining.setTextColor(Color.RED);
            else
                loseValueRemaining.setTextColor(Color.GREEN);
            int val2 = Integer.parseInt(maintainValueRemaining.getText().toString());
            if(val2<0)
                maintainValueRemaining.setTextColor(Color.RED);
            else
                maintainValueRemaining.setTextColor(Color.GREEN);
            int val3 = Integer.parseInt(gainValueRemaining.getText().toString());
            if(val3>0)
                gainValueRemaining.setTextColor(Color.RED);
            else
                gainValueRemaining.setTextColor(Color.GREEN);
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
        }

        else {
            heightValue.setText("Height: Not Entered.");
            weightValue.setText("Weight: Not Entered.");
            ageValue.setText("Age: Not Entered.");
            genderValue.setText("Gender: Not Entered.");
            activityLevelValue.setText("Activity Level: Not Entered");
        }
    }


     String IdentifyGender(){
        if (getGender()){
            return "Male";
        }
        else{
            return "Female";
        }
    }
}