package com.example.cybersafe;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.net.Inet4Address;

public class Question implements Parcelable {
    private String question_text;
    private String answer;
    private Integer weight;
    private Integer id;
    private Integer table;

    public Question(String question, String answer, Integer w, Integer id, Integer table) {
        this.question_text = question;
        this.answer = answer;
        this.id = id;
        this.weight = w;
        this.table = table;
    }

    public Question(Parcel source) {
        question_text = source.readString();
        answer = source.readString();
        weight = source.readInt();
        id = source.readInt();
        table = source.readInt();
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

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(question_text);
        parcel.writeString(answer);
        parcel.writeInt(weight);
        parcel.writeInt(id);
        parcel.writeInt(table);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }
}
