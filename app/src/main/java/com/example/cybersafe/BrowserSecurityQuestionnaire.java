package com.example.cybersafe;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BrowserSecurityQuestionnaire extends AppCompatActivity {
    ArrayList<Question> general_questions = new ArrayList<>();
    ArrayList<Question> targeted_questions = new ArrayList<>();
    private RecyclerView question_box;

    private RecyclerView target1_box;

    private QuestionAdapter adapter;
    private QuestionAdapter targ1_adapter;
    //private Integer weight_total;
    //private Integer question_counter;

    private Button next;
    private Button submit;

    String ip_address = "";
    apiHandler api = new apiHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_security_questionnaire);

        Intent nt = getIntent();
        ip_address = nt.getStringExtra("ip");
        if(ip_address.isEmpty()) {
            ip_address = api.getIP();
        }


        question_box = findViewById(R.id.browser_question_general);
        target1_box = findViewById(R.id.browser_target_1);

        next = findViewById(R.id.next);
        submit = findViewById(R.id.browser_submit);

        question_box.setLayoutManager(new LinearLayoutManager(this));
        target1_box.setLayoutManager(new LinearLayoutManager(this));

        adapter = new QuestionAdapter(this, general_questions);
        targ1_adapter = new QuestionAdapter(this, targeted_questions);

        question_box.setAdapter(adapter);
        target1_box.setAdapter(targ1_adapter);
        getBrowserGeneralQuestions();
    }

    public void startRecommendations(View view) {

        //System.out.println(targeted_all.size());

        Intent rec = new Intent(this, RecommendationsActivity.class);
        rec.putParcelableArrayListExtra("targeted1", targeted_questions);
        rec.putParcelableArrayListExtra("general", general_questions);
        rec.putExtra("question_type", "browser");
        //rec.putExtra("weight_total", weight_total);
        //rec.putExtra("question_counter", question_counter);
        rec.putExtra("ip", ip_address);
        startActivity(rec);
    }

    public void checkQuestions(View view) {
        // note: general questions can't be removed without app crashing

        for(int i = 0; i < general_questions.size(); i++) {

            if(!Objects.equals(general_questions.get(i).getAnswer(), "no")) {
                //weight_total += general_questions.get(i).getWeight();
                //question_counter += 1;
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

    }

    public void getBrowserGeneralQuestions() {
        apiHandler api = new apiHandler();
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=browsersecuritygeneral";
        String url = "https://"+ip_address+":8443/questionsHandler?param=browsersecuritygeneral";

        CountDownLatch latch = new CountDownLatch(1);


        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();


        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                latch.countDown();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //System.out.println(response.body().string());

                try {
                    JSONObject resp_string = new JSONObject(response.body().string());
                    JSONArray array = resp_string.getJSONArray("questions");
                    for(int i = 0; i < array.length(); i++) {
                        // default answer to no
                        JSONObject q = array.getJSONObject(i);
                        // zero means general questions
                        general_questions.add(new Question(q.getString("question_text"), "no", q.getInt("weight"), q.getInt("id"), 0));

                        //System.out.println(general_questions.get(i).getQuestionText());
                    }
                    latch.countDown();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        adapter.notifyDataSetChanged();

    }

    public boolean questionExists(String text, ArrayList<Question> list) {
        for(int i = 0; i < list.size(); i++) {
            if(Objects.equals(list.get(i).getQuestionText(), text)) {
                return  true;
            }
        }
        return false;
    }

    public void getTargetedQuestions(String targetedtable) {
        apiHandler api = new apiHandler();
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url1 = "https://10.0.2.2:8443/questionsHandler?param=emailtargeted&targetedtable=1";
        String url1 = "https://"+ip_address+":8443/questionsHandler?param=browsertargeted&targetedtable=" + targetedtable;

        CountDownLatch latch = new CountDownLatch(1);


        Request req = new Request.Builder()
                .url(url1)
                .get()
                .build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                latch.countDown();

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
                        JSONObject q = t.getJSONObject(i);
                        if(!questionExists(q.getString("question_text"), targeted_questions)) {
                            targeted_questions.add(new Question(q.getString("question_text"), "no", q.getInt("weight"), q.getInt("id"), Integer.valueOf(targetedtable)));
                        }

                    }
                    latch.countDown();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        targ1_adapter.notifyDataSetChanged();
    }
}