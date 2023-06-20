package reu;

//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;

/******
	This class manage a connection to the Department database and it should not be accessed from the front End. 
*/
public class MySQLConnector{

	//Database credential <jdbc:<protocol>://<hostName>/<databaseName>>
	private String DB_URL="jdbc:mysql://localhost:3306/cybersafe?serverTimezone=UTC";
	//private String DB_URL="jdbc:mysql://localhost:3306/cybersafe";
	
	//Database authorized user information
	private String USER="root";
	//private String PASS="lol.exe1"
	private String PASS="NewPassword123!";
   
   //Connection objects
   private Connection conn;
   
   //Statement object to perform queries and transations on the database
   private Statement stmt;
   
	/********
		Default constructor
		@parameters:
		
	*/
	public MySQLConnector()
	{
		//define connections ojects null
		conn = null;
		stmt = null;
	}
		
	/********
		doConnection method
			It creates a new connection object and open a connection to the database
			@parameters:

	*/		
	public void doConnection(){
		try{
		  //Register JDBC the driver
		  Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

								   
		  System.out.println("Connecting to database...");
		   //Open a connection using the database credentials
		  conn = DriverManager.getConnection(DB_URL, USER, PASS);
		  
		  System.out.println("Creating statement...");
		  //Create an Statement object for performing queries and transations
		  stmt = conn.createStatement();
		  System.out.println("Statement Ok...");
		} catch(SQLException sqlex){
			sqlex.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/********
		closeConnection method
			Close the connection to the database
			@parameters:

	*/		
	public void closeConnection()
	{
		try{
			//close the statement object
			stmt.close();
			//close the connection to the database
			conn.close();
			}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean doAuthentication(String username, String hash){
		// SQL query to get the user with the given username and password hash
		String query = "SELECT * FROM Users WHERE UserName = '" + username + "' AND PasswordHash = '" + hash + "'";
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println("Running query: " + query);
	
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
				
			// If a user is found, return true, else return false
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return false;
	}

	
	public ResultSet doSelect(String query){
		//Create a ResulSet
		ResultSet result=null;
		
		//Create the selection statement 
		String selectionStatement = query;
		
		try{
			//perform the query and catch results in the result object
			result = stmt.executeQuery(selectionStatement);
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			//return results
			return result;
		}
	}


	public boolean doInsert(String query)
	{
		boolean res=false;
		//try to insert a record to the selected table	
		try{
			res = stmt.execute(query);
			System.out.println("MySQLConnector insertion: " + res);
			
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		return res;
		
			
	}

	/***********
		Debugging method
			This method creates an applicationDBManager object, retrieves all departments in the database, and close the connection to the database
			@parameters:
				args[]: String array 
			@returns:
	*/
	public static void main(String[] args)
	{	
	}

}