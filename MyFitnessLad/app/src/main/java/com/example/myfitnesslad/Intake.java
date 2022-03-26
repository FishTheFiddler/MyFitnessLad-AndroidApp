package com.example.myfitnesslad;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
//import com.opencsv.CSVReader;
//import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Intake extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake);
        LoadData();

    }

    public void CreateMeal(View view) {

        EditText inputCarbs = findViewById(R.id.inputCarbs);
        EditText inputFats = findViewById(R.id.inputFats);
        EditText inputProtein = findViewById(R.id.inputProtein);
        EditText inputCalories = findViewById(R.id.inputCalories);
        TextView totalCalories = findViewById(R.id.totalCalories);
        TextView ocBox = findViewById(R.id.ocBox);

        int calorieInput = 0;

        //int carbsInput = 0;
        //int fatsInput = 0;
        //int proteinInput = 0;

        int carbsInput = Integer.parseInt(inputCarbs.getText().toString());
        int fatsInput = Integer.parseInt(inputFats.getText().toString());
        int proteinInput = Integer.parseInt(inputProtein.getText().toString());
        if (!inputCalories.getText().toString().isEmpty()) {
            calorieInput = Integer.parseInt(inputCalories.getText().toString());
        }

        Meal meal = new Meal(carbsInput, fatsInput, proteinInput);
        int tempCalories = meal.calculateCalories();

        //override when user knows calories
        if (calorieInput <= 0) {
            String s = "";
            if (meal.ocCalories(tempCalories)) {
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
            totalCalories.setText("" + tempCalories);
        } else {
            totalCalories.setText("" + inputCalories.getText().toString());
            if (calorieInput < 2200) {
                ocBox.setText("Balanced");
            } else {
                ocBox.setText("Overall high calorie intake!");
            }
        }

        try {
            SaveData(inputCalories.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoadData();
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
            //String output = String.valueOf(tempCalories);
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
        //String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCsvFile.csv"); // Here csv file name is MyCsvFile.csv
        /*
                try {
                    Log.d(this.getLocalClassName(),"button clicked");

                    File csv = new File (Environment.getExternalStorageDirectory() + "/Meal.csv");
                    Log.d(this.getLocalClassName(),"csv variable");
                    CSVWriter writer = null;
                    if (!csv.exists()) {
                        Log.d(this.getLocalClassName(),"if statement reached");
                        // creates the missing folders for this file
                        csv.mkdirs();
                    }
                    Log.d(this.getLocalClassName(),"about to start writer");
                    writer = new CSVWriter(new FileWriter(csv.getAbsolutePath()));
                    Log.d(this.getLocalClassName(),"after writer");

                    List<String[]> data = new ArrayList<String[]>();
                    data.add(new String[]{"Country", "Capital"});
                    data.add(new String[]{"India", "New Delhi"});
                    data.add(new String[]{"United States", "Washington D.C"});
                    data.add(new String[]{"Germany", "Berlin"});

                    Log.d(this.getLocalClassName(),"about to save");
                    writer.writeAll(data); // data is adding to csv
                    Log.d(this.getLocalClassName(),"saved");
                    writer.close();
                    //callRead();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error Saving Data", Toast.LENGTH_SHORT).show();
                }
            }

        */

        /* //String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Meal.csv"); // Here csv file name is MyCsvFile.csv
        File csv = new File (Environment.getExternalStorageDirectory() + "/Meal.csv");
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv.getAbsolutePath()));

            List<String[]> mealCsv = new ArrayList<String[]>();
            mealCsv.add(new String[]{"Calories Consumed"});
            mealCsv.add(new String[]{String.valueOf(tempCalories)});

            writer.writeAll(mealCsv); // data is adding to csv

            writer.close();
            // callRead();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error Saving Data", Toast.LENGTH_SHORT).show();

        }
    }*/
    }

    void LoadData() {
        FileInputStream fis = null;

        try {
            fis = openFileInput("meals.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            TextView historyBox = findViewById(R.id.historyBox);
            historyBox.setText(sb.toString());

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
        /*
        try {
            File csv = new File(Environment.getExternalStorageDirectory() + "/MyCsvFile.csv");
            CSVReader reader = new CSVReader(new FileReader(csv.getAbsolutePath()));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                System.out.println(nextLine[0] + nextLine[1] + "etc...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }
    }*/
    }

    public void ResetHistory(View view) {

        FileOutputStream fos = null;

        try {
            fos = openFileOutput("meals.txt", MODE_PRIVATE);
            fos.write("".getBytes());
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
        LoadData();
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



        //TODO: SEND CALORIE DATA TO MAIN
