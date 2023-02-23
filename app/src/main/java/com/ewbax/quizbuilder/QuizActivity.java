package com.ewbax.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private ArrayList<Button> termButtons;

    private Map<String, String> answers;

    private int score;
    private int numQuestions;
    private int currentQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Getting definitions and terms from bundle
        definitions = getIntent().getStringArrayListExtra("definitions");
        terms = getIntent().getStringArrayListExtra("terms");

        score = 0;
        numQuestions = definitions.size();
        currentQuestion = 0;

        // pairing definitions and terms in answers HashMap
        answers = new HashMap<>();
        for (int i = 0; i < definitions.size(); i++) {
            answers.put(definitions.get(i), terms.get(i));
        }

        // shuffling definitions
        Collections.shuffle(definitions);

        // Assigning views by ID
        questionNumberTV = findViewById(R.id.questionNumberTV);
        definitionTV = findViewById(R.id.definitionTV);
        term1Btn = findViewById(R.id.term1Btn);
        term2Btn = findViewById(R.id.term2Btn);
        term3Btn = findViewById(R.id.term3Btn);
        term4Btn = findViewById(R.id.term4Btn);
        nextBtn = findViewById(R.id.nextBtn);

        // Assigning listener to buttons
        term1Btn.setOnClickListener(onButtonClicked);
        term2Btn.setOnClickListener(onButtonClicked);
        term3Btn.setOnClickListener(onButtonClicked);
        term4Btn.setOnClickListener(onButtonClicked);
        nextBtn.setOnClickListener(onButtonClicked);

        // Adding term buttons to arrayList for easier manipulation of buttons
        termButtons = new ArrayList<>();
        termButtons.add(term1Btn);
        termButtons.add(term2Btn);
        termButtons.add(term3Btn);
        termButtons.add(term4Btn);

        // Setting up first question
        nextQuestion();

    } // end onCreate method


    // Common listener for buttons
    public View.OnClickListener onButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                // Shared condition for each term button
                case R.id.term1Btn:
                case R.id.term2Btn:
                case R.id.term3Btn:
                case R.id.term4Btn:

                    checkAnswer((Button) v);

                    // Showing next button
                    nextBtn.setVisibility(View.VISIBLE);

                    // Burning definition from list
                    definitions.remove(0);
                    break;

                case R.id.nextBtn:

                    // If there are more questions left, go to next question
                    if (definitions.size() > 0) {

                        // Reset the term button colors and re-enable them
                        resetTermButtons();

                        // Hide next button and go to next question
                        nextBtn.setVisibility(View.INVISIBLE);
                        nextQuestion();

                    } else {

                        // Move on to results page
                        Intent resultsActivity = new Intent(QuizActivity.this, ResultsActivity.class);

                        // Passing score and number of questions via bundle/intent
                        resultsActivity.putExtra("score", score);
                        resultsActivity.putExtra("numQuestions", numQuestions);

                        startActivity(resultsActivity);

                    } // end if/else

            } // end switch

        } // end method on click
    }; // end inner class


    // Method to set up the next question
    private void nextQuestion() {

        currentQuestion++;

        // Setting question number text
        String temp = getResources().getString(R.string.questionNumber) + " " + currentQuestion + "/" + numQuestions;
        questionNumberTV.setText(temp);

        // setting definition text, always uses index 0 because definition is burned from list after use
        String def = definitions.get(0);
        definitionTV.setText(def);

        // Shuffling terms and list of buttons for randomization
        Collections.shuffle(terms);
        Collections.shuffle(termButtons);


        int termIndex = 0;

        // assigning terms all of the buttons except one
        for (int i = 0; i < (termButtons.size() - 1); i++) {

            // Checking if the current term is the answer, and skipping to next one if so
            if (terms.get(termIndex).equals(answers.get(def))) {
                termIndex++;
            }

            // Setting term button text
            termButtons.get(i).setText(terms.get(termIndex));
            termIndex++;

        } // end for loop

        // Setting the last button in the list to be the answer (this is not necessarily the bottom button as we shuffled the list)
        termButtons.get(termButtons.size() - 1).setText(answers.get(def));

    } // end nextQuestion method


    // Method to check if selected term was correct and update
    private void checkAnswer(Button b) {

        // the current definition will always be index 0 because they are burned from list after use
        // Checking if correct and changing color and incrementing score appropriately
        if (b.getText().equals(answers.get(definitions.get(0)))) {
            score++;
            b.setBackgroundColor(getResources().getColor(R.color.buttonColorCorrect, getTheme()));
        } else {
            b.setBackgroundColor(getResources().getColor(R.color.buttonColorIncorrect, getTheme()));
        }

        // disabling term buttons after answer has been selected
        for (Button termBtn: termButtons) {

            // Changing colors of all buttons NOT selected
            if (!termBtn.equals(b)) {
                termBtn.setBackgroundColor(getResources().getColor(R.color.buttonColorDisabled, getTheme()));
            }
            termBtn.setEnabled(false);

        } // end for loop

    } // end checkAnswer method


    // Method to reset term buttons color and re-enable them
    private void resetTermButtons() {

        for (Button b: termButtons) {
            b.setEnabled(true);
            b.setBackgroundColor(getResources().getColor(R.color.buttonColorRegular, getTheme()));
        }

    } // end resetTermButtons method

} // end QuizActivity Class
