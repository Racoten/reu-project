package com.example.cybersafe;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Question implements Parcelable {
    private String question_text;
    private String answer;
    boolean yes;
    boolean no;

    public Question(String question, String answer) {
        this.question_text = question;
        this.answer = answer;
    }

    public Question(Parcel source) {
        question_text = source.readString();
        answer = source.readString();
    }

    public String getQuestionText() {
        return this.question_text;
    }

    public void setQuestionText(String q) {
        this.question_text = q;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String ans) {
        this.answer = ans;
    }

    public boolean isYes() { return yes; }
    public boolean isNo() { return no; }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(question_text);
        parcel.writeString(answer);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
