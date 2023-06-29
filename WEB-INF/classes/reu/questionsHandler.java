package reu;
//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;
// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class questionsHandler extends HttpServlet {
    public MySQLConnector myDBConn;

    ArrayList<String> questions = new ArrayList<String>();
    public String answer;

	public questionsHandler(){
		//Create the MySQLConnector object
		myDBConn = new MySQLConnector();
		
		//Open the connection to the database
		myDBConn.doConnection();
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Retreive the http request parameters
		String param = request.getParameter("param");
        String targetedTable = request.getParameter("targetedtable");

		System.out.println("Receive request with parameter: " + param);

        if(param.equals("emailgeneral")) {
<<<<<<< HEAD
            ArrayList<String> questions = getGeneralQuestions("emailquestions");
            StringBuilder msg = new StringBuilder("{ \"questions\": [");

            for(int i = 0; i < questions.size(); i++) {
                System.out.println(questions.get(i));
                msg.append(questions.get(i));
                if(i < questions.size()-1) {
                    msg.append(",");
                }
            }

            msg.append("]}");
            PrintWriter out = response.getWriter();
            out.println(msg.toString());
=======
            ArrayList<Question> questions = getGeneralQuestions("EmailQuestions");
            JSONObject root_obj = new JSONObject();
            JSONArray resp_array = new JSONArray();
            for(int i = 0; i < questions.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("question_text", questions.get(i).getQuestion_text());
                obj.put("id", questions.get(i).getId());
                obj.put("weight", questions.get(i).getWeight());
                resp_array.put(obj);
            }
            root_obj.put("questions", resp_array);
            //System.out.println(root_obj.toString());
            PrintWriter out = response.getWriter();
            out.println(root_obj.toString());
>>>>>>> d350f8e (json on backend)
        }

        else if(param.equals("browsersecuritygeneral")) {
<<<<<<< HEAD
            ArrayList<String> questions = getGeneralQuestions("browsersecurityquestions");
            StringBuilder msg = new StringBuilder("{ \"questions\": [");

            for(int i = 0; i < questions.size(); i++) {
                msg.append(questions.get(i));
                if(i < questions.size()-1) {
                    msg.append(",");
                }
=======
            ArrayList<Question> questions = getGeneralQuestions("BrowserSecurityQuestions");
            JSONObject root_obj = new JSONObject();
            JSONArray resp_array = new JSONArray();
            for(int i = 0; i < questions.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("question_text", questions.get(i).getQuestion_text());
                obj.put("id", questions.get(i).getId());
                obj.put("weight", questions.get(i).getWeight());
                resp_array.put(obj);
>>>>>>> d350f8e (json on backend)
            }

<<<<<<< HEAD
            msg.append("]}");
            PrintWriter out = response.getWriter();
            out.println(msg.toString());
        }


        else if(param.equals("browsertargeted")) {
            ArrayList<String> questions = getTargetedQuestions("browsersecurityquestionstargeted", targetedTable);
            StringBuilder msg = new StringBuilder("{ \"questions\": [");

            for(int i = 0; i < questions.size(); i++) {
                msg.append(questions.get(i));
                if(i < questions.size()-1) {
                    msg.append(",");
                }
=======
            PrintWriter out = response.getWriter();
            out.println(root_obj.toString());
        } 

        else if(param.equals("browsertargeted")) {
            ArrayList<Question> questions = getTargetedQuestions("BrowserSecurityQuestionsTargeted", targetedTable);
            JSONObject root_obj = new JSONObject();
            JSONArray resp_array = new JSONArray();
            for(int i = 0; i < questions.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("question_text", questions.get(i).getQuestion_text());
                obj.put("id", questions.get(i).getId());
                obj.put("weight", questions.get(i).getWeight());
                resp_array.put(obj);
>>>>>>> d350f8e (json on backend)
            }

<<<<<<< HEAD
            msg.append("]}");
            PrintWriter out = response.getWriter();
            out.println(msg.toString());
        }

        else if(param.equals("emailtargeted")) {
            ArrayList<String> questions = getTargetedQuestions("emailquestionstargeted", targetedTable);
            StringBuilder msg = new StringBuilder("{ \"questions\": [");

            for(int i = 0; i < questions.size(); i++) {
                msg.append(questions.get(i));
                if(i < questions.size()-1) {
                    msg.append(",");
                }
=======
            PrintWriter out = response.getWriter();
            out.println(root_obj.toString());
        } 

        else if(param.equals("emailtargeted")) { 
            ArrayList<Question> questions = getTargetedQuestions("EmailQuestionsTargeted", targetedTable);
            JSONObject root_obj = new JSONObject();
            JSONArray resp_array = new JSONArray();
            for(int i = 0; i < questions.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("question_text", questions.get(i).getQuestion_text());
                obj.put("id", questions.get(i).getId());
                obj.put("weight", questions.get(i).getWeight());
                resp_array.put(obj);
>>>>>>> d350f8e (json on backend)
            }

<<<<<<< HEAD
            msg.append("]}");
            PrintWriter out = response.getWriter();
            out.println(msg.toString());
=======
            PrintWriter out = response.getWriter();
            out.println(root_obj.toString());
>>>>>>> d350f8e (json on backend)
        }


        else {
            // Set response content type
            response.setContentType("text/html");

            // Actual logic goes here.
            PrintWriter out = response.getWriter();
            
            // Send the response
            out.println("There was an issue with the parameter...");
        }
	 }

<<<<<<< HEAD
    public ArrayList<String> getGeneralQuestions(String type) {
    String query = "SELECT * FROM " + type + ";";
    ResultSet querySend = myDBConn.doSelect(query);

    try {
        while (querySend.next()) {
            String questionId = querySend.getString("QuestionID");
            String questionText = querySend.getString("QuestionText");
            String weight = querySend.getString("Weight");

            String questionJson = "{ \"QuestionID\": \"" + questionId + "\", \"QuestionText\": \"" + questionText + "\", \"Weight\": \"" + weight + "\" }";

            System.out.println("Question being added: " + questionJson);
            questions.add(questionJson);
=======
    public ArrayList<Question> getGeneralQuestions(String type) {
        String query = "SELECT * FROM "+type+";";
        ResultSet querySend = myDBConn.doSelect(query);
        
        if (!qlist.isEmpty()) {
            qlist.clear();
        }

        try {
            while (querySend.next()) {
                qlist.add(new Question(querySend.getString("QuestionText"), querySend.getInt("QuestionID"), querySend.getInt("Weight")));
                System.out.println("Question being added: " + querySend.getString("QuestionText"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally { 
            return qlist;
>>>>>>> d350f8e (json on backend)
        }
    }

<<<<<<< HEAD
    return questions;
}



    public ArrayList<String> getTargetedQuestions(String type, String table) {
        String query = "SELECT * FROM " + type + table + ";";
        ResultSet querySend = myDBConn.doSelect(query);

        try {
            while (querySend.next()) {
                String questionId = querySend.getString("QuestionID");
                String questionText = querySend.getString("QuestionText");
                String weight = querySend.getString("Weight");

                String questionJson = "{ \"QuestionID\": \"" + questionId + "\", \"QuestionText\": \"" + questionText + "\", \"Weight\": \"" + weight + "\" }";

                System.out.println("Question being added: " + questionJson);
                questions.add(questionJson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return questions;
=======
    public ArrayList<Question> getTargetedQuestions(String type, String table) {
        String query = "SELECT * FROM " + type + table + ";";
        ResultSet querySend = myDBConn.doSelect(query);
        
        if (!qlist.isEmpty()) {
            qlist.clear();
        }

        try {
            while (querySend.next()) {
                qlist.add(new Question(querySend.getString("QuestionText"), querySend.getInt("QuestionID"), querySend.getInt("Weight")));
                System.out.println("Question being added: " + querySend.getString("QuestionText"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally { 
            return qlist;
        }
>>>>>>> d350f8e (json on backend)
    }
}