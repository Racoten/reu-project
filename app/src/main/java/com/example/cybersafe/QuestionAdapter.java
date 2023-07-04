package com.example.cybersafe;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;
/*
TODO: decide if maybe button/input should be hidden if question isn't open-ended.
     Schema/JSON responses could include is_open_ended flag, which would trigger the open-ended input box to show

 */
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

        //q.setAnswer("no");
        holder.setDetails(q);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class QuestionHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private TextView maybetext;
        private RadioButton yesb;
        private RadioButton nob;
        private RadioButton maybebtn;
        public QuestionHolder(View view) {
            super(view);
            question = view.findViewById(R.id.question_text);
            yesb = view.findViewById(R.id.yes_btn);
            nob = view.findViewById(R.id.no_btn);
            maybebtn = view.findViewById(R.id.maybe_btn);
            maybetext = view.findViewById(R.id.maybe_input);



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
            maybetext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(maybebtn.isChecked() && !editable.toString().isEmpty()) {
                        q.setAnswer(editable.toString());
                    } else if(editable.toString().isEmpty() && !yesb.isChecked()) {
                        q.setAnswer("no");
                    }
                    System.out.println(q.getAnswer());

                }
            });
        }
    }

}
