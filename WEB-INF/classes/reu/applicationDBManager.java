package reu;

//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;

/******
	This class manage a connection to the Department database and it should be accessed from the front End. Therefore,
	this class must contain all needed methods for manipulating data without showing how to access the database

*/
public class applicationDBManager{

	//myDBConn is an MySQLConnector object for accessing to the database
	private MySQLConnector myDBConn;
	
	/********
		Default constructor
		It creates a new MySQLConnector object and open a connection to the database
		@parameters:
		
	*/
	public applicationDBManager(){
		//Create the MySQLConnector object
		myDBConn = new MySQLConnector();
		
		//Open the connection to the database
		myDBConn.doConnection();
	}
	
	
	/*******
		listAllDepartment method
			List all departments in the database
			@parameters:
			@returns:
				A ResultSet containing all departments in the database
	*/
	public ResultSet listAllDepartment()
	{
		
		//Declare function variables
		String fields, tables;
		
		//Define the table where the selection is performed
		tables="department";
		//Define the list fields list to retrieve from the table department
		fields ="dept_name, building";
		
		
		System.out.println("listing...");
		
		//Return the ResultSet containing all departments in the database
		return myDBConn.doSelect(fields, tables);
		
		
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

	/***********
		Debugging method
			This method creates an applicationDBManager object, retrieves all departments in the database, and close the connection to the database
			@parameters:
				args[]: String array 
			@returns:
	*/
	public static void main(String[] args)
	{
		
		try{
			//Create a applicationDBManager object
			applicationDBManager appDBMnger = new applicationDBManager();
			System.out.println("Connecting...");
			System.out.println(appDBMnger.toString());
			
			//Call the listAllDepartment in order to retrieve all departments in the database
			ResultSet res=appDBMnger.listAllDepartment();
			
			//Iterate over the ResulSet containing all departments in the database, and count how many tuples were retrieved
			int count=0;
			while (res.next()){
				count++;	
			}
			//Print the results count
			System.out.println("Count:"  + count);
			
			//Close the ResulSet
			res.close();
			//Close the database connection
			appDBMnger.close();
			
		} catch(Exception e)
		{
			//Nothing to show!
			e.printStackTrace();
		}		
	}

}