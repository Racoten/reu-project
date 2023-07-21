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


// NOTE: Don't change runOnUiThread calls to lambdas

// TODO: determine if countdown latch is really necessary
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

            for(int k = 0; k < general.size(); k++) {
                question_counter += 1;
                if(!Objects.equals(general.get(k).getAnswer(), "no")) {
                    getTargetedEmailRecommendations(String.valueOf(k+1), temp1, targeted1);

                }
            }


        } else if(Objects.equals(type, "browser")) {
            getGeneralBrowserRecommendations();

            for(int k = 0; k < general.size(); k++) {
                question_counter += 1;
                if(!Objects.equals(general.get(k).getAnswer(), "no")) {
                    getTargetedBrowserRecommendations(String.valueOf(k+1), temp1, targeted1);

                }
            }

        } else if(Objects.equals(type, "sms")) {
            getGeneralSMSRecommendations();

            for(int k = 0; k < general.size(); k++) {
                question_counter += 1;
                if(!Objects.equals(general.get(k).getAnswer(), "no")) {
                    getTargetedSMSRecommendations(String.valueOf(k+1), temp1, targeted1);

                }
            }
        } else if(Objects.equals(type, "wifi")) {
            getGeneralWifiRecommendations();

            for(int k = 0; k < general.size(); k++) {
                question_counter += 1;
                if(!Objects.equals(general.get(k).getAnswer(), "no")) {
                    getTargetedWifiRecommendations(String.valueOf(k+1), temp1, targeted1);
                }
            }
        }
        //setWeightAverage();
        //score.setText(String.valueOf(weight_average));

    }

    public void setWeightAverage() {
        weight_average = (double) ( weight_total / question_counter);
    }

    public ArrayList<Question> getQuestionTable(ArrayList<Question> all, Integer table) {
        ArrayList<Question> t = new ArrayList<>();
        for(int i = 0; i < all.size(); i++) {
            if(Objects.equals(all.get(i).getTable(), table)) {
                t.add(all.get(i));
            }
        }
        return t;
    }

    public void getGeneralEmailRecommendations() {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=emailgeneral";
        //String url = "https://192.168.0.32:8443/recommendationsHandler?param=emailgeneral";
        String url = "https://"+apiHandler.URL_STR+"/recommendationsHandler?param=emailgeneral";

        //CountDownLatch latch = new CountDownLatch(1);
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
                //latch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //JSONObject obj = null;
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray gen_rec = obj.getJSONArray("recommendations");
                    //System.out.println(gen_rec.toString());
                    for(int i = 0; i < gen_rec.length(); i++) {

                        if(!Objects.equals(general.get(i).getAnswer(), "no")) {
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
        String url = "https://"+apiHandler.URL_STR+"/recommendationsHandler?param=emailtargeted&targetedtable=" + tablename;
        Integer tblint = new Integer(tablename);

        //CountDownLatch latch = new CountDownLatch(1);
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
                //latch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //JSONObject obj = null;
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray t1_rec = obj.getJSONArray("recommendations");
                    //System.out.println(t1_rec);
                    // filter out questions from specific table
                    ArrayList<Question> tableq = getQuestionTable(comparelist, Integer.valueOf(tablename));
                    for (int i = 0; i < t1_rec.length(); i++) {
                        //System.out.println(comparelist.get(i).getQuestionText());
                        JSONObject ro = t1_rec.getJSONObject(i);

                        // fix if statement
                        if(!Objects.equals(tableq.get(i).getAnswer(), "no") && tableq.get(i).getId().toString().equals(ro.getString("RecommendationID"))) {
                            weight_total += tableq.get(i).getWeight();
                            rec_all.add(new Recommendation(ro.getString("RecommendationText")));

                            setWeightAverage();

                        }


                        question_counter += 1;
                    }
                    latch.countDown();

                    //System.out.println("Weight: "+weight_average+" Count: "+question_counter);

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
        String url = "https://"+apiHandler.URL_STR+"/recommendationsHandler?param=browsersecuritygeneral";

        CountDownLatch latch = new CountDownLatch(1);

        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Error fetching email general recommendations");
                //latch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //JSONObject obj = null;
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray gen_rec = obj.getJSONArray("recommendations");
                    //System.out.println(gen_rec.toString());
                    for(int i = 0; i < gen_rec.length(); i++) {

                        if(!Objects.equals(general.get(i).getAnswer(), "no")) {
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
        String url = "https://"+apiHandler.URL_STR+"/recommendationsHandler?param=browsersecuritytargeted&targetedtable=" + tablename;
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
                //latch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //JSONObject obj = null;
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray t1_rec = obj.getJSONArray("recommendations");
                    //System.out.println(t1_rec);
                    ArrayList<Question> tableq = getQuestionTable(comparelist, Integer.valueOf(tablename));

                    for (int i = 0; i < t1_rec.length(); i++) {
                        JSONObject ro = t1_rec.getJSONObject(i);

                        if(!Objects.equals(tableq.get(i).getAnswer(), "no") && tableq.get(i).getId().toString().equals(ro.getString("RecommendationID"))) {
                            weight_total += tableq.get(i).getWeight();
                            //question_counter += 1;
                            rec_all.add(new Recommendation(ro.getString("RecommendationText")));
                            setWeightAverage();

                        }
                        question_counter += 1;
                    }

                    latch.countDown();

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

    public void getGeneralSMSRecommendations() {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=emailgeneral";
        //String url = "https://192.168.0.32:8443/recommendationsHandler?param=emailgeneral";
        String url = "https://"+apiHandler.URL_STR+"/recommendationsHandler?param=smsgeneral";

        CountDownLatch latch = new CountDownLatch(1);

        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Error fetching email general recommendations");
                //latch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //JSONObject obj = null;
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray gen_rec = obj.getJSONArray("recommendations");
                    //System.out.println(gen_rec.toString());
                    for(int i = 0; i < gen_rec.length(); i++) {

                        if(!Objects.equals(general.get(i).getAnswer(), "no")) {
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

    public void getTargetedSMSRecommendations(String tablename, ArrayList<Recommendation> tempreclist, ArrayList<Question> comparelist) {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url1 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        //String url1 = "https://192.168.0.32:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        //String url2 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=2";
        String url = "https://"+apiHandler.URL_STR+"/recommendationsHandler?param=smstargeted&targetedtable=" + tablename;
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
                //latch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //JSONObject obj = null;
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray t1_rec = obj.getJSONArray("recommendations");
                    //System.out.println(t1_rec);
                    ArrayList<Question> tableq = getQuestionTable(comparelist, Integer.valueOf(tablename));

                    for (int i = 0; i < t1_rec.length(); i++) {
                        JSONObject ro = t1_rec.getJSONObject(i);

                        if(!Objects.equals(tableq.get(i).getAnswer(), "no") && tableq.get(i).getId().toString().equals(ro.getString("RecommendationID"))) {
                            weight_total += tableq.get(i).getWeight();
                            //question_counter += 1;
                            rec_all.add(new Recommendation(ro.getString("RecommendationText")));
                            setWeightAverage();

                        }
                        question_counter += 1;
                    }

                    latch.countDown();

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

    public void getGeneralWifiRecommendations() {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url = "https://10.0.2.2:8443/questionsHandler?param=emailgeneral";
        //String url = "https://192.168.0.32:8443/recommendationsHandler?param=emailgeneral";
        String url = "https://"+apiHandler.URL_STR+"/recommendationsHandler?param=wifigeneral";

        CountDownLatch latch = new CountDownLatch(1);

        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Error fetching email general recommendations");
                //latch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //JSONObject obj = null;
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray gen_rec = obj.getJSONArray("recommendations");
                    //System.out.println(gen_rec.toString());
                    for(int i = 0; i < gen_rec.length(); i++) {

                        if(!Objects.equals(general.get(i).getAnswer(), "no")) {
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

    public void getTargetedWifiRecommendations(String tablename, ArrayList<Recommendation> tempreclist, ArrayList<Question> comparelist) {
        OkHttpClient client = apiHandler.getUnsafeOkHttpClient();
        //String url1 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        //String url1 = "https://192.168.0.32:8443/recommendationsHandler?param=emailtargeted&targetedtable=1";
        //String url2 = "https://10.0.2.2:8443/recommendationsHandler?param=emailtargeted&targetedtable=2";
        String url = "https://"+apiHandler.URL_STR+"/recommendationsHandler?param=wifitargeted&targetedtable=" + tablename;
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
                //latch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //JSONObject obj = null;
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray t1_rec = obj.getJSONArray("recommendations");
                    //System.out.println(t1_rec);
                    ArrayList<Question> tableq = getQuestionTable(comparelist, Integer.valueOf(tablename));

                    for (int i = 0; i < t1_rec.length(); i++) {
                        JSONObject ro = t1_rec.getJSONObject(i);

                        if(!Objects.equals(tableq.get(i).getAnswer(), "no") && tableq.get(i).getId().toString().equals(ro.getString("RecommendationID"))) {
                            weight_total += tableq.get(i).getWeight();
                            //question_counter += 1;
                            rec_all.add(new Recommendation(ro.getString("RecommendationText")));
                            setWeightAverage();

                        }
                        question_counter += 1;
                    }

                    latch.countDown();

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