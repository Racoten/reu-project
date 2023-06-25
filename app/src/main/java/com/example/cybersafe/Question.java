package com.example.cybersafe;

public class Question {
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
