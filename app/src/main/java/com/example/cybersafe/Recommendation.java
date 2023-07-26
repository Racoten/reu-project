package com.example.cybersafe;

public class Recommendation {
    private String recommendation_text;
    private String q_text;

    public Recommendation(String rec, String q) {
        this.recommendation_text = rec;
        this.q_text = q;
    }

    public String getRecommendationText() {
        return this.recommendation_text;
    }

    public String getQuestionText() {
        return this.q_text;
    }


}
