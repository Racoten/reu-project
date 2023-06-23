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
        String queryEmail = "SELECT QuestionText FROM emailquestions;";
        ResultSet querySend = myDBConn.doSelect(queryEmail); 
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

    // public String getBrowserSecurityGeneralQuestions() {
    //     ResultSet queryBrowserSecurity = "SELECT QuestionText FROM browsersecurityquestions;";

    //     try {
    //         while (queryBrowserSecurity.next()) {
    //             questionsBrowserSecurity = 
    //         }
    //     } catch (Exception e) {
    //         System.out.println(e);
    //     }

    //     return questionsBrowserSecurity;
    // }
}