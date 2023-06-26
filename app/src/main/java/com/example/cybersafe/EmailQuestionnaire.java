package com.example.cybersafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EmailQuestionnaire extends AppCompatActivity {

    String current_type = "general";
    Integer general_number = 0;
    Integer targeted1_number = 0;
    Integer targeted2_number = 0;
    Integer targeted_table = 1;

    //JSONArray general_questions;
    Question[] general_questions;
    Question[] targeted1;
    Question[] targeted2;
    TextView qbox;
    RadioButton yes;
    RadioButton no;
    Button next;
    Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_questionnaire);
        /*
        qbox = findViewById(R.id.question_box);
        yes = findViewById(R.id.yes_btn);
        no = findViewById(R.id.no_btn);

         */
        next = findViewById(R.id.next_question);
        submit = findViewById(R.id.submit);
        submit.setVisibility(View.GONE);
        getEmailQuestions();
        getTargetedEmailQuestions();
        //showQuestions();
    }

    public void showGeneralQuestions(Question[] gq) {
        ArrayList<String> qs = new ArrayList<String>();
        qs.add(gq[0].getQuestionText());
        qs.add(gq[1].getQuestionText());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, qs);
        ListView ll = findViewById(R.id.question_view);
        ll.setAdapter(adapter);
        ll.setMinimumHeight(80);
        ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView chked = (CheckedTextView) view;
                chked.setChecked(!chked.isChecked());
                // record which question was checked
                if(general_questions[i].getAnswer() == "no" || general_questions[i].getAnswer() == "") {
                    general_questions[i].setAnswer("yes");
                } else {
                    general_questions[i].setAnswer("no");
                }
                //System.out.println(general_questions[i].getAnswer());
            }
        });

    }

    public void checkGeneralAnswer(View view) throws JSONException {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                next.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            }
        });

        ArrayList<String> all_targeted = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, all_targeted);
        ListView tg = findViewById(R.id.target_view);
        boolean t1_used = false;
        boolean t2_used = false;

        if(general_questions[0].getAnswer() == "yes") {
            t1_used = true;
            for(int i = 0; i < targeted1.length; i++) {
                all_targeted.add(targeted1[i].getQuestionText());

            }
        }
        if(general_questions[1].getAnswer() == "yes") {
            t2_used = true;
            for(int i = 0; i < targeted2.length; i++) {
                all_targeted.add(targeted2[i].getQuestionText());
            }
        }

        tg.setAdapter(adapter);

        boolean finalT1_used = t1_used;
        boolean finalT2_used = t2_used;
        tg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView chked = (CheckedTextView) view;
                chked.setChecked(!chked.isChecked());

                // get question text
                String current = all_targeted.get(i);
                //System.out.println(findQuestion(i, all_targeted));
                // check in targeted1 if being used
                if(finalT1_used) {
                    Question found = findQuestion(current, targeted1);
                    //System.out.println(found.getQuestionText());
                    if(chked.isChecked()) {
                        found.setAnswer("yes");
                    } else {
                        found.setAnswer("no");
                    }
                    //System.out.println(found.getAnswer());
                } else if(finalT2_used) {
                    Question found = findQuestion(current, targeted2);
                    //System.out.println(found.getQuestionText());
                    if(chked.isChecked()) {
                        found.setAnswer("yes");
                    } else {
                        found.setAnswer("no");
                    }
                    //System.out.println(found.getAnswer());
                }
            }
        });

    }

    public void startRecommendationAcitivity(View view) {
        Intent rec = new Intent(this, RecommendationsActivity.class);
        rec.putExtra("targeted_questions_1", targeted1);
        rec.putExtra("targeted_questions_2", targeted2);
        rec.putExtra("general_questions", general_questions);
        startActivity(rec);
    }

    private Question findQuestion(String ques, Question[] q_arr) {
        for(int i = 0; i < q_arr.length; i++) {
            if(q_arr[i].getQuestionText() == ques) {
                return q_arr[i];
            }
        }
        return null;
    }

    public void getEmailQuestions() {

        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=emailgeneral";
        String url = "https://192.168.0.32:8443/questionsHandler?param=emailgeneral";


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
                    JSONObject obj = new JSONObject(response.body().string());
                    //System.out.println(obj);
                    JSONArray gq = obj.getJSONArray("questions");
                    //qbox.setText(general_questions.getString(0));
                    //gen_answered = new Integer[gq.length()];
                    general_questions = new Question[gq.length()];
                    for(int i = 0; i < gq.length(); i++) {
                        general_questions[i] = new Question(gq.getString(i), "");
                    }
                    //startGeneralQuestions(general_questions);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showGeneralQuestions(general_questions);

                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void getTargetedEmailQuestions() {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url1 = "https://10.0.2.2:8443/questionsHandler?param=emailtargeted&targetedtable=1";
        String url1 = "https://192.168.0.32:8443/questionsHandler?param=emailtargeted&targetedtable=1";
        //String url2 = "https://10.0.2.2:8443/questionsHandler?param=emailtargeted&targetedtable=2";
        String url2 = "https://192.168.0.32:8443/questionsHandler?param=emailtargeted&targetedtable=2";



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
                    JSONArray t1 = obj.getJSONArray("questions");
                    //targ1_answered = new Integer[t1.length()];
                    targeted1 = new Question[t1.length()];
                    for(int i = 0; i < t1.length(); i++) {
                        targeted1[i] = new Question(t1.getString(i), "");
                    }
                    //System.out.println(targeted1.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        req = new Request.Builder()
                .url(url2)
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
                    JSONArray t2 = obj.getJSONArray("questions");
                    //targ2_answered = new Integer[t2.length()];
                    targeted2 = new Question[t2.length()];
                    for(int i = 0; i < t2.length(); i++) {
                        targeted2[i] = new Question(t2.getString(i), "");
                    }
                    //System.out.println(targeted2.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}