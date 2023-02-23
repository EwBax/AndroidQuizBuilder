package com.ewbax.quizbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    TextView scoreTV;
    Button tryAgainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Getting score and numQuestions from intent bundle
        int score = getIntent().getIntExtra("score", 0);
        int numQuestions = getIntent().getIntExtra("numQuestions", 0);

        // Assigning views by ID
        scoreTV = findViewById(R.id.scoreTV);
        tryAgainBtn = findViewById(R.id.tryAgainBtn);

        // Setting score TV to display score
        String scoreString = score + "/" + numQuestions;
        scoreTV.setText(scoreString);

        // Setting listener for tryAgainBtn
        tryAgainBtn.setOnClickListener(v -> {

            // Going back to mainActivity to restart quiz
            Intent mainActivity = new Intent(ResultsActivity.this, MainActivity.class);
            startActivity(mainActivity);

        }); // end inner class

    } // end onCreate method

} // end ResultsActivity class
