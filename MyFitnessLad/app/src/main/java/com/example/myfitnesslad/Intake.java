package com.example.myfitnesslad;

import android.icu.util.Calendar;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;

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
        TextView errorBox = findViewById(R.id.errorBox);
        // Create a new instance of the "Meal.java" class
        Meal meal;

        /* TODO: This whole section requires Data validation. If a user enters a string or a
           TODO: negative, it will just crash the app.  */

        // Declare and instantiate variables to 0.
        int calorieInput = 0, carbsInput = 0, fatsInput = 0, proteinInput = 0;

        // If the calorie box DOES have values in it,  then that is our calorie value and we
        // can proceed by creating a new meal with that information
        // ------------------------------------------------------
        // If the calorie box DOESN'T have values in it,  then we will grab values from our other fields
        // and create a meal that way. Those parameters will calculate our calories later.
        try {
            if (!inputCalories.getText().toString().isEmpty()) {
                calorieInput = Integer.parseInt(inputCalories.getText().toString());
                meal = new Meal(calorieInput, carbsInput, fatsInput, proteinInput);
                errorBox.setText("");
            } else {
                carbsInput = Integer.parseInt(inputCarbs.getText().toString());
                fatsInput = Integer.parseInt(inputFats.getText().toString());
                proteinInput = Integer.parseInt(inputProtein.getText().toString());
                meal = new Meal(calorieInput, carbsInput, fatsInput, proteinInput);
                errorBox.setText("");
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
                } else if (meal.ocCarbs(carbsInput)) {
                    s += "High carbohydrate intake! ";
                } else if (meal.ocFats(fatsInput)) {
                    s += "High fat intake! ";
                } else if (meal.ocProtein(proteinInput)) {
                    s += "High protein intake! ";
                } else if (s == "") {
                    s += "Balanced";
                }
                ocBox.setText(s);
                totalCalories.setText("" + calorieInput);
            } else {
                totalCalories.setText("" + inputCalories.getText().toString());
                if (calorieInput < 2200) {
                    ocBox.setText("Balanced");
                } else if (calorieInput >= 2200) {
                    ocBox.setText("Overall high calorie intake!");
                }
            }
        } catch (InputMismatchException e) {
            errorBox.setText("ERROR: All fields must be equal to or greater to 0. Only whole numbers.");
            e.printStackTrace();
        } catch (Exception e) {
            errorBox.setText("ERROR: Fill out either top field, or bottom 3.");
            e.printStackTrace();
        }
        // Once we have calculated how many calories we have consumed, we must save this
        // information to our "meals.txt" (history) and to our "caloriesConsumed.txt"
        if (calorieInput != 0){
            try {
                AddCaloriesConsumed(calorieInput);
                SaveData(String.valueOf(calorieInput));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Will save the calorie information along with a time stamp
    void SaveData(String calories) throws IOException {

        LocalTime time = null;
        String finalTime = "";
        String finalDate = "";
        Date date = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("HH:mm");
            time = LocalTime.now();
            finalTime = time.format(formattedTime);
            date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("MM-dd");
            finalDate = dateFormat.format(date);
        }
        else{
            finalTime = "";
        }

        FileOutputStream fos = null;

        try {
            fos = openFileOutput("meals.txt", MODE_APPEND);
            fos.write((finalDate +" " + finalTime + " - " + calories + " cal consumed").getBytes());
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