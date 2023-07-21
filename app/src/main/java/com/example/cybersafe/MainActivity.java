package com.example.cybersafe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String ip_address = "";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         EditText ip = findViewById(R.id.ip_input);
         ip.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void afterTextChanged(Editable editable) {
                    ip_address = editable.toString();
             }
         });
    }

    public void loginPage(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.putExtra("ip", ip_address);
        startActivity(loginIntent);
    }

    public void signupPage(View view) {
        Intent signupIntent = new Intent(this, SignupActivity.class);
        signupIntent.putExtra("ip", ip_address);
        startActivity(signupIntent);
    }
}