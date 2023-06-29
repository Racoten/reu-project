package reu;

public class Question {
    private String question_text;
    private Integer id;
    private Integer weight;

    public Question(String q, Integer id, Integer w) {
        this.question_text = q;
        this.id = id;
        this.weight = w;
    }

    public String getQuestion_text() {
        return question_text;
    }
    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }



    
}
