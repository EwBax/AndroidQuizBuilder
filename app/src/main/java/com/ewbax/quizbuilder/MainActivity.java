package com.ewbax.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView welcomeTV;
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeTV = findViewById(R.id.welcomeTV);
        startBtn = findViewById(R.id.startBtn);

        // adding listener to progress to next activity
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    // calling method to read from raw file
                    String delimitedQuizData = readQuizFile();

                    // Creating array lists to store definitions and terms in parallel
                    ArrayList<String> definitions = new ArrayList<>();
                    ArrayList<String> terms = new ArrayList<>();

                    // Calling method to split quiz data
                    splitQuizData(delimitedQuizData, definitions, terms);

                    // Creating intent for next activity
                    Intent quizActivity = new Intent(MainActivity.this, QuizActivity.class);

                    // bundling definitions and terms
                    quizActivity.putStringArrayListExtra("definitions", definitions);
                    quizActivity.putStringArrayListExtra("terms", terms);

                    // starting next activity
                    startActivity(quizActivity);

                } catch (QuizNotReadException e) {
                    Toast.makeText(getApplicationContext(), "Error: Could not read quiz from file. Please restart app.", Toast.LENGTH_LONG).show();
                } // end try/catch

            }
        }); //end onClickListener

    } //end onCreate method


    // Method to split and sort definitions and terms appropriately
    private void splitQuizData(String delimitedQuizData, ArrayList<String> definitions, ArrayList<String> terms) {

        String[] temp = delimitedQuizData.split("[\\$|\\n]");

        // Looping and storing each definition and term
        // definitions are at even indices and terms are odd
        for (int i = 0; i < temp.length; i+=2) {

            definitions.add(temp[i]);
            terms.add(temp[i+1]);

        }

    } // end getQuizData Method


    // method to read contents of quiz file intro string
    private String readQuizFile() throws QuizNotReadException {

        // Method body based on "Demo - Internal Read Write & Res Raw Read" by David Russell

        String temp = null;
        StringBuilder delimitedQuizData = new StringBuilder();
        BufferedReader br = null;

        try {
            InputStream is = getResources().openRawResource(R.raw.quiz);
            br = new BufferedReader(new InputStreamReader(is));
            System.out.println("Quiz file in RAW is open");

            while ((temp = br.readLine()) != null) {
                delimitedQuizData.append(temp);
                delimitedQuizData.append("\n");
            }
            is.close();
            System.out.println("Quiz file in RAW is closed");

        } catch (IOException e) {
            e.printStackTrace();
            throw new QuizNotReadException();
        }catch(Exception e){
            e.printStackTrace();
            throw new QuizNotReadException();
        } // end try/catch

        return delimitedQuizData.toString();

    } // end readQuizFile method


} // end MainActivity class