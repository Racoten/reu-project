package reu;
//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;
import java.text.SimpleDateFormat;
// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;


public class SessionHandler extends HttpServlet{
    public String token;
    public MySQLConnector myDBConn;
    
    public SessionHandler(String username) {
        //Create the MySQLConnector object
		myDBConn = new MySQLConnector();
		
		//Open the connection to the database
		myDBConn.doConnection();

        String dateNow = getDate();
        String cleartext = username + dateNow;

        token = hashingSha256(cleartext);

        String query = "UPDATE users SET token='" + token + "' WHERE username = '" + username + "';";
        System.out.println("Generating user token: " + token);
        myDBConn.doInsert(query);
    }

    private String hashingSha256(String plainText)
	{
			String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(plainText); 
			return sha256hex;
	}

    public static String getDate() {  
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");  
        Date date = new Date();  
        return formatter.format(date);
    }
}
