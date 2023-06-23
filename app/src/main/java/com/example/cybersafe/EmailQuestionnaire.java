package com.example.cybersafe;

import static org.apache.http.conn.ssl.SSLSocketFactory.SSL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

public class EmailQuestionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_questionnaire);
        getEmailQuestions();
    }

    public void getEmailQuestions() {
        TextView question = (TextView) findViewById(R.id.question_box);

        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        String url = "https://10.0.2.2:8443/appAuth";

        RequestBody body = new FormBody.Builder()
                .add("param", "emailgeneral")
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
                JSONObject resp_string = null;
                String formatted = null;
                try {
                    resp_string = new JSONObject(response.body().string());
                    formatted = "1. "+resp_string.getString("1")+"\n2. "+resp_string.getString("2");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                question.setText(formatted);
            }
        });

    }
}