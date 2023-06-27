package com.example.cybersafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.RecommendationHolder> {
    private Context context;

    private ArrayList<Recommendation> recommendations;

    public RecommendationAdapter(Context con, ArrayList<Recommendation> recs) {
        this.context = con;
        this.recommendations = recs;
    }

    @NonNull
    @Override
    public RecommendationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recommendation_items, parent, false);
        return new RecommendationHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationHolder holder, int position) {
        Recommendation r = recommendations.get(position);
        holder.setDetails(r);
    }

    @Override
    public int getItemCount() {
        return recommendations.size();
    }

    public class RecommendationHolder extends RecyclerView.ViewHolder {
        private TextView rec_text;

        public RecommendationHolder(@NonNull View itemView) {
            super(itemView);
            rec_text = itemView.findViewById(R.id.rec_text);
        }


        public void setDetails(Recommendation r) {
            rec_text.setText(r.getRecommendationText());
        }
    }

}
