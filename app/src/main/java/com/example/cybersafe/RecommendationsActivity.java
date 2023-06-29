package com.example.cybersafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// TODO: all recommendations do show up, however sometimes they aren't added to the recyclerview's arraylist fast enough
public class RecommendationsActivity extends AppCompatActivity {
    private ArrayList<Question> targeted1 = new ArrayList<>();
    private ArrayList<Question> targeted2 = new ArrayList<>();


    private ArrayList<Question> general = new ArrayList<>();
    private ArrayList<Recommendation> rec_all = new ArrayList<>();
    private ArrayList<Recommendation> temp1 = new ArrayList<>();
    private ArrayList<Recommendation> temp2 = new ArrayList<>();
    private RecyclerView recs;
    private RecommendationAdapter rec_adapter;
    private boolean showGeneral1 = false;
    private boolean showGeneral2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
        Intent i = getIntent();
        general = i.getParcelableArrayListExtra("general");
        targeted1 = i.getParcelableArrayListExtra("targeted1");
        //targeted2 = i.getParcelableArrayListExtra("targeted2");
        String type = i.getStringExtra("question_type");

        recs = findViewById(R.id.recommendation_view);
        recs.setLayoutManager(new LinearLayoutManager(this));
        rec_adapter = new RecommendationAdapter(this, rec_all);

        recs.setAdapter(rec_adapter);
        //rec_adapter.notifyDataSetChanged();

        if(Objects.equals(type, "email")) {
            getGeneralEmailRecommendations();


            if (Objects.equals(general.get(0).getAnswer(), "yes")) {
                getTargetedEmailRecommendations("1", temp1, targeted1);
            }

            if (Objects.equals(general.get(1).getAnswer(), "yes")) {
                getTargetedEmailRecommendations("2", temp2, targeted1);
            }
        } else if(Objects.equals(type, "browser")) {
            getGeneralBrowserRecommendations();

            if(Objects.equals(general.get(0).getAnswer(), "yes")) {
                getTargetedBrowserRecommendations("1", temp1, targeted1);
            }
            if(Objects.equals(general.get(1).getAnswer(), "yes")) {
                getTargetedBrowserRecommendations("2", temp2, targeted1);
            }
        }

    }

    public void getGeneralEmailRecommendations() {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=emailgeneral";
        //String url = "https://192.168.0.32:8443/recommendationsHandler?param=emailgeneral";
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
                    JSONArray gen_rec = obj.getJSONArray("recommendations");
                    //System.out.println(gen_rec.toString());
                    for(int i = 0; i < gen_rec.length(); i++) {

                        if(Objects.equals(general.get(i).getAnswer(), "yes")) {
                            //System.out.println(gen_rec.getString(i));
                            rec_all.add(new Recommendation(gen_rec.getString(i)));
                        }



                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rec_adapter.notifyDataSetChanged();
                        }
                    });
                    //System.out.println(gen_rec);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(obj);
            }
        });

    }

    public void getTargetedEmailRecommendations(String tablename, ArrayList<Recommendation> tempreclist, ArrayList<Question> comparelist) {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url1 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        //String url1 = "https://192.168.0.32:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        //String url2 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=2";
        String url = "https://192.168.0.32:8443/recommendationsHandler?param=emailtargeted&targetedtable=" + tablename;
        Integer tblint = new Integer(tablename);

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
                    JSONArray t1_rec = obj.getJSONArray("recommendations");
                    //System.out.println(t1_rec);
                    for (int i = 0; i < t1_rec.length(); i++) {
                        if(Objects.equals(comparelist.get(i).getAnswer(), "yes") && Objects.equals(comparelist.get(i).getTable(), Integer.valueOf(tablename))) {
                            rec_all.add(new Recommendation(t1_rec.getString(i)));

                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //rec_all.addAll(tempreclist);
                            rec_adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(obj);
            }
        });

    }

    public void getGeneralBrowserRecommendations() {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=emailgeneral";
        //String url = "https://192.168.0.32:8443/recommendationsHandler?param=emailgeneral";
        String url = "https://192.168.0.32:8443/recommendationsHandler?param=browsersecuritygeneral";

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
                    JSONArray gen_rec = obj.getJSONArray("recommendations");
                    //System.out.println(gen_rec.toString());
                    for(int i = 0; i < gen_rec.length(); i++) {

                        if(Objects.equals(general.get(i).getAnswer(), "yes")) {
                            //System.out.println(gen_rec.getString(i));
                            rec_all.add(new Recommendation(gen_rec.getString(i)));
                        }



                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rec_adapter.notifyDataSetChanged();
                        }
                    });
                    //System.out.println(gen_rec);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(obj);
            }
        });

    }

    public void getTargetedBrowserRecommendations(String tablename, ArrayList<Recommendation> tempreclist, ArrayList<Question> comparelist) {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url1 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        //String url1 = "https://192.168.0.32:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        //String url2 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=2";
        String url = "https://192.168.0.32:8443/recommendationsHandler?param=browsersecuritytargeted&targetedtable=" + tablename;
        Integer tblint = new Integer(tablename);

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
                    JSONArray t1_rec = obj.getJSONArray("recommendations");
                    //System.out.println(t1_rec);
                    for (int i = 0; i < t1_rec.length(); i++) {
                        if(Objects.equals(comparelist.get(i).getAnswer(), "yes")) {
                            rec_all.add(new Recommendation(t1_rec.getString(i)));

                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //rec_all.addAll(tempreclist);
                            rec_adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(obj);
            }
        });
    }

}