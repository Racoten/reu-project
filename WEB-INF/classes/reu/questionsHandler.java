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
        }


        else if(param.equals("browsersecuritygeneral")) {
            ArrayList<String> questions = getGeneralQuestions("browsersecurityquestions");
            StringBuilder msg = new StringBuilder("{ \"questions\": [");

            for(int i = 0; i < questions.size(); i++) {
                msg.append(questions.get(i));
                if(i < questions.size()-1) {
                    msg.append(",");
                }
            }

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
            }

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
            }

            msg.append("]}");
            PrintWriter out = response.getWriter();
            out.println(msg.toString());
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
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } 

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
    }

}