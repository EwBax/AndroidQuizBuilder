package com.ewbax.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.*;

public class QuizActivity extends AppCompatActivity {

    TextView questionNumberTV;
    TextView definitionTV;
    Button term1Btn;
    Button term2Btn;
    Button term3Btn;
    Button term4Btn;
    Button nextBtn;

    private ArrayList<String> definitions;
    private ArrayList<String> terms;

    private Map<String, String> answers;

    private int score;
    private int numQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        definitions = getIntent().getStringArrayListExtra("definitions");
        terms = getIntent().getStringArrayListExtra("terms");

        // pairing definitions and terms in answers HashMap
        answers = new HashMap<>();
        for (int i = 0; i < definitions.size(); i++) {
            answers.put(definitions.get(i), terms.get(i));
        }

        // Assigning views by ID
        questionNumberTV = findViewById(R.id.questionNumberTV);
        definitionTV = findViewById(R.id.definitionTV);
        term1Btn = findViewById(R.id.term1Btn);
        term2Btn = findViewById(R.id.term2Btn);
        term3Btn = findViewById(R.id.term3Btn);
        term4Btn = findViewById(R.id.term4Btn);
        nextBtn = findViewById(R.id.nextBtn);

        // Assigning listener to buttons

    } // end onCreate method

    public View.OnClickListener onButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            System.out.println(v.getId() + "Clicked.");

        } // end method on click
    }; // end inner class

} // end QuizActivity Class
