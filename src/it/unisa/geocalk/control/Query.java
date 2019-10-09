/**
 * Author: Antonio De Piano
 * Web site: http://www.depiano.it
 * email: depianoantonio@gmail.com
 */

package it.unisa.geocalk.control;

import javax.servlet.*;
import javax.servlet.RequestDispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.corba.se.impl.orbutil.ObjectWriter;

import it.unisa.geocalk.connection.PostgreSqlConnectionPool;
import it.unisa.geocalk.exception.LocalException;
import it.unisa.geocalk.model.Polygon;
import it.unisa.geocalk.model.Restaurant;
import it.unisa.geocalk.model.Station;
import it.unisa.geocalk.model.citiBike;
import it.unisa.geocalk.model.dao.NeighborhoodsDao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import static com.mongodb.client.model.Filters.*;

@WebServlet("/Query")
public class Query extends HttpServlet 
{
	
		private static final long serialVersionUID = 1L;
		private JSONObject obj;
		private JSONObject json_result;
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			  // Write response header.
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        
	        //Quello che mi arriva                
	        String json = request.getParameter("elements");
	        this.obj = new JSONObject(json);
			
		    //Quello che spedisco
	        json_result = new JSONObject();
	        
	        String database=(String)obj.get("database");
	        String query=(String)obj.get("query");
	        
	        json_result.put("database",database);
	        json_result.put("query",query);
	        
	        dispatcher();
	        
	        response.getWriter().write(json_result.toString());   
	        
	        
		/*
		String json = request.getParameter("elements");
		
		System.out.println("Mi arriva [doPOST] : "+json+"\n");
		this.obj = new JSONObject(json);
		System.out.println(obj.toString());
		
		dispatcher((String)obj.get("action"));
		*/
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	private void  dispatcher()
	{
		if(((String)obj.get("database")).equals("mongodb"))
			executeMongoDB();
		//else if(((String)obj.get("database")).equals("postgreSQL"))
			//return executePostgreSQL();
		//return null;
		
	}
	
	private void executeMongoDB()
	{
		if(((String)obj.get("query")).equals("All Stations"))
		{
			NeighborhoodsDao district=new NeighborhoodsDao();
			ArrayList<Station> stations=district.allStationMondoDB();
			json_result.put("result", stations);
			
			/*fai un altra query per i ristoranti
			ArrayList<Restaurant> restaurants=district.allRestaurants();
			System.out.println("...resta..:"+restaurants.toString());
			*/
			
		}
		if(((String)obj.get("query")).equals("prova"))
		{
			NeighborhoodsDao district=new NeighborhoodsDao();
			district.restaurantInNeighborhood();
		}
		if(((String)obj.get("query")).equals("All Restaurants"))
		{
			System.out.println("\nentro\n");
			NeighborhoodsDao district=new NeighborhoodsDao();
			
			ArrayList<Restaurant> restaurants=district.allRestaurants();
			json_result.put("result", restaurants);
			System.out.println(json_result.toString());
			
		}
		if(((String)obj.get("query")).equals("All Neighborhoods"))
		{
			System.out.println("\nentro\n");
			NeighborhoodsDao district=new NeighborhoodsDao();
			
			ArrayList<Polygon> neighborhoods=district.allNeighborhoods();
			json_result.put("result", neighborhoods);
			System.out.println(json_result.toString());
			
		}
		if(((String)obj.get("query")).equals("Where is the station?"))
		{
			System.out.println("eseguo la query mondodb [1]!\n");
			
			
			NeighborhoodsDao district=new NeighborhoodsDao();
			//district.printAll();
			
			String station_name=(String)obj.get("param");
			Station s=district.getStartStationByNameMondoDB(station_name);
			//Station s=district.getStartStationByNameMondoDB("Exchange Place");
			if(s==null)
				s=district.getEndStationByNameMondoDB(station_name);
			json_result.put("stationName",s.getName());
	        json_result.put("latitude",s.getLatitude());
	        json_result.put("longitude",s.getLongitude());
	        json_result.put("id",s.getId());
	        
	        
			System.out.println("check: "+s.toString());
			
		}
		
		if(((String)obj.get("query")).equals("Distance as crow flies between Exchange Place stations and Essex Light Rail station on map!"))
		{
			
			System.out.println("eseguo la query mondodb [2]!\n");
			
			ArrayList<Station> str=new ArrayList<Station>();
			
			NeighborhoodsDao district=new NeighborhoodsDao();
			
			Station start=district.getStartStationByNameMondoDB("Exchange Place");
			System.out.println("..."+start.toString());
			str.add(start);
			
			Station end=district.getEndStationByNameMondoDB("Essex Light Rail");
			System.out.println("..."+end.toString());
			str.add(end);
			
			json_result.put("result", str);
			
		}
		if(((String)obj.get("query")).equals("Trip with longer duration between the Exchange Place departure station and the Harborside end station"))
		{
			NeighborhoodsDao district=new NeighborhoodsDao();
			//ArrayList<citiBike> cB=district.longerTripBetweenStations("Exchange Place","Essex Light Rail");
			ArrayList<citiBike> cB=district.longerTripBetweenStations("Exchange Place","Harborside");
			json_result.put("result", cB);
		}
		if(((String)obj.get("query")).equals("In which neighborhood is the Riviera Caterer restaurant ?"))
		{
			NeighborhoodsDao district=new NeighborhoodsDao();
			//ArrayList<citiBike> cB=district.longerTripBetweenStations("Exchange Place","Essex Light Rail");
			
			ArrayList<Polygon> pl=district.inWhichNeighborhoodIsTheRivieraCatererRestaurant();
			json_result.put("result", pl);
			
			System.out.println("\n\n"+json_result.toString());
		}
		if(((String)obj.get("query")).equals("What are the restaurant within a radius of 500.0m to 1000.0m from Riviera Caterer restaurant ?"))
		{
			NeighborhoodsDao district=new NeighborhoodsDao();
			ArrayList<Restaurant> rest=district.nearRestaurants();
			json_result.put("result", rest);
		}
		
			
	}
	
	private String executePostgreSQL()
	{
		//stampo l'area di Bedford su mappa
		if(((String)obj.get("query")).equals("View the neighborhood with the most stations on the map!"))
		{
			
			/*System.out.println("eseguo la query postgreSQL!");
			
			NeighborhoodsDao district=new NeighborhoodsDao();
			district.NeighborhoodsPostgreSQL("Bedford");
			return district.toString();
			*/
			Connection con=PostgreSqlConnectionPool.PostgreSqlConnectionPool();
			try {
				
				
				//ResultSet resultSet=PostgreSqlConnectionPool.runQuery(con,"select * from public.bike;");
						/*ResultSet resultSet=PostgreSqlConnectionPool.runQuery(con,"SELECT vehicletype, state, make, model, year, color, violation_type, gender, geom " + 
								"FROM example " + 
								"WHERE date_of_stop = '2013-09-05' AND " + 
								"geom <> 'NULL' AND " + 
								"arrest_type = 'Q - Marked Laser' ");
								*/
				ResultSet resultSet=PostgreSqlConnectionPool.runQuery(con,"SELECT * "+
				"FROM neightborhoods "+  
				"WHERE "+  
				"name = 'Bedford'");
				
				while (resultSet.next()) {
					System.out.printf("name:"+resultSet.getString("name")+"\n");
				}
				
				/*
				 * Query perfetta
				 */
				/*
				ResultSet resultSet=PostgreSqlConnectionPool.runQuery(con,"SELECT vehicletype, state, make, model, year, color, violation_type, gender,geom, ST_X(geom) as longitude, ST_Y(geom) as latitude "+
						"FROM example "+
						"WHERE date_of_stop = '2013-09-05' AND " + 
						"geom <> 'NULL' AND " + 
						"arrest_type = 'Q - Marked Laser' ");
				
				
				while (resultSet.next()) {
					System.out.printf("vehicletype: "+resultSet.getString("vehicletype")+"long: "+resultSet.getString("longitude")+"lat: "+resultSet.getString("latitude")+"\n");
				}
				*/
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
		}
		return "";
		
	}

}

