package com.example.myfitnesslad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static int height = 72;
    static float weight = 176;
    static boolean isMale = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void resetValues(View view) {
        // Create Intent for Reset Activity
        Intent resetIntent = new Intent(this, ResetValues.class);

        // Start the next activity
        startActivity(resetIntent);
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


     String IdentifyGender(){
        if (getGender()){
            return "Male";
        }
        else{
            return "Female";
        }
    }
}