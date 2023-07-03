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
import android.widget.TextView;

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

/*
TODO: all recommendations do show up, however sometimes they aren't added to the recyclerview's arraylist fast enough
    Figure out how to block UI until all recommendations appear.
    When only 1 general question is selected, all recommendations show up in time, and count is updated correctly.
    However, when both general questions are selected, not all of them show up.

 */
public class RecommendationsActivity extends AppCompatActivity {
    private ArrayList<Question> targeted1 = new ArrayList<>();


    private ArrayList<Question> general = new ArrayList<>();
    private ArrayList<Recommendation> rec_all = new ArrayList<>();
    private ArrayList<Recommendation> temp1 = new ArrayList<>();
    private ArrayList<Recommendation> temp2 = new ArrayList<>();
    private RecyclerView recs;
    private TextView score;
    private double weight_total = 0;
    private double question_counter = 0;
    private Double weight_average = 0.0;
    private RecommendationAdapter rec_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
        Intent i = getIntent();
        general = i.getParcelableArrayListExtra("general");
        targeted1 = i.getParcelableArrayListExtra("targeted1");
        //targeted2 = i.getParcelableArrayListExtra("targeted2");
        String type = i.getStringExtra("question_type");
        //weight_total = i.getIntExtra("weight_total", 0);
        //question_counter = i.getIntExtra("question_counter", 0);

        recs = findViewById(R.id.recommendation_view);
        score = findViewById(R.id.score);
        recs.setLayoutManager(new LinearLayoutManager(this));
        rec_adapter = new RecommendationAdapter(this, rec_all);

        recs.setAdapter(rec_adapter);
        //rec_adapter.notifyDataSetChanged();
        //question_counter = targeted1.size();
        if(Objects.equals(type, "email")) {
            getGeneralEmailRecommendations();


            if (Objects.equals(general.get(0).getAnswer(), "yes")) {
                question_counter += 1;
                getTargetedEmailRecommendations("1", temp1, targeted1);
            }

            if (Objects.equals(general.get(1).getAnswer(), "yes")) {
                question_counter += 1;
                getTargetedEmailRecommendations("2", temp2, targeted1);
            }
        } else if(Objects.equals(type, "browser")) {
            getGeneralBrowserRecommendations();

            if(Objects.equals(general.get(0).getAnswer(), "yes")) {
                question_counter += 1;

                getTargetedBrowserRecommendations("1", temp1, targeted1);
            }
            if(Objects.equals(general.get(1).getAnswer(), "yes")) {
                question_counter += 1;
                getTargetedBrowserRecommendations("2", temp2, targeted1);
            }
        }
        //setWeightAverage();
        //score.setText(String.valueOf(weight_average));

    }

    public void setWeightAverage() {
        weight_average = (double) ( weight_total / question_counter);
    }

    public void getGeneralEmailRecommendations() {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=emailgeneral";
        //String url = "https://192.168.0.32:8443/recommendationsHandler?param=emailgeneral";
        String url = "https://192.168.0.32:8443/recommendationsHandler?param=emailgeneral";

        CountDownLatch latch = new CountDownLatch(1);

        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Error fetching email general recommendations");
                e.printStackTrace();
                latch.countDown();
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
                            JSONObject ro = gen_rec.getJSONObject(i);
                            //question_counter += 1;
                            weight_total += general.get(i).getWeight();
                            //System.out.println(gen_rec.getString(i));
                            rec_all.add(new Recommendation(ro.getString("RecommendationText")));
                            setWeightAverage();
                        }

                    }
                    //System.out.println("Weight: "+weight_average+" Count: "+question_counter);
                    latch.countDown();
                    /*
                    runOnUiThread(() -> {
                        score.setText(String.format("%.1f", weight_average));
                        rec_adapter.notifyDataSetChanged();
                    });

                     */
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
        score.setText(String.format("%.1f", weight_average));
        rec_adapter.notifyDataSetChanged();

    }

    public void getTargetedEmailRecommendations(String tablename, ArrayList<Recommendation> tempreclist, ArrayList<Question> comparelist) {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=" + tablename;
        String url = "https://192.168.0.32:8443/recommendationsHandler?param=emailtargeted&targetedtable=" + tablename;
        Integer tblint = new Integer(tablename);

        CountDownLatch latch = new CountDownLatch(1);


        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Error fetching email general recommendations");
                e.printStackTrace();
                latch.countDown();
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
                            JSONObject ro = t1_rec.getJSONObject(i);
                            weight_total += comparelist.get(i).getWeight();

                            rec_all.add(new Recommendation(ro.getString("RecommendationText")));
                            setWeightAverage();

                        }
                        question_counter += 1;
                    }
                    latch.countDown();

                    //System.out.println("Weight: "+weight_average+" Count: "+question_counter);
                    /*
                    runOnUiThread(() -> {
                        //rec_all.addAll(tempreclist);
                        //score.setText(String.valueOf(weight_average));
                        score.setText(String.format("%.1f", weight_average));
                        rec_adapter.notifyDataSetChanged();
                    });

                     */
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(obj);
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        score.setText(String.format("%.1f", weight_average));
        rec_adapter.notifyDataSetChanged();

    }

    public void getGeneralBrowserRecommendations() {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=emailgeneral";
        //String url = "https://192.168.0.32:8443/recommendationsHandler?param=emailgeneral";
        String url = "https://192.168.0.32:8443/recommendationsHandler?param=browsersecuritygeneral";

        CountDownLatch latch = new CountDownLatch(1);

        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Error fetching email general recommendations");
                latch.countDown();
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
                            JSONObject ro  = gen_rec.getJSONObject(i);

                            question_counter += 1;
                            weight_total += general.get(i).getWeight();
                            //System.out.println(gen_rec.getString(i));
                            rec_all.add(new Recommendation(ro.getString("RecommendationText")));
                            setWeightAverage();
                        }

                    }

                    latch.countDown();
                    //System.out.println("Weight: "+weight_average+" Count: "+question_counter);
                    /*
                    runOnUiThread(() -> {
                        score.setText(String.format("%.1f", weight_average));

                        rec_adapter.notifyDataSetChanged();
                    });

                     */
                    //System.out.println(gen_rec);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(obj);
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        score.setText(String.format("%.1f", weight_average));
        rec_adapter.notifyDataSetChanged();

    }

    public void getTargetedBrowserRecommendations(String tablename, ArrayList<Recommendation> tempreclist, ArrayList<Question> comparelist) {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url1 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        //String url1 = "https://192.168.0.32:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        //String url2 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=2";
        String url = "https://192.168.0.32:8443/recommendationsHandler?param=browsersecuritytargeted&targetedtable=" + tablename;
        Integer tblint = new Integer(tablename);

        CountDownLatch latch = new CountDownLatch(1);

        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Error fetching email general recommendations");
                latch.countDown();
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
                            JSONObject ro = t1_rec.getJSONObject(i);
                            weight_total += comparelist.get(i).getWeight();
                            //question_counter += 1;
                            rec_all.add(new Recommendation(ro.getString("RecommendationText")));
                            setWeightAverage();

                        }
                        question_counter += 1;
                    }

                    latch.countDown();
                    //System.out.println("Weight: "+weight_average+" Count: "+question_counter);
                    //System.out.println("Weight: "+weight_total+" Count: "+question_counter);
                    /*
                    runOnUiThread(() -> {
                        //rec_all.addAll(tempreclist);
                        score.setText(String.format("%.1f", weight_average));
                        rec_adapter.notifyDataSetChanged();
                    });

                     */
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(obj);
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        score.setText(String.format("%.1f", weight_average));
        rec_adapter.notifyDataSetChanged();
    }

}