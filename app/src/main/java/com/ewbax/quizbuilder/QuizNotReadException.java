package com.ewbax.quizbuilder;

public class QuizNotReadException extends Exception {

    public QuizNotReadException() {
        super("Unable to read quiz data from file");
    }

}
