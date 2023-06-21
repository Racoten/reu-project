package com.example.cybersafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("here");
        Button login_btn = findViewById(R.id.login_page_button);
        /*
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText username = (EditText) findViewById(R.id.username_input);
                //EditText password = (EditText) findViewById(R.id.password_input);

                try {
                    URL url = new URL("http://localhost:8080/appAuth");
                    String params = "param=login&username=test&password=test";

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");

                    conn.setDoOutput(true);

                    //conn.connect();
                    OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                    out.write(params.getBytes());
                    out.flush();
                    out.close();
                    conn.connect();


                    BufferedReader b = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while((line = b.readLine()) != null) {
                        System.out.println(line);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

         */
    }

    public void doLogin(View view) {

        //EditText username = (EditText) findViewById(R.id.username_input);
        //EditText password = (EditText) findViewById(R.id.password_input);
        try {
            /*
            Map<String, String> params = new HashMap<>();
            params.put("param", "login");
            params.put("username", username.getText().toString());
            params.put("password", password.getText().toString());

             */
            //String param_string = "param=login&username="+username.getText().toString()+"&password="+password.getText().toString();
            String param_string = "param=login&username=test&password=test";
            URL loginurl = new URL("http://localhost:8080/appAuth");

            HttpURLConnection conn = (HttpURLConnection) loginurl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            DataOutputStream outstream = new DataOutputStream(conn.getOutputStream());
            outstream.writeBytes(param_string);
            outstream.flush();
            outstream.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}