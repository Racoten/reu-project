package com.example.cybersafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EmailQuestionnaire extends AppCompatActivity {

    //JSONArray general_questions;
    // TODO: change name to all_questions to avoid confusion, keep as arraylist hooked up to adapter
    ArrayList<Question> general_questions = new ArrayList<>();
    ArrayList<Question> targeted_all = new ArrayList<>();
    Question[] targeted1;
    Question[] targeted2;
    private RecyclerView box;
    private RecyclerView target_box;
    private QuestionAdapter adapter;
    private QuestionAdapter targ_adapter;
    private RadioButton yes_btn;
    private RadioButton no_btn;
    private Button next;
    private Button submit;

    // have array list of all questions being display (targeted and general), which can be modified with questions in general and targeted lists


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
        targ_adapter = new QuestionAdapter(this, targeted_all);

        box.setAdapter(adapter);
        target_box.setAdapter(targ_adapter);
        getEmailQuestions();

        // notifyDataSetChanged has to run inside of ui thread runnable, inside getEmailQuestions()
        //adapter.notifyDataSetChanged();
    }

    public void checkQuestions(View view) {
        // note: general questions can't be removed without app crashing

        for(int i = 0; i < general_questions.size(); i++) {
            //System.out.println(general_questions.get(i).getQuestionText()+" "+general_questions.get(i).getAnswer());

            //yes_btn = box.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.yes_btn);
            //no_btn = box.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.no_btn);

            if(general_questions.get(i).getAnswer() == "yes") {
                if(i == 0) {
                    // targeted 1
                    getTargeted1Questions();
                } else if(i == 1) {
                    // targeted 2
                    getTargeted2Questions();
                }
            }
        }
        // remove next button
        next.setVisibility(View.INVISIBLE);
        // show submit button
        submit.setVisibility(View.VISIBLE);
    }

    public void startRecommendations(View view) {

        //System.out.println(targeted_all.size());

        Intent rec = new Intent(this, RecommendationsActivity.class);
        rec.putParcelableArrayListExtra("targeted", targeted_all);
        rec.putParcelableArrayListExtra("general", general_questions);
        startActivity(rec);
    }



    /*
    Get and display questions here (helper methods ok, pass individual strings)
    Because async network call required, reading general_questions outside of getEmailQuestions causes nullpointerexception
    due to task of reading Question[] general_questions not being finished
     */
    public void getEmailQuestions() {
        JSONObject resp_obj = null;
        JSONArray array = null;

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
                        general_questions.add(new Question(gq.getString(i), "no"));
                        //System.out.println(general_questions.get(i).getQuestionText());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                    //startGeneralQuestions(general_questions);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


    public void getTargeted1Questions() {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url1 = "https://10.0.2.2:8443/questionsHandler?param=emailtargeted&targetedtable=1";
        String url1 = "https://192.168.0.32:8443/questionsHandler?param=emailtargeted&targetedtable=1";



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
                    //targeted1 = new Question[t1.length()];
                    for (int i = 0; i < t1.length(); i++) {
                        //targeted1[i] = new Question(t1.getString(i), "");
                        targeted_all.add(new Question(t1.getString(i), "no"));
                    }
                    // adapter dataset is done after the second targeted list is loaded
                    //System.out.println(targeted1.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            targ_adapter.notifyDataSetChanged();
                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void getTargeted2Questions() {
        //String url2 = "https://10.0.2.2:8443/questionsHandler?param=emailtargeted&targetedtable=2";
        String url2 = "https://192.168.0.32:8443/questionsHandler?param=emailtargeted&targetedtable=2";

        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();

        Request req = new Request.Builder()
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
                    //targeted2 = new Question[t2.length()];
                    for(int i = 0; i < t2.length(); i++) {
                        //targeted2[i] = new Question(t2.getString(i), "");
                        targeted_all.add(new Question(t2.getString(i), "no"));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            targ_adapter.notifyDataSetChanged();
                        }
                    });

                    //System.out.println(targeted2.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // public void getTargetedQuestions(String targetedtable) {
    //     OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
    //     //String url1 = "https://10.0.2.2:8443/questionsHandler?param=emailtargeted&targetedtable=1";
    //     String url1 = "https://192.168.0.32:8443/questionsHandler?param=emailtargeted&targetedtable=" + targetedtable;



    //     Request req = new Request.Builder()
    //             .url(url1)
    //             .get()
    //             .build();
    //     client.newCall(req).enqueue(new Callback() {
    //         @Override
    //         public void onFailure(@NonNull Call call, @NonNull IOException e) {
    //             e.printStackTrace();

    //         }

    //         @Override
    //         public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
    //             //System.out.println(response.body().string());

    //             try {
    //                 JSONObject obj = new JSONObject(response.body().string());
    //                 JSONArray t = obj.getJSONArray("questions");
    //                 //targ1_answered = new Integer[t1.length()];
    //                 //targeted1 = new Question[t1.length()];
    //                 for (int i = 0; i < t.length(); i++) {
    //                     //targeted1[i] = new Question(t1.getString(i), "");
    //                     targeted_all.add(new Question(t.getString(i), "no"));
    //                 }
    //                 // adapter dataset is done after the second targeted list is loaded
    //                 //System.out.println(targeted1.toString());

    //                 runOnUiThread(new Runnable() {
    //                     @Override
    //                     public void run() {
    //                         targ_adapter.notifyDataSetChanged();
    //                     }
    //                 });


    //             } catch (JSONException e) {
    //                 throw new RuntimeException(e);
    //             }
    //         }
    //     });
    // }
}