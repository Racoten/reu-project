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
    public String answer;

	public questionsHandler(){
		//Create the MySQLConnector object
		myDBConn = new MySQLConnector();
		
		//Open the connection to the database
		myDBConn.doConnection();
	}

    public ArrayList<String> getEmailGeneralQuestions() {
        //String queryEmail = "SELECT QuestionText FROM emailquestions;";
        String queryEmail = "SELECT QuestionText FROM EmailQuestions;";
        ResultSet querySend = myDBConn.doSelect(queryEmail);
        
        if (!questionsEmail.isEmpty()) {
            questionsEmail.clear();
        }

        try {
            while (querySend.next()) {
                questionsEmail.add(querySend.getString("QuestionText"));
                System.out.println("Question being added: " + querySend.getString("QuestionText"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return questionsEmail;
    }

    public ArrayList<String> getBrowserSecurityGeneralQuestions() {
        //String queryBrowser = "SELECT QuestionText FROM browsersecurityquestions;";
        String queryBrowser = "SELECT QuestionText FROM BrowserSecurityQuestions;";
        ResultSet querySend = myDBConn.doSelect(queryBrowser);

        if (!questionsBrowserSecurity.isEmpty()) {
            questionsBrowserSecurity.clear();
        }

        try {
            while (querySend.next()) {
                questionsBrowserSecurity.add(querySend.getString("QuestionText"));
                System.out.println("Question being added: " + querySend.getString("QuestionText"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return questionsBrowserSecurity;
    }
}