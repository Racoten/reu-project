package reu;
//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;
// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import org.json.*;

public class questionsHandler extends HttpServlet {
    public MySQLConnector myDBConn;

    ArrayList<String> questions = new ArrayList<String>();
    ArrayList<Question> qlist = new  ArrayList<Question>();
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
        }

        else if(param.equals("browsersecuritygeneral")) {
            ArrayList<Question> questions = getGeneralQuestions("BrowserSecurityQuestions");
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
            }
            root_obj.put("questions", resp_array);

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
            }
            root_obj.put("questions", resp_array);

            PrintWriter out = response.getWriter();
            out.println(root_obj.toString());
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
        }
    }

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
    }
}