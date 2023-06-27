package com.example.cybersafe;

import static org.apache.http.conn.ssl.SSLSocketFactory.SSL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Objects;

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

public class BrowserSecurityQuestionnaire extends AppCompatActivity {
    ArrayList<Question> general_questions = new ArrayList<>();
    ArrayList<Question> targeted_1 = new ArrayList<>();
    ArrayList<Question> targeted_2 = new ArrayList<>();
    private RecyclerView question_box;

    private RecyclerView target1_box;
    private RecyclerView target2_box;

    private QuestionAdapter adapter;
    private QuestionAdapter targ1_adapter;
    private QuestionAdapter targ2_adapter;

    private Button next;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_security_questionnaire);
        question_box = findViewById(R.id.browser_question_general);
        target1_box = findViewById(R.id.browser_target_1);
        target2_box = findViewById(R.id.browser_target_2);

        next = findViewById(R.id.next);
        submit = findViewById(R.id.browser_submit);

        question_box.setLayoutManager(new LinearLayoutManager(this));
        target1_box.setLayoutManager(new LinearLayoutManager(this));
        target2_box.setLayoutManager(new LinearLayoutManager(this));

        adapter = new QuestionAdapter(this, general_questions);
        targ1_adapter = new QuestionAdapter(this, targeted_1);
        targ2_adapter = new QuestionAdapter(this, targeted_2);

        question_box.setAdapter(adapter);
        target1_box.setAdapter(targ1_adapter);
        target2_box.setAdapter(targ2_adapter);
        getBrowserGeneralQuestions();
    }

    public void checkQuestions(View view) {
        // note: general questions can't be removed without app crashing

        for(int i = 0; i < general_questions.size(); i++) {

            if(Objects.equals(general_questions.get(i).getAnswer(), "yes")) {
                Integer temp = i+1;
                String tbl = temp.toString();
                getTargetedQuestions(tbl);
            }
        }
        // remove next button
        next.setVisibility(View.INVISIBLE);
        question_box.setVisibility(View.INVISIBLE);
        // show submit button
        submit.setVisibility(View.VISIBLE);
        target1_box.setVisibility(View.VISIBLE);
        target2_box.setVisibility(View.VISIBLE);
    }

    public void getBrowserGeneralQuestions() {

        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=browsersecuritygeneral";
        String url = "https://192.168.0.32:8443/questionsHandler?param=browsersecuritygeneral";



        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();


        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //System.out.println(response.body().string());

                try {
                    JSONObject resp_string = new JSONObject(response.body().string());
                    JSONArray array = resp_string.getJSONArray("questions");
                    for(int i = 0; i < array.length(); i++) {
                        // default answer to no
                        general_questions.add(new Question(array.getString(i), "no"));
                        //System.out.println(general_questions.get(i).getQuestionText());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }

    public void getTargetedQuestions(String targetedtable) {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url1 = "https://10.0.2.2:8443/questionsHandler?param=emailtargeted&targetedtable=1";
        String url1 = "https://192.168.0.32:8443/questionsHandler?param=browsertargeted&targetedtable=" + targetedtable;



        Request req = new Request.Builder()
                .url(url1)
                .get()
                .build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //System.out.println(response.body().string());

                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray t = obj.getJSONArray("questions");
                    //targ1_answered = new Integer[t1.length()];
                    //targeted1 = new Question[t1.length()];
                    for (int i = 0; i < t.length(); i++) {
                        //targeted1[i] = new Question(t1.getString(i), "");

                        if(Objects.equals(targetedtable, "1")) {
                            targeted_1.add(new Question(t.getString(i), "no"));
                        } else if(Objects.equals(targetedtable, "2")) {
                            targeted_2.add(new Question(t.getString(i), "no"));
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            targ1_adapter.notifyDataSetChanged();
                            targ2_adapter.notifyDataSetChanged();
                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}