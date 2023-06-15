package reu;

//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;

/******
	This class manage a connection to the Department database and it should not be accessed from the front End. 
*/
public class MySQLCompleteConnectorPrivileged{

	//Database credential <jdbc:<protocol>://<hostName>/<databaseName>>
	private String DB_URL="jdbc:mysql://localhost/social_network";
	
	//Database authorized user information
	private String USER="social_network_admin";
	private String PASS="password";
   
   //Connection objects
   private Connection conn;
   
   //Statement object to perform queries and transations on the database
   private Statement stmt;
   
	/********
		Default constructor
		@parameters:
		
	*/
	public MySQLCompleteConnectorPrivileged()
	{
		//define connections ojects null
		conn = null;
		stmt = null;}
		
	/********
		doConnection method
			It creates a new connection object and open a connection to the database
			@parameters:

	*/		
	public void doConnection(){
		try{
		  //Register JDBC the driver
		  Class.forName("com.mysql.jdbc.Driver").newInstance();

								   
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

    public boolean doUpdate(String tableName, String userName, String setClause, String whereClause) {
        boolean result = false;
        try {

            String sql = "UPDATE " + tableName + setClause + " " + whereClause + ";";

            result =stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    

	public boolean doDelete(String table, String where)
	{
		boolean res=false;
		String queryString ="DELETE FROM "+ table + " WHERE " + where + ";";

		//try to insert a record to the selected table
		try{
			 res=stmt.execute(queryString);
			 System.out.println("MySQLCompleteConnector insertion: " + res);
			 
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		finally{
			
		}
			return res;
	}

	public ResultSet doPageSelect(String query){
		//Create a ResulSet
		ResultSet result=null;
		System.out.println(query);
		try{
			//perform the query and catch results in the result object
			result = stmt.executeQuery(query);
		} catch(Exception e){
			e.printStackTrace();
		}
		finally{
			//return results
			return result;
		}
	}

	public static void main(String[] args)
	{	
		System.out.println("TEsting");
		//Create a MySQLConnector
		MySQLCompleteConnector conn = new MySQLCompleteConnector();
		//Declare tthe fiels, tables and whereClause string objects
		String fields, tables, whereClause;
		//Define the projected fields
		fields ="dept_name, building";
		//Define the selected tables
		tables="department";
		//Establish the where clause
		whereClause="budget>1000";		
		
			
		try{
			System.out.println("Connecting...");
			//Establish the database connection
			conn.doConnection();
			//perform the query using the doSelect methods with 3 parameters
			ResultSet res=conn.doSelect(fields, tables, whereClause);
		
			//Iterate over the ResulSet containing all departments in the database, and count how many tuples were retrieved
			int count=0;
			while (res.next())
			{
				count++;
				
			}
			//Print the results count
			System.out.println("Count: " + count);
			
			//Close the ResulSet
			res.close();
			//Close the database connection
			conn.closeConnection();
			
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}