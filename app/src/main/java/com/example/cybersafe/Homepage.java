package com.example.cybersafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Homepage extends AppCompatActivity {
    String ip_address = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Intent nt = getIntent();
        ip_address = nt.getStringExtra("ip");
    }

    public void openEmailQuestionnaire(View view) {
        Intent emailquestions = new Intent(this, EmailQuestionnaire.class);
        emailquestions.putExtra("ip", ip_address);
        startActivity(emailquestions);
    }

    public void openBrowserSecurityQuestionnaire(View view) {
        Intent browserquestions = new Intent(this, BrowserSecurityQuestionnaire.class);
        browserquestions.putExtra("ip", ip_address);
        startActivity(browserquestions);
    }

    public void openSMSQuestionnaire(View view) {
        Intent sms_questions = new Intent(this, SMSQuestionnaire.class);
        sms_questions.putExtra("ip", ip_address);
        startActivity(sms_questions);
    }


    public void openWIFIQuestionnaire(View view) {
        Intent wifi_questions = new Intent(this, WIFIQuestionnaire.class);
        wifi_questions.putExtra("ip", ip_address);
        startActivity(wifi_questions);
    }


}