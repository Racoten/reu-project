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
			StringBuilder msg = new StringBuilder("{ \"recommendations\": [");

			for(int i = 0; i < recommendations.size(); i++) {
				msg.append(recommendations.get(i));
				if(i < recommendations.size()-1) {
					msg.append(",");
				}
			}

			msg.append("]}");
			PrintWriter out = response.getWriter();
			out.println(msg.toString());
		}

		else if(param.equals("browsersecuritygeneral")) {
			ArrayList<String> recommendations = getGeneralRecommendations("browsersecurityrecommendations");
			StringBuilder msg = new StringBuilder("{ \"recommendations\": [");

			for(int i = 0; i < recommendations.size(); i++) {
				msg.append(recommendations.get(i));
				if(i < recommendations.size()-1) {
					msg.append(",");
				}
			}

			msg.append("]}");
			PrintWriter out = response.getWriter();
			out.println(msg.toString());
		}

		else if(param.equals("emailtargeted")) {
			ArrayList<String> recommendations = getTargetedRecommendations("emailrecommendationstargeted", targetedTable);
			StringBuilder msg = new StringBuilder("{ \"recommendations\": [");

			for(int i = 0; i < recommendations.size(); i++) {
				msg.append(recommendations.get(i));
				if(i < recommendations.size()-1) {
					msg.append(",");
				}
			}

			msg.append("]}");
			PrintWriter out = response.getWriter();
			out.println(msg.toString());
		}

		else if(param.equals("browsersecuritytargeted")) {
			ArrayList<String> recommendations = getTargetedRecommendations("browsersecurityrecommendationstargeted", targetedTable);
			StringBuilder msg = new StringBuilder("{ \"recommendations\": [");

			for(int i = 0; i < recommendations.size(); i++) {
				msg.append(recommendations.get(i));
				if(i < recommendations.size()-1) {
					msg.append(",");
				}
			}

			msg.append("]}");
			PrintWriter out = response.getWriter();
			out.println(msg.toString());
		}

	 }

     public ArrayList<String> getGeneralRecommendations(String type) {
		ArrayList<String> recommendations = new ArrayList<>();
		String query = "SELECT * FROM " + type + ";";
		ResultSet querySend = myDBConn.doSelect(query);

		try {
			while(querySend.next()) {
				String recommendationId = querySend.getString("RecommendationID");
				String recommendationText = querySend.getString("RecommendationText");

				String recommendationJson = "{ \"RecommendationID\": \"" + recommendationId + "\", \"RecommendationText\": \"" + recommendationText + "\" }";

				System.out.println("Recommendation being added: " + recommendationJson);
				recommendations.add(recommendationJson);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return recommendations;
	}

	public ArrayList<String> getTargetedRecommendations(String type, String table) {
		ArrayList<String> recommendations = new ArrayList<>();
		String query = "SELECT * FROM " + type + table + ";";
		ResultSet querySend = myDBConn.doSelect(query);

		try {
			while(querySend.next()) {
				String recommendationId = querySend.getString("RecommendationID");
				String recommendationText = querySend.getString("RecommendationText");

				String recommendationJson = "{ \"RecommendationID\": \"" + recommendationId + "\", \"RecommendationText\": \"" + recommendationText + "\" }";

				System.out.println("Recommendation being added: " + recommendationJson);
				recommendations.add(recommendationJson);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return recommendations;
	}

}
