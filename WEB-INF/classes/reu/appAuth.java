package reu;
//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;
// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

// Extend HttpServlet class
public class appAuth extends HttpServlet {

	public MySQLConnector myDBConn;
	public questionsHandler myQuestionsHandler;

	public appAuth(){
		//Create the MySQLConnector object
		myDBConn = new MySQLConnector();
		
		//Open the connection to the database
		myDBConn.doConnection();

		myQuestionsHandler = new questionsHandler();
	}

	private String message;
	private String title;

	public void init() throws ServletException {

	}

	private String hashingSha256(String plainText)
	{
			String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(plainText); 
			return sha256hex;
	}
/***
	doGet method: it is executed when the GET method is used for the http request
**/

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
     
	 
      // Set response content type
      response.setContentType("text/html");

      // Actual logic goes here.
      PrintWriter out = response.getWriter();
      
	  // Send the response
	  out.println("This servlet does not support authentication via GET method!");
	 }
/*****

	doPost method: this method is executed when the POST method is used for the http request

**/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
		//Retreive the http request parameters
		String param = request.getParameter("param");

		System.out.println("Receive request with parameter: " + param);

		if (param.equals("login")) {
			System.out.println("Param is login");
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			String hashingVal = hashingSha256(username + password);
			
			System.out.println("Received user: " + username + " with password: " + hashingVal);

			//Return the ResultSet containing all roles assigned to the user
			Boolean queryReturn = myDBConn.doAuthentication(username, hashingVal);

			if (queryReturn == true) {
				System.out.println("User: " + username + " Has logged In!");

				// Set response content type
				response.setContentType("text/html");
				StringBuilder msg = doAuthentication(username);

				// Actual logic goes here.
				PrintWriter out = response.getWriter();

				//Send the final response to the requester
				out.println(msg);
				System.out.println(msg);
			}
			else {
				PrintWriter out = response.getWriter();
				out.println("not");	  
			}
		}
		else if (param.equals("register")) {
			System.out.println("Param is register");

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String msg = doRegister(username, email, password);

			// Actual logic goes here.
			PrintWriter out = response.getWriter();

			//Send the final response to the requester
			out.println(msg);
			System.out.println(msg);
		}

		else if(param.equals("emailgeneral")) {
			ArrayList<String> questions = myQuestionsHandler.getEmailGeneralQuestions();
			String msg = "{";

			for(int i = 0; i < questions.size(); i++) {
				msg += "\"" + (i+1) + "\": \"" + questions.get(i) + "\"";
				if(i < questions.size()-1) {
					msg += ",";
				}
			}

			msg += "}";
			PrintWriter out = response.getWriter();
			out.println(msg);
		}

		else if(param.equals("browsersecuritygeneral")) {
			ArrayList<String> questions = myQuestionsHandler.getBrowserSecurityGeneralQuestions();
			String msg = "{";

			for(int i = 0; i < questions.size(); i++) {
				msg += "\"" + (i+1) + "\": \"" + questions.get(i) + "\"";
				if(i < questions.size()-1) {
					msg += ",";
				}
			}

			msg += "}";
			PrintWriter out = response.getWriter();
			out.println(msg);
		}
	}

	/****
		This method perform a dummy authentication process
	***/
	public StringBuilder doAuthentication(String username) {
		String msg = "";
		String fields = "*";
		String tables = "users, useranswers";
		String whereClause = "UserName = '" + username + "';";

	
		String query = "SELECT " + fields + " FROM " + tables + " WHERE " + whereClause;
		System.out.println(query);
		StringBuilder json = new StringBuilder();
	
		try {
			ResultSet userInfo = myDBConn.doSelect(query);
	
			
		
			while (userInfo.next()) {
				String email = userInfo.getString("email");
		
				json.append("{\n");
				json.append("\t\"email\": \"" + email + "\",\n");
				json.append("\t\"userName\": \"" + username + "\",\n");
				json.append("},\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Return the actual message
		return json;
	}

	public String doRegister(String username, String email, String password) {
		String msg = "";
		String table, values;

		String hashingVal = hashingSha256(username + password);

		table = "users";
		values = "'" + username + "', '" + email + "', '" + hashingVal + "'";

		String query = "INSERT INTO " + table + " (UserName, Email, PasswordHash) VALUES (" + values + ");";

		try {
			boolean result = myDBConn.doInsert(query);

			if (result == true) {
				msg = "no";
			} else {
				System.out.println("New user added: " + username);
				msg = "yes";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;
	}

    public void destroy() {
    	// do nothing.
    }
}
