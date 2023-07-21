package com.example.cybersafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    String ip_address = "";
    apiHandler api = new apiHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Intent nt = getIntent();
        ip_address = nt.getStringExtra("ip");
        if(ip_address.isEmpty()) {
            ip_address = api.getIP();
        }
    }

    public void doSignup(View view) {
        EditText username = (EditText) findViewById(R.id.username_signup);
        EditText email = (EditText) findViewById(R.id.email_signup);
        EditText password = (EditText) findViewById(R.id.password_signup);

        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/appAuth";
        String url = "https://"+ip_address+":8443/appAuth";

        //String params = "param=login&username=test&password=test";
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String e = email.getText().toString();

        if(user.isEmpty() || pass.isEmpty() || e.isEmpty()) {
            Toast.makeText(view.getContext(), "Couldn't create account: empty fields", Toast.LENGTH_LONG).show();
            return;
        }

        RequestBody body = new FormBody.Builder()
                .add("param", "register")
                .add("username", username.getText().toString())
                .add("password", password.getText().toString())
                .add("email", email.getText().toString())
                .build();

        Request req = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //System.out.println(response.body().string());
                String resp = response.body().string();
                if(resp.equals("no")) {
                    runOnUiThread(() -> Toast.makeText(view.getContext(), "failed to create account", Toast.LENGTH_LONG).show());
                }
            }
        });
    }
}