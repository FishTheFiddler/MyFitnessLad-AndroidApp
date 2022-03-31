package com.example.myfitnesslad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    static int height = 0;
    static float weight = 0;
    static boolean isMale = true;
    static int age = 0;
    static int activityLevel = 0;
    static int caloriesConsumed = 0;
    static boolean informationEntered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use the activity_main.xml file
        setContentView(R.layout.activity_main);

        // Read the user file "profile.txt", if it doesn't exist yet due to first time users, no values will be displayed
        ReadFile();
        // Read the calorie file "caloriesConsumed.txt" if it doesn't exist yet, default is 0
        ReadCalorieFile();
        // Displays the values based on the file "profile.txt"
        DisplayInformation();
    }

    @Override
    public void onResume() {
        super.onResume();  // Calling the superclass method first
        // Read the user file "profile.txt", if it doesn't exist yet due to first time users, no values will be displayed
        ReadFile();
        // Read the calorie file "caloriesConsumed.txt" if it doesn't exist yet, default is 0
        ReadCalorieFile();
        // Displays the values based on the file "profile.txt"
        DisplayInformation();
    }

    // This function will load the Body Mass Index activity to calculate your BMI
    public void BMI(View view) {
        // Create Intent for DisplayBMI Activity
        Intent BMIIntent = new Intent(this, DisplayBMI.class);

        BMIIntent.putExtra("Height", getHeight());
        BMIIntent.putExtra("Weight", getWeight());

        // Log that we are creating an intent
        Log.d(this.getLocalClassName(),"Creating intent with a height of " +
                getHeight() + " inches, and a weight of " + getWeight() + " pounds");

        // Start the next activity
        startActivity(BMIIntent);
    }

    // This function will load the Ideal body Weight activity to calculate your IBW
    public void IBW(View view) {
        // Create Intent for DisplayIBW Activity
        Intent IBWIntent = new Intent(this, DisplayIBW.class);

        String gender = IdentifyGender();
        IBWIntent.putExtra("Height", getHeight());
        IBWIntent.putExtra("Weight", getWeight());
        IBWIntent.putExtra("Gender", gender);

        // Log that we are creating an intent
        Log.d(this.getLocalClassName(),"Creating intent with a height of " +
                height + " inches, a weight of " + weight + " pounds, for a " + gender +".");

        // Start the next activity
        startActivity(IBWIntent);
    }

    // This function will load the intake activity to enter in calories eaten
    public void Intake(View view) {
        // Create Intent for DisplayIBW Activity
        Intent IntakeIntent = new Intent(this, Intake.class);

        String gender = IdentifyGender();
        IntakeIntent.putExtra("Height", getHeight());
        IntakeIntent.putExtra("Weight", getWeight());
        IntakeIntent.putExtra("Gender", gender);
        IntakeIntent.putExtra("Age", getAge());

        // Log that we are creating an intent
        Log.d(this.getLocalClassName(),"Creating intent headed for the \"Intake\" activity");

        // Start the next activity
        startActivity(IntakeIntent);
    }

    // This function will load the History activity to view meal History
    public void History(View view){
        // Create Intent for History Activity
        Intent historyIntent = new Intent(this, History.class);

        // Log that we are creating an intent
        Log.d(this.getLocalClassName(),"Creating intent headed for the \"History\" activity");

        // Start the next activity
        startActivity(historyIntent);
    }

    // This function will open the Reset Values activity in order to change body parameters
    public void resetValues(View view) {
        // Create Intent for Reset Activity
        Intent resetIntent = new Intent(this, ResetValues.class);

        // Log that we are creating an intent
        Log.d(this.getLocalClassName(),"Creating intent to the \"Reset-Values\" activity");

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

    // This function will update all existing fields with
    public void DisplayInformation(){

        TextView loseValue = findViewById(R.id.loseWeight);
        TextView maintainValue = findViewById(R.id.maintainWeight);
        TextView gainValue = findViewById(R.id.gainWeight);
        TextView consumeValue = findViewById(R.id.consumed);

        if (informationEntered){
            int maintenanceCalories = (int) (getWeight() * 15);
            loseValue.setText("" + (maintenanceCalories - 450));
            maintainValue.setText("" + (maintenanceCalories));
            gainValue.setText("" + (maintenanceCalories + 450));
            consumeValue.setText("" + caloriesConsumed);
        }

        else {
            loseValue.setText("N/A");
            maintainValue.setText("N/A");
            gainValue.setText("N/A");
            consumeValue.setText("N/A");
        }
    }

    // This function will open the "profile.txt" file and insert the values into correct variables
    void ReadFile(){
        FileInputStream fis = null;

        String dateToday = "";
        Date date = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            date = Calendar.getInstance().getTime();
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateToday = dateFormat.format(date);

        try {
            fis = openFileInput("profile.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                String[] splitStr=text.split(", ");
                if(splitStr.length == 7){
                    setHeight(Integer.parseInt(splitStr[0]));
                    setWeight(Float.parseFloat(splitStr[1]));
                    setAge(Integer.parseInt(splitStr[2]));
                    String gender = (splitStr[3]);
                    if (gender.equals("Male")){
                        setGender(true);
                    } else {
                        setGender(false);
                    }
                    setActivityLevel(Integer.parseInt(splitStr[4]));
                    if ((splitStr[5]).equals("true")){
                        informationEntered = true;
                    }
                    String tempDate = (splitStr[6]);
                    if (!tempDate.equals(dateToday)){
                        dateToday = (splitStr[6]);
                        fis.close();
                        FileOutputStream fos = null;
                        try {
                            fos = openFileOutput("caloriesConsumed.txt", MODE_PRIVATE);
                            fos.write("0".getBytes());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (fos != null) {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // This function will open the "caloriesConsumed.txt" file and insert the values into variables
    void ReadCalorieFile() {
        FileInputStream fis = null;
        int tempCalories = 0;
        String output = "";

        try {
            fis = openFileInput("caloriesConsumed.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            output = sb.toString();
            tempCalories = Integer.parseInt(output);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        caloriesConsumed = tempCalories;
    }

    // This function simply results in turning a gender boolean into a string to be readable.
    String IdentifyGender(){
        if (getGender()){
            return "Male";
        }
        else{
            return "Female";
        }
    }
}