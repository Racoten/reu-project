package com.example.cybersafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class EmailQuestionnaire extends AppCompatActivity {
    ArrayList<Question> general_questions = new ArrayList<>();
    ArrayList<Question> targeted_1 = new ArrayList<>();
    ArrayList<Question> targeted_2 = new ArrayList<>();
    private RecyclerView box;
    // 2 different adapter views are used
    // switching the answer text requires them to be apart of the adapter/holder method system
    private RecyclerView target_box;
    private RecyclerView target2;
    private QuestionAdapter adapter;
    private QuestionAdapter targ_adapter;
    private QuestionAdapter targ2_adapter;
    private RadioButton yes_btn;
    private RadioButton no_btn;
    private Button next;
    private Button submit;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_questionnaire);


        box = findViewById(R.id.q_box);
        target_box = findViewById(R.id.target_box);

        next = findViewById(R.id.next_question);
        submit = findViewById(R.id.submit_btn);

        box.setLayoutManager(new LinearLayoutManager(this));
        target_box.setLayoutManager(new LinearLayoutManager(this));

        adapter = new QuestionAdapter(this, general_questions);
        targ_adapter = new QuestionAdapter(this, targeted_1);

        box.setAdapter(adapter);
        box.setVisibility(View.VISIBLE);
        target_box.setAdapter(targ_adapter);
        getEmailQuestions();

        // notifyDataSetChanged has to run inside of ui thread runnable, inside getEmailQuestions()
        //adapter.notifyDataSetChanged();
    }

    public boolean questionExists(String text, ArrayList<Question> list) {
        for(int i = 0; i < list.size(); i++) {
            if(Objects.equals(list.get(i).getQuestionText(), text)) {
                return  true;
            }
        }
        return false;
    }

    public void checkQuestions(View view) {
        // note: general questions can't be removed without app crashing

        for(int i = 0; i < general_questions.size(); i++) {

            if(general_questions.get(i).getAnswer() == "yes") {
                Integer temp = i+1;
                String tbl = temp.toString();
                getTargetedQuestions(tbl);
            }
        }
        // remove next button
        next.setVisibility(View.INVISIBLE);
        box.setVisibility(View.INVISIBLE);
        // show submit button
        submit.setVisibility(View.VISIBLE);
        target_box.setVisibility(View.VISIBLE);
    }

    public void startRecommendations(View view) {

        //System.out.println(targeted_all.size());

        Intent rec = new Intent(this, RecommendationsActivity.class);
        rec.putParcelableArrayListExtra("targeted1", targeted_1);
        //rec.putParcelableArrayListExtra("targeted2", targeted_2);
        rec.putParcelableArrayListExtra("general", general_questions);
        rec.putExtra("question_type", "email");

        startActivity(rec);
    }

    public void getEmailQuestions() {

        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=emailgeneral";
        String url = "https://192.168.0.32:8443/questionsHandler?param=emailgeneral";


        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();

        // Have to use enqueue and callback, synchronous way (execute) causes a network error

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
                    for(int i = 0; i < gq.length(); i++) {
                        // default answer to no
                        JSONObject q = gq.getJSONObject(i);
                        // zero means general questions
                        general_questions.add(new Question(q.getString("question_text"), "no", q.getInt("weight"), q.getInt("id"), 0));
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
        String url1 = "https://192.168.0.32:8443/questionsHandler?param=emailtargeted&targetedtable=" + targetedtable;



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
                        JSONObject q = t.getJSONObject(i);
                        // TODO: delete if statements when not needed
                       if(Objects.equals(targetedtable, "1")) {
                           if(!questionExists(q.getString("question_text"),  targeted_1)) {
                               targeted_1.add(new Question(q.getString("question_text"), "no", q.getInt("weight"), q.getInt("id"), Integer.valueOf(targetedtable)));
                           }
                           //targeted_1.add(new Question(t.getString(i), "no"));
                       } else if(Objects.equals(targetedtable, "2")) {
                           if(!questionExists(q.getString("question_text"),  targeted_1)) {
                               targeted_1.add(new Question(q.getString("question_text"), "no", q.getInt("weight"), q.getInt("id"), Integer.valueOf(targetedtable)));
                           }
                           //targeted_2.add(new Question(t.getString(i), "no"));
                       }
                    }

                   runOnUiThread(new Runnable() {
                       @Override
                        public void run() {
                            targ_adapter.notifyDataSetChanged();
                            //targ2_adapter.notifyDataSetChanged();
                       }
                   });


               } catch (JSONException e) {
                    throw new RuntimeException(e);
              }
          }
        });
    }
}