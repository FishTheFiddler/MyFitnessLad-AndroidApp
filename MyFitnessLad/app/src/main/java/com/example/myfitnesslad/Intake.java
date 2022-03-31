package com.example.myfitnesslad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        //Upon creation, load the
        //LoadData();

    }

    public void CreateMeal(View view) {

        EditText inputCarbs = findViewById(R.id.inputCarbs);
        EditText inputFats = findViewById(R.id.inputFats);
        EditText inputProtein = findViewById(R.id.inputProtein);
        EditText inputCalories = findViewById(R.id.inputCalories);
        TextView totalCalories = findViewById(R.id.totalCalories);
        TextView ocBox = findViewById(R.id.ocBox);
        Meal meal;

        int calorieInput = 0, carbsInput = 0, fatsInput = 0, proteinInput = 0;

        if (!inputCalories.getText().toString().isEmpty()) {
            calorieInput = Integer.parseInt(inputCalories.getText().toString());
            meal = new Meal(calorieInput,carbsInput, fatsInput, proteinInput);
        } else {
            carbsInput = Integer.parseInt(inputCarbs.getText().toString());
            fatsInput = Integer.parseInt(inputFats.getText().toString());
            proteinInput = Integer.parseInt(inputProtein.getText().toString());
            meal = new Meal(calorieInput,carbsInput, fatsInput, proteinInput);
        }

        //override when user knows calories
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
            System.out.println("ANOTHER ELSE");
            totalCalories.setText("" + inputCalories.getText().toString());
            if (calorieInput < 2200) {
                System.out.println("1");
                ocBox.setText("Balanced");
            } else {
                System.out.println("2");
                ocBox.setText("Overall high calorie intake!");
            }
        }

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