package com.example.myfitnesslad;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Intake extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use the activity_intake.xml file
        setContentView(R.layout.activity_intake);

    }

    public void CreateMeal(View view) {

        // These are all the fields and elements from activity_intake.xml
        EditText inputCarbs = findViewById(R.id.inputCarbs);
        EditText inputFats = findViewById(R.id.inputFats);
        EditText inputProtein = findViewById(R.id.inputProtein);
        EditText inputCalories = findViewById(R.id.inputCalories);
        TextView totalCalories = findViewById(R.id.totalCalories);
        TextView ocBox = findViewById(R.id.ocBox);
        // Create a new instance of the "Meal.java" class
        Meal meal;

        /* TODO: This whole section requires Data validation. If a user enters a string or a
           TODO: negative, it will just crash the app.  */

        // Declare and instantiate variables to 0.
        int calorieInput = 0, carbsInput = 0, fatsInput = 0, proteinInput = 0;

        // If the calorie box DOES have values in it,  then that is our calorie value and we
        // can proceed by creating a new meal with that information
        // ------------------------------------------------------
        // If the calorie box DOESN'T values in it,  then we will grab values from our other fields
        // and create a meal that way. Those parameters will calculate our calories later.
        if (!inputCalories.getText().toString().isEmpty()) {
            calorieInput = Integer.parseInt(inputCalories.getText().toString());
            meal = new Meal(calorieInput,carbsInput, fatsInput, proteinInput);
        } else {
            carbsInput = Integer.parseInt(inputCarbs.getText().toString());
            fatsInput = Integer.parseInt(inputFats.getText().toString());
            proteinInput = Integer.parseInt(inputProtein.getText().toString());
            meal = new Meal(calorieInput,carbsInput, fatsInput, proteinInput);
        }

        // If the calorie box DOES have values in it, this will be overlooked
        // ------------------------------------------------------
        // If the calorie box DOESN'T values in it, this will calculate the calories using other
        // values.
        if (calorieInput <= 0) {
            calorieInput = meal.calculateCalories();
            System.out.println(calorieInput);
            String s = "";
            if (meal.ocCalories(calorieInput)) {
                s += "Overall high calorie intake! ";
            }
            if (meal.ocCarbs(carbsInput)) {
                s += "High carbohydrate intake! ";
            }
            if (meal.ocFats(fatsInput)) {
                s += "High fat intake! ";
            }
            if (meal.ocProtein(proteinInput)) {
                s += "High protein intake! ";
            }
            if (s == "") {
                s += "Balanced";
            }
            ocBox.setText(s);
            totalCalories.setText("" + calorieInput);
        } else {
            totalCalories.setText("" + inputCalories.getText().toString());
            if (calorieInput < 2200) {
                ocBox.setText("Balanced");
            } else {
                ocBox.setText("Overall high calorie intake!");
            }
        }

        // Once we ahve calculated how many calories we have consumed, we must save this
        // information to our "meals.txt" (history) and to our "caloriesConsumed.txt"
        try {
            AddCaloriesConsumed(calorieInput);
            SaveData(String.valueOf(calorieInput));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Will save the calorie information along with a time stamp
    void SaveData(String calories) throws IOException {


        LocalTime time = null;
        String finalTime = "";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("HH:mm");
            time = LocalTime.now();
            finalTime = time.format(formattedTime);
        }
        else{
            finalTime = "";
        }

        FileOutputStream fos = null;

        try {
            fos = openFileOutput("meals.txt", MODE_APPEND);
            fos.write(("At " + finalTime + " hours, you ate " + calories + " cal").getBytes());
            fos.write("\n".getBytes());

            Toast.makeText(this, "Saved to " + getFilesDir() + "/meals.txt",
                    Toast.LENGTH_LONG).show();
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


    // Opens the file caloriesConsumed.txt and will add our calories eaten to the existing value
    // Example: Open .txt, read 500 from the .txt... Add 300 + 500...write 800 back to the .txt
    void AddCaloriesConsumed(int newCalories){
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

        tempCalories += newCalories;

        FileOutputStream fos = null;
        try {
            fos = openFileOutput("caloriesConsumed.txt", MODE_PRIVATE);
            fos.write(String.valueOf(tempCalories).getBytes());
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

    // Simple function that will clear out all of our input elements
    public void ClearFields(View view){
        EditText inputCarbs = findViewById(R.id.inputCarbs);
        EditText inputFats = findViewById(R.id.inputFats);
        EditText inputProtein = findViewById(R.id.inputProtein);
        EditText inputCalories = findViewById(R.id.inputCalories);

        inputCarbs.getText().clear();
        inputFats.getText().clear();
        inputProtein.getText().clear();
        inputCalories.getText().clear();
    }

}