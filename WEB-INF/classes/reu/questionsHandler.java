package reu;
//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;
// Import required java libraries
import java.io.*;
import java.util.*;

public class questionsHandler {
    public MySQLConnector myDBConn;

    ArrayList<String> questionsEmail = new ArrayList<String>();
    ArrayList<String> questionsBrowserSecurity = new ArrayList<String>();
    ArrayList<String> questions = new ArrayList<String>();
    public String answer;

	public questionsHandler(){
		//Create the MySQLConnector object
		myDBConn = new MySQLConnector();
		
		//Open the connection to the database
		myDBConn.doConnection();
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
        }

        return questions;
    }
}