package com.example.cybersafe;

import java.io.Serializable;

public class Question implements Serializable {
    String question_text;
    String answer;

    public Question(String question, String answer) {
        this.question_text = question;
        this.answer = answer;
    }

    public String getQuestionText() {
        return this.question_text;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String ans) {
        this.answer = ans;
    }

}
