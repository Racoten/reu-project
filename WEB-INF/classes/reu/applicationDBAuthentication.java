package reu;

//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;

//Import hashing functions
import org.apache.commons.codec.*;

// File Operations to handle byte streams when user uploads profile picture
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/******
	This class authenticate users using userName and passwords

*/

public class applicationDBAuthentication extends HttpServlet{

	//myDBConn is an MySQLConnector object for accessing to the database
	private MySQLConnector myDBConn;
	
	/********
		Default constructor
		It creates a new MySQLConnector object and open a connection to the database
		@parameters:
		
	*/
	public applicationDBAuthentication(){
		//Create the MySQLConnector object
		myDBConn = new MySQLConnector();
		
		//Open the connection to the database
		myDBConn.doConnection();
	}
	
	/**
	 * 
	 * @param file
	 * @param username
	 */
	public void setProfilePicture(String file, String username) {
		String sql = "INSERT INTO picturesForUser (PicturePath, UserName) VALUES (" + file + ", '" + username + "');";
		myDBConn.doInsert(sql);
	}
	/**
	 * 
	 * @param username
	 * @return ResultSet containing the file name of the picture
	 */
	public ResultSet getProfilePicture(String username) {
		String query = "SELECT PicturePath from picturesForUser WHERE username = '" + username + "';";

		return myDBConn.doGetProfilePicture(query);
	}

	/**
	 * Adds a new user to the database.
	 * 
	 * @param username      The username of the new user.
	 * @param completeName  The complete name of the new user.
	 * @param userpass      The password of the new user.
	 * @param userTelephone The telephone number of the new user.
	 * @param dateOfBirth   The date of birth of the new user.
	 * @param gender        The gender of the new user.
	 * @param userEmail     The email address of the new user.
	 * @param street        The street of the new user.
	 * @param town          The town of the new user.
	 * @param state         The state of the new user.
	 * @param country       The country of the new user.
	 * @param degree        The degree of the new user.
	 * @param school        The school of the new user.
	 * 
	 * @return              true if the insertion was successful, false otherwise.
	 */
	public boolean addUser(String username, String completeName, String userpass, String userTelephone, String dateOfBirth, String gender, String userEmail, String street, String town, String state, String country, String degree, String school)
	{
		boolean res = false;
		String userTable = "UserInformation";
		String addressTable = "AddressInformation";
		String hashingValue = hashingSha256(username + userpass);

		// String userValues = "'" + username + "', '" + hashingValue + "', '" + completeName + "', '" + userTelephone + "', '" + dateOfBirth + "', '" + gender + "', '" + userEmail + "'";
		// res = myDBConn.doInsert(userTable, userValues);
		// String addressValues = "'" + username + "', '" + degree + "', '" + school + "', '" + street + "', '" + town + "', '" + state + "', '" + country + "'";
		// res &= myDBConn.doInsert(addressTable, addressValues);

		// Special insert to add automatic roles. Add userRole and change manually if needed.
		String query = "INSERT INTO RolesForUser (username, roleID) VALUES ('" + username + "', 2);";
		// res &= myDBConn.doRoleInsert(query);
		
		System.out.println("Insertion result: " + res);
		return res;
	}
	
	/**
		hashingSha256 method
		Generates a hash value using the sha256 algorithm.
		@param plainText
		@return the hash string based on the plainText
	*/
	private String hashingSha256(String plainText)
	{
			String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(plainText); 
			return sha256hex;
	}
	
	/*********
		close method
			Close the connection to the database.
			This method must be called at the end of each page/object that instatiates a applicationDBManager object
			@parameters:
			@returns:
	*/
	public void close()
	{
		//Close the connection
		myDBConn.closeConnection();
	}

}