package com.example.myfitnesslad;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayIBW extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibw);

        TextView results = findViewById(R.id.results);
        TextView range = findViewById(R.id.range);

        // If the user has entered information, or there is information in "profile".txt,
        // we can calculate with those values
        if (MainActivity.informationEntered) {
            // -------------   Get information from Main Page
            Bundle IBWBundle = getIntent().getExtras();
            int height = IBWBundle.getInt("Height");
            float weight = IBWBundle.getFloat("Weight");
            String gender = IBWBundle.getString("Gender");

            // Calculate the two ranges
            float lowerIbw = millerFormula(height, gender);
            float upperIbw = hamwiFormula(height, gender);

            // -------------   Change text to reflect valid information.
            results.setText("As a " + (height / 12) + " foot, " + (height % 12)
                    + " inch " + gender + ", weighing in at " + weight
                    + " pounds.\n\nYour ideal body weight range is " + Math.round(lowerIbw) + " - "
                    + Math.round(upperIbw) + " pounds");
            range.setText(Math.round(lowerIbw) + " - " + Math.round(upperIbw));
        }

        // If there is no information in "profile.txt" they will need to go enter values first.
        else{
            results.setText("No Information to display.\nEnter body values first.");
        }
    }

    // Miller formula for calculating IBW
    float millerFormula(int height, String gender){
        if (gender.equals("Male")){
            return (float) ((56.2 + (1.41 * (height - 60))) * 2.2);
        }
        else {
            return (float) ((53.1 + (1.36 * (height - 60))) * 2.2);
        }
    }

    //Hamwi formula for calculating IBW
    float hamwiFormula(int height, String gender){
        if (gender.equals("Male")){
            return (float) (106 + (6 * (height - 60)));
        }
        else {
            return (float) (100 + (5 * (height - 60)));
        }
    }
}
