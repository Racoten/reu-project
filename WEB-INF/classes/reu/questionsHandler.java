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
            ArrayList<String> questions = getGeneralQuestions("emailquestions");
            String msg = "{\"questions\": [";

            for(int i = 0; i < questions.size(); i++) {
                msg += "\"" + questions.get(i) + "\"";
                if(i < questions.size()-1) {
                    msg += ",";
                }
            }

            msg += "]}";
            PrintWriter out = response.getWriter();
            out.println(msg);
        }

        else if(param.equals("browsersecuritygeneral")) {
            ArrayList<String> questions = getGeneralQuestions("browsersecurityquestions");
            String msg = "{\"questions\": [";

            for(int i = 0; i < questions.size(); i++) {
                msg += "\"" + questions.get(i) + "\"";
                if(i < questions.size()-1) {
                    msg += ",";
                }
            }

            msg += "]}";
            PrintWriter out = response.getWriter();
            out.println(msg);
        } 

        else if(param.equals("browsertargeted")) {
            ArrayList<String> questions = getTargetedQuestions("browsersecurityquestionstargeted", targetedTable);
            String msg = "{\"questions\": [";

            for(int i = 0; i < questions.size(); i++) {
                msg += "\"" + questions.get(i) + "\"";
                if(i < questions.size()-1) {
                    msg += ",";
                }
            }

            msg += "]}";
            PrintWriter out = response.getWriter();
            out.println(msg);
        } 

        else if(param.equals("emailtargeted")) {
            ArrayList<String> questions = getTargetedQuestions("emailquestionstargeted", targetedTable);
            String msg = "{\"questions\": [";

            for(int i = 0; i < questions.size(); i++) {
                msg += "\"" + questions.get(i) + "\"";
                if(i < questions.size()-1) {
                    msg += ",";
                }
            }

            msg += "]}";
            PrintWriter out = response.getWriter();
            out.println(msg);
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

    public ArrayList<String> getGeneralQuestions(String type) {
        String query = "SELECT QuestionText FROM "+type+";";
        ResultSet querySend = myDBConn.doSelect(query);
        
        if (!questions.isEmpty()) {
            questions.clear();
        }

        try {
            while (querySend.next()) {
                questions.add(querySend.getString("QuestionText"));
                System.out.println("Question being added: " + querySend.getString("QuestionText"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally { 
            return questions;
        }
    }

    public ArrayList<String> getTargetedQuestions(String type, String table) {
        String query = "SELECT QuestionText FROM " + type + table + ";";
        ResultSet querySend = myDBConn.doSelect(query);
        
        if (!questions.isEmpty()) {
            questions.clear();
        }

        try {
            while (querySend.next()) {
                questions.add(querySend.getString("QuestionText"));
                System.out.println("Question being added: " + querySend.getString("QuestionText"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally { 
            return questions;
        }
    }
}