package reu;
//Import the java.sql package for managing the ResulSet objects
import java.sql.* ;
// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class recommendationsHandler extends HttpServlet {
    public MySQLConnector myDBConn;

    ArrayList<String> recommendations = new ArrayList<String>();
    public String answer;

	public recommendationsHandler(){
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
			ArrayList<String> recommendations = getGeneralRecommendations("emailrecommendations");
			String msg = "{\"recommendations\": [";

			for(int i = 0; i < recommendations.size(); i++) {
				msg += "\"" + recommendations.get(i) + "\"";
				if(i < recommendations.size()-1) {
					msg += ",";
				}
			}

			msg += "]}";
			PrintWriter out = response.getWriter();
			out.println(msg);
		}

		else if(param.equals("browsersecuritygeneral")) {
			ArrayList<String> recommendations = getGeneralRecommendations("browsersecurityrecommendations");
			String msg = "{\"recommendations\": [";

			for(int i = 0; i < recommendations.size(); i++) {
				msg += "\"" + recommendations.get(i) + "\"";
				if(i < recommendations.size()-1) {
					msg += ",";
				}
			}

			msg += "]}";
			PrintWriter out = response.getWriter();
			out.println(msg);
		} 

		else if(param.equals("emailtargeted")) {
			ArrayList<String> recommendations = getTargetedRecommendations("emailrecommendationstargeted", targetedTable);
			String msg = "{\"recommendations\": [";

			for(int i = 0; i < recommendations.size(); i++) {
				msg += "\"" + recommendations.get(i) + "\"";
				if(i < recommendations.size()-1) {
					msg += ",";
				}
			}

			msg += "]}";
			PrintWriter out = response.getWriter();
			out.println(msg);
		} 

		else if(param.equals("browsersecuritytargeted")) {
			ArrayList<String> recommendations = getTargetedRecommendations("browsersecurityrecommendationstargeted", targetedTable);
			String msg = "{\"recommendations\": [";

			for(int i = 0; i < recommendations.size(); i++) {
				msg += "\"" + recommendations.get(i) + "\"";
				if(i < recommendations.size()-1) {
					msg += ",";
				}
			}

			msg += "]}";
			PrintWriter out = response.getWriter();
			out.println(msg);
		}

	 }

     public ArrayList<String> getGeneralRecommendations(String type) {
        String query = "SELECT RecommendationText FROM " + type + ";";
        ResultSet querySend = myDBConn.doSelect(query);

        if (!recommendations.isEmpty()) {
            recommendations.clear();
        }
        try {
            while(querySend.next()) {
                recommendations.add(querySend.getString("RecommendationText"));
                System.out.println("Recommendation being added: " + querySend.getString("RecommendationText"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            return recommendations;
        }
     }

     public ArrayList<String> getTargetedRecommendations(String type, String table) {
        String query = "SELECT RecommendationText FROM " + type  + table + ";";
        ResultSet querySend = myDBConn.doSelect(query);

        if (!recommendations.isEmpty()) {
            recommendations.clear();
        }
        try {
            while(querySend.next()) {
                recommendations.add(querySend.getString("RecommendationText"));
                System.out.println("Recommendation being added: " + querySend.getString("RecommendationText"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            return recommendations;
        }
     }
}
