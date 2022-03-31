package com.example.myfitnesslad;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use the activity_history.xml file
        setContentView(R.layout.activity_history);
        // On creation, display the history
        DisplayHistory();
    }

    // This will open our "meals.txt" and read from it. It will read it line by line and output it
    // into the blank text box.
    void DisplayHistory(){
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
    }

    // This will open "meals.txt" (history) and overwrite with a blank string. (clearing history)
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
        // After clearing the history, we re-call Display function to read the file and display
        // it accordingly.
        DisplayHistory();
    }
}
