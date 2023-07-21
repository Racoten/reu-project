package com.example.cybersafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    String ip_address = "";
    apiHandler api = new apiHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent nt = getIntent();

        ip_address = nt.getStringExtra("ip");
        if(ip_address.isEmpty()) {
           ip_address = api.getIP();
        }
    }

    public void doLogin(View view) throws NoSuchAlgorithmException, KeyManagementException {

        EditText username = (EditText) findViewById(R.id.username_input);
        EditText password = (EditText) findViewById(R.id.password_input);

        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/appAuth";
        String url = "https://"+ip_address+":8443/appAuth";

        //String params = "param=login&username=test&password=test";

        RequestBody body = new FormBody.Builder()
                .add("param", "login")
                .add("username", username.getText().toString())
                .add("password", password.getText().toString())
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
                String resp_string = response.body().string();
                //System.out.println(resp_string);
                if(!resp_string.startsWith("not")) {
                    // start questionnaire home
                    Intent homeIntent = new Intent(LoginActivity.this, Homepage.class);
                    homeIntent.putExtra("ip", ip_address);
                    startActivity(homeIntent);
                } else {
                    runOnUiThread(() -> Toast.makeText(view.getContext(), "Failed to log in", Toast.LENGTH_LONG).show());

                }
            }
        });

    }

}