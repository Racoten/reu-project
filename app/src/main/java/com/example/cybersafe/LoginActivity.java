package com.example.cybersafe;

import static org.apache.http.conn.ssl.SSLSocketFactory.SSL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void doLogin(View view) throws NoSuchAlgorithmException, KeyManagementException {

        EditText username = (EditText) findViewById(R.id.username_input);
        EditText password = (EditText) findViewById(R.id.password_input);

        TextView error_box = (TextView) findViewById(R.id.login_error_box);

        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        String url = "https://10.0.2.2:8443/appAuth";
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
                    startActivity(homeIntent);
                } else {
                    error_box.setText("Error: failed to login");
                }
            }
        });

    }

}