package com.example.cybersafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    public void openEmailQuestionnaire(View view) {
        Intent emailquestions = new Intent(this, EmailQuestionnaire.class);
        startActivity(emailquestions);
    }

    public void openBrowserSecurityQuestionnaire(View view) {
        Intent browserquestions = new Intent(this, BrowserSecurityQuestionnaire.class);
        startActivity(browserquestions);
    }
}