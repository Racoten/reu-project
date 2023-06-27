package com.example.cybersafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionHolder> {
    private Context context;
    private ArrayList<Question> questions;

    public QuestionAdapter(Context con, ArrayList<Question> questions) {
        this.context = con;
        this.questions = questions;
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup par, int view_type) {
        View v = LayoutInflater.from(context).inflate(R.layout.question_items, par, false);
        return new QuestionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
        Question q = questions.get(position);
        holder.setDetails(q);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class QuestionHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private RadioButton yesb;
        private RadioButton nob;
        public QuestionHolder(View view) {
            super(view);
            question = view.findViewById(R.id.question_text);
            yesb = view.findViewById(R.id.yes_btn);
            nob = view.findViewById(R.id.no_btn);


        }

        public void setDetails(Question q) {
            question.setText(q.getQuestionText());
            nob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    q.setAnswer("no");
                }
            });
            yesb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    q.setAnswer("yes");
                }
            });

        }
    }

}
