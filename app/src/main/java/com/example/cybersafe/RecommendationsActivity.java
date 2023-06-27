package com.example.cybersafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecommendationsActivity extends AppCompatActivity {
    private ArrayList<Question> targeted = new ArrayList<>();
    private ArrayList<Question> general = new ArrayList<>();
    JSONArray gen_rec;
    JSONArray t1_rec;
    JSONArray t2_rec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
        Intent i = getIntent();
        general = i.getParcelableArrayListExtra("general");
        targeted = i.getParcelableArrayListExtra("targeted");

        for(int k = 0; k < general.size(); k++) {
            System.out.println(general.get(k).getQuestionText());
        }

        for(int k = 0; k < targeted.size(); k++) {
            System.out.println(targeted.get(k).getQuestionText());
        }

        /*
        targeted1 = (Question[]) i.getSerializableExtra("targeted_questions_1");
        targeted2 = (Question[]) i.getSerializableExtra("targeted_questions_2");

        System.out.println(targeted1[0].getQuestionText());

         */
        getGeneralEmailRecommendations();
        getTargetedEmailRecommendations();

        // TODO: display targeted recommendations, show only for questions answered yes
        // check question answer text, Question arraylists will be passed
        //displayRecommendations();
    }

    public void getGeneralEmailRecommendations() {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=emailgeneral";
        String url = "https://192.168.0.32:8443/recommendationsHandler?param=emailgeneral";


        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Error fetching email general recommendations");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //JSONObject obj = null;
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    gen_rec = obj.getJSONArray("recommendations");
                    //System.out.println(gen_rec);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(obj);
            }
        });

    }

    public void getTargetedEmailRecommendations() {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url1 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        String url1 = "https://192.168.0.32:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        //String url2 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=2";
        String url2 = "https://192.168.0.32:8443/recommendationsHandler?param=emailtargeted&targetedtable=2";

        Request req = new Request.Builder()
                .url(url1)
                .get()
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Error fetching email general recommendations");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //JSONObject obj = null;
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    t1_rec = obj.getJSONArray("recommendations");
                    //System.out.println(t1_rec);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(obj);
            }
        });

        req = new Request.Builder()
                .url(url2)
                .get()
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //JSONObject ob = null;
                try {
                    JSONObject ob = new JSONObject(response.body().string());
                    t2_rec = ob.getJSONArray("recommendations");
                    //System.out.println(t2_rec);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}