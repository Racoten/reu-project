//This class belongs to the ut.JAR.CPEN410 package
package reu;
/********
CPEN 410 Mobile, Web, and Internet programming

This servlet perform a dummy authentication process
if the user is authenticated, it sends a json objects containing: id, name, userName and email
Required values:
	userName: user
	passWord: pass



******************/
//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;
// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

// Extend HttpServlet class
public class appAuth extends HttpServlet {

	public MySQLCompleteConnector myDBConn;

	public appAuth(){
		//Create the MySQLConnector object
		myDBConn = new MySQLCompleteConnector();
		
		//Open the connection to the database
		myDBConn.doConnection();
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

		if (param.equals("auth")) {
			System.out.println("Param is auth");
			String username = request.getParameter("user");
			String password = request.getParameter("pass");

			String hashingVal = hashingSha256(username + password);
			
			System.out.println("Received user: " + username + " with password: " + hashingVal);

			//Return the ResultSet containing all roles assigned to the user
			Boolean queryReturn = myDBConn.doAuthentication(username, hashingVal);

			if (queryReturn == true) {
				System.out.println("User: " + username + " Has logged In!");

				// Set response content type
				response.setContentType("text/html");
				String msg = doAuthentication(username);

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
		else if (param.equals("search")) {
			System.out.println("Param is search");
		}
	}

	/****
		This method perform a dummy authentication process
	***/
	public String doAuthentication(String username) {
		String msg = "";
		String fields = "";
		String tables = "";
		String whereClause = "";

	
		String query = "SELECT " + fields + " FROM " + tables + " WHERE " + whereClause;
		System.out.println(query);
	
		try {
			ResultSet userInfo = myDBConn.doSelect(query);
	
			if (userInfo.next()) {
				return msg;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Return the actual message
		return null;
	}
	
	


   public void destroy() {
      // do nothing.
   }
}
