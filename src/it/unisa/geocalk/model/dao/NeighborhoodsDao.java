package it.unisa.geocalk.model.dao;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;

import com.google.gson.Gson;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.Block;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Position;
import com.sun.xml.internal.txw2.Document;
import com.mongodb.client.model.geojson.Point;
import it.unisa.geocalk.connection.DBMongoConnectionPool;
import it.unisa.geocalk.connection.PostgreSqlConnectionPool;
import it.unisa.geocalk.exception.LocalException;
import it.unisa.geocalk.model.Geometry;
import it.unisa.geocalk.model.GeometryData;
import it.unisa.geocalk.model.GeometryPoint;
import it.unisa.geocalk.model.Neighborhoods;
import it.unisa.geocalk.model.Dot;
import it.unisa.geocalk.model.Polygon;
import it.unisa.geocalk.model.Restaurant;
import it.unisa.geocalk.model.Station;
import it.unisa.geocalk.model.citiBike;
import it.unisa.geocalk.model.dao.operations.NeighborhoodsDaoInterface;
import it.unisa.geocalk.utils.Constants;

public class NeighborhoodsDao implements NeighborhoodsDaoInterface{
	
	//Dammi il poligono in base ai punti: lat, long
	//In which neighborhood is the Riviera Caterer restaurant ?
	public ArrayList<Polygon> inWhichNeighborhoodIsTheRivieraCatererRestaurant() {
		ArrayList<Polygon> re=new ArrayList<Polygon>();
		try
		{
			MongoClient client = DBMongoConnectionPool.createMongoDBConnection();
			MongoDatabase db=DBMongoConnectionPool.selectDB(client);
			MongoCollection<org.bson.Document> collection=db.getCollection(Constants.mongoCollectionNeighborhoods);
			
			
			
			Block<org.bson.Document> printBlock = new Block<org.bson.Document>() {
			  
				public void apply(final org.bson.Document district) {
					
						Polygon pl=new Polygon();
			    	/*
						JSONObject jsonReport = new JSONObject();
			    	    jsonReport.put("id", district.getObjectId("_id").toString());
			            reports.put(jsonReport);
			            */
			    	
			    		//System.out.println("Ci sono: "+district.toJson());
			    		
			    		//System.out.println("object name:"+district.getObjectId("_id"));
			    		//System.out.println("geometry: "+district.get("geometry"));
			    		org.bson.Document doc=(org.bson.Document) district.get("geometry");
			    		
			    		//System.out.println("type: "+doc.get("type"));
			    		
			    		GeometryData g=new Gson().fromJson(doc.toJson(), GeometryData.class);
	        	    	
			    		//System.out.println("eooooo:"+g.toString());
			    		System.out.println("\n\nStampo le coordinate:\n");
			    		g.printPoint(g.getPoints());
			    		System.out.println("Name: "+district.get("name"));
	    	    	    
			    		pl.setName(district.get("name").toString());
			    		pl.setPointList(g.getPoints());
			    		re.add(pl);
	    	    	}
	    	
	    	    
			};
			
			//Funziona mi da tutti i ristoranti tra 1 metro e 800000 metri dal punto definito
			//Point refPoint = new Point(new Position(-73.98241999999999, 40.579505));
			//collection.find(Filters.near("location.coordinates", refPoint, 8000000.0, 1.0)).forEach(printBlock);
			
			Point refPoint = new Point(new Position(-73.98241999999999, 40.579505));
			/*
			 * Questo è quello buono
			Point refPoint = new Point(new Position(-73.93414657, 40.82302903));
			*/
			collection.find(Filters.geoIntersects("geometry", refPoint)).forEach(printBlock);
		
			//collection.find().forEach(printBlock);
			//System.out.println("--"+re.toString());
			DBMongoConnectionPool.close(client);
			
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		} catch (LocalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;
	}

	public void restaurantInNeighborhood() {
		
		List<List<Double>> polygons = new ArrayList<>();
		
		try
		{
			MongoClient client = DBMongoConnectionPool.createMongoDBConnection();
			MongoDatabase db=DBMongoConnectionPool.selectDB(client);
			MongoCollection<org.bson.Document> collection=db.getCollection(Constants.mongoCollectionNeighborhoods);
			MongoCollection<org.bson.Document> collectionRestaurant=db.getCollection(Constants.mongoCollectionRestaurants);
			
			
			Block<org.bson.Document> printBlock = new Block<org.bson.Document>() {
			  
				public void apply(final org.bson.Document district) {
					
			    		System.out.println("Ci sono: "+district.toJson());
			    		org.bson.Document doc=(org.bson.Document) district.get("geometry");
			    		
			    		GeometryData g=new Gson().fromJson(doc.toJson(), GeometryData.class);
	        	    	
			    		for(int i= 0;i<g.getPoints().size();i++)
					    {
					       polygons.add(Arrays.asList(g.getPoints().get(i).getLatitude(),g.getPoints().get(i).getLongitude()));
					    }
			    		
			    		Block<org.bson.Document> printBlock1 = new Block<org.bson.Document>() {
							  
							public void apply(final org.bson.Document district) {
								
						    		System.out.println("Ci sono: "+district.toJson());
						    		
				    	    	}
						};
			    		 collectionRestaurant.find(Filters.geoWithinPolygon("location",polygons)).forEach(printBlock1);
			    		
	    	    	}
			};
			
			
			
			//Funziona mi da tutti i ristoranti tra 1 metro e 800000 metri dal punto definito
			//Point refPoint = new Point(new Position(-73.98241999999999, 40.579505));
			//collection.find(Filters.near("location.coordinates", refPoint, 8000000.0, 1.0)).forEach(printBlock);
			
		
			
			collection.find(eq("name","Midwood")).forEach(printBlock);
			
			
				
			
			DBMongoConnectionPool.close(client);
			
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		} catch (LocalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	//Ristoranti tra 1 metro e 800000 raggio sferico dal punto definito
	public ArrayList<Restaurant> nearRestaurants()
	{
		ArrayList<Restaurant> re=new ArrayList<Restaurant>();
		try
		{
			MongoClient client = DBMongoConnectionPool.createMongoDBConnection();
			MongoDatabase db=DBMongoConnectionPool.selectDB(client);
			MongoCollection<org.bson.Document> collection=db.getCollection("restaurants");
			collection.createIndex(Indexes.geo2dsphere("location.coordinates"));
			
			Block<org.bson.Document> printBlock = new Block<org.bson.Document>() {
			  
				public void apply(final org.bson.Document district) {
			    	
						Restaurant restau=new Restaurant();
						
						org.bson.Document doc=(org.bson.Document) district.get("location");
						List cc=(List) doc.get("coordinates");
						//estraggo la long o la lat --> controlla
						double longitude=Double.parseDouble(cc.get(0).toString());
						double latitude=Double.parseDouble(cc.get(1).toString());
						restau.setCoordinates(new Dot(longitude,latitude));
						restau.setName(district.get("name").toString());
						System.out.println("*"+restau.toString());
						re.add(restau);
				}
			};
			
			//Funziona mi da tutti i ristoranti tra 1 metro e 800000 metri dal punto definito
			//Il ristorante è Riviera Caterer
			Point refPoint = new Point(new Position(-73.98241999999999, 40.579505));
			//collection.find(Filters.near("location.coordinates", refPoint, 8000000.0, 1.0)).forEach(printBlock);
			collection.find(Filters.near("location.coordinates", refPoint, 1000.0, 500.0)).forEach(printBlock);
			
			DBMongoConnectionPool.close(client);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (LocalException e)
		{
			e.printStackTrace();
		}
		
		return re;
	}
	
	
	public ArrayList<Polygon> allNeighborhoods()
	{
		ArrayList<Polygon> re=new ArrayList<Polygon>();
		try
		{
			MongoClient client = DBMongoConnectionPool.createMongoDBConnection();
			MongoDatabase db=DBMongoConnectionPool.selectDB(client);
			MongoCollection<org.bson.Document> collection=db.getCollection(Constants.mongoCollectionNeighborhoods);
			
			
			
			Block<org.bson.Document> printBlock = new Block<org.bson.Document>() {
			  
				public void apply(final org.bson.Document district) {
					
						Polygon pl=new Polygon();
			    	/*
						JSONObject jsonReport = new JSONObject();
			    	    jsonReport.put("id", district.getObjectId("_id").toString());
			            reports.put(jsonReport);
			            */
			    	
			    		System.out.println("Ci sono: "+district.toJson());
			    		
			    		//System.out.println("object name:"+district.getObjectId("_id"));
			    		//System.out.println("geometry: "+district.get("geometry"));
			    		org.bson.Document doc=(org.bson.Document) district.get("geometry");
			    		
			    		System.out.println("doc: "+doc.toJson());
			    		if(doc.get("type").equals("Polygon"))
			    		{
			    			
			    			GeometryData g=new Gson().fromJson(doc.toJson(), GeometryData.class);
				    		System.out.println("sono qui:\n"+g.toString());
		        	    	if(g.getType().equals("Polygon"))
		        	    	{
		        	    		
			    	    	    
					    		pl.setName(district.get("name").toString());
					    		pl.setPointList(g.getPoints());
					    		re.add(pl);
		        	    	}	
			    		}
	    	    	}
	    	
	    	    
			};
			
			
			
			collection.find().forEach(printBlock);
		
			
			DBMongoConnectionPool.close(client);
			
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		} catch (LocalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;

	}
	
	//Metodo per popolare il DB! Usalo una sola volta.
	@Override
	public void insertNeighborhoodsIntoPostgreSQL(Neighborhoods district) {
		
		 Connection con=PostgreSqlConnectionPool.PostgreSqlConnectionPool();
 	    ResultSet resultSet=null;
			try {
				
				String s="INSERT INTO neighborhoods (name, polygon) VALUES ('"+district.getName()+"',ST_GeometryFromText('POLYGON((";
				
				for(int i=0;i<district.getGeoemtry().getCoordinates()[0].length;i++)
		    	{
					if(district.getGeoemtry().getCoordinates()[0].length-1==i)
						s=s+ArrayUtils.toString(district.getGeoemtry().getCoordinates()[0][i][0])+" "+ArrayUtils.toString(district.getGeoemtry().getCoordinates()[0][i][1]);
					else
						s=s+ArrayUtils.toString(district.getGeoemtry().getCoordinates()[0][i][0])+" "+ArrayUtils.toString(district.getGeoemtry().getCoordinates()[0][i][1]+",");
				}
				s=s+"))'));";
				
				System.out.println("query:"+s);
				
				resultSet=PostgreSqlConnectionPool.runQuery(con,s);
				 	    
				
				PostgreSqlConnectionPool.close(con);
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LocalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
	}
	
	//Da console funziona
	public void NeighborhoodsPostgreSQL(String name) {
		
	    Connection con=PostgreSqlConnectionPool.PostgreSqlConnectionPool();
 	    ResultSet resultSet=null;
			try {
				
				//se il parser lo lanci una sola volta , non c'è bisogno che metti distinct
				String s="SELECT distinct name, ST_AsGeoJSON(polygon) as polygon FROM neighborhoods where name='Bedford'";
				
				
				resultSet=PostgreSqlConnectionPool.runQuery(con,s);
				 	    
				
				
				while(resultSet.next())
				{
					String ss=resultSet.getString("polygon");
					System.out.println("---"+ss);
					
					
        	    	
        	    	
        	    	GeometryData g=new Gson().fromJson(ss, GeometryData.class);
        	    	System.out.println("ci sono:"+g.toString());
				}
				
			
				
				PostgreSqlConnectionPool.close(con);
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LocalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	  
	}

	
	public Station getStartStationByNameMondoDB(String stationName) {
		
		Station station=null;
		try
		{
				MongoClient client = DBMongoConnectionPool.createMongoDBConnection();
				MongoDatabase db=DBMongoConnectionPool.selectDB(client);
				MongoCollection<org.bson.Document> collection=db.getCollection(Constants.mongoCollectionCitiBike);
				
				org.bson.Document doc=collection.find(eq("start station name",stationName)).first();
				
				station=new Station();
				station.setId(doc.get("start station id").toString());
				station.setLatitude(Double.parseDouble(doc.get("start station latitude").toString()));
				station.setLongitude(Double.parseDouble(doc.get("start station longitude").toString()));
				station.setName(stationName);
				
		    	//System.out.println("lat: "+doc.get("start station latitude").toString()+" long:"+doc.get("start station longitude").toString());
				
				
				DBMongoConnectionPool.close(client);
				
		}
		catch (UnknownHostException e)
		{
				e.printStackTrace();
		}
		catch (LocalException e)
		{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return station;
	}
	
	public boolean check(ArrayList<Station> stations, Station station)
	{
		for(int i=0;i<stations.size();i++)
		{
			if(stations.get(i).getName().equals(station.getName()))
				return true;
		}
		return false;
	}
		public ArrayList<Station> allStationMondoDB() {
		
		ArrayList<Station> stations=new ArrayList<Station>();
		try
		{
				MongoClient client = DBMongoConnectionPool.createMongoDBConnection();
				MongoDatabase db=DBMongoConnectionPool.selectDB(client);
				MongoCollection<org.bson.Document> collection=db.getCollection(Constants.mongoCollectionCitiBike);
				
				
				  try (MongoCursor<org.bson.Document> cur = collection.find().iterator()) {
			            while (cur.hasNext()) {

			                org.bson.Document doc = cur.next();
			                
			                Station station=new Station();
							station.setId(doc.get("start station id").toString());
							station.setLatitude(Double.parseDouble(doc.get("start station latitude").toString()));
							station.setLongitude(Double.parseDouble(doc.get("start station longitude").toString()));
							station.setName(doc.get("start station name").toString());
							
							if(!check(stations,station))
							{
								stations.add(station);
							}
			                /*
			                List list = new ArrayList(doc.values());
			                System.out.print(list.get(1));
			                System.out.print(": ");
			                System.out.println(list.get(2));
			                */
			            }
			        }
				
				
				DBMongoConnectionPool.close(client);
				
		}
		catch (UnknownHostException e)
		{
				e.printStackTrace();
		}
		catch (LocalException e)
		{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return stations;
	}
		
		public ArrayList<Restaurant> allRestaurants()
		{
			ArrayList<Restaurant> re=new ArrayList<Restaurant>();
			try
			{
				MongoClient client = DBMongoConnectionPool.createMongoDBConnection();
				MongoDatabase db=DBMongoConnectionPool.selectDB(client);
				MongoCollection<org.bson.Document> collection=db.getCollection("restaurants");
				collection.createIndex(Indexes.geo2dsphere("location.coordinates"));
				
				Block<org.bson.Document> printBlock = new Block<org.bson.Document>() {
				  
					public void apply(final org.bson.Document district) {
				    	
							Restaurant restau=new Restaurant();
							
							org.bson.Document doc=(org.bson.Document) district.get("location");
							List cc=(List) doc.get("coordinates");
							//estraggo la long o la lat --> controlla
							double longitude=Double.parseDouble(cc.get(0).toString());
							double latitude=Double.parseDouble(cc.get(1).toString());
							restau.setCoordinates(new Dot(longitude,latitude));
							restau.setName(district.get("name").toString());
							System.out.println("*"+restau.toString());
							re.add(restau);
					}
				};
				
				collection.find().forEach(printBlock);
				
				DBMongoConnectionPool.close(client);
			}
			catch (UnknownHostException e)
			{
				e.printStackTrace();
			}
			catch (LocalException e)
			{
				e.printStackTrace();
			}
			
			return re;
		}	

	public Station getEndStationByNameMondoDB(String stationName) {
		
		Station station=null;
		try
		{
				MongoClient client = DBMongoConnectionPool.createMongoDBConnection();
				MongoDatabase db=DBMongoConnectionPool.selectDB(client);
				MongoCollection<org.bson.Document> collection=db.getCollection(Constants.mongoCollectionCitiBike);
				
				org.bson.Document doc=collection.find(eq("end station name",stationName)).first();
				
				station=new Station();
				station.setId(doc.get("end station id").toString());
				station.setLatitude(Double.parseDouble(doc.get("end station latitude").toString()));
				station.setLongitude(Double.parseDouble(doc.get("end station longitude").toString()));
				station.setName(stationName);
				
		    	//System.out.println("lat: "+doc.get("start station latitude").toString()+" long:"+doc.get("start station longitude").toString());
				
				
				DBMongoConnectionPool.close(client);
				
		}
		catch (UnknownHostException e)
		{
				e.printStackTrace();
		}
		catch (LocalException e)
		{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return station;
	}
	
	public ArrayList<citiBike> longerTripBetweenStations(String startStation,String endStation)
	{
		ArrayList<citiBike> listCB=new ArrayList<citiBike>();
		citiBike cB=null;
		try
		{
				MongoClient client = DBMongoConnectionPool.createMongoDBConnection();
				MongoDatabase db=DBMongoConnectionPool.selectDB(client);
				MongoCollection<org.bson.Document> collection=db.getCollection(Constants.mongoCollectionCitiBike);
				
				cB=new citiBike();
				
				//org.bson.Document doc=collection.find(and(eq("start station name", "Exchange Place"),eq("end station name", "Essex Light Rail"))).sort(new org.bson.Document("triduration", 1)).first();
				
				org.bson.Document doc=collection.find(and(eq("start station name",startStation ),eq("end station name", endStation))).sort(new org.bson.Document("triduration", 1)).first();
				
				System.out.println("\n\nTripConMaggiorDurata"+doc.toJson()+"\n\n");
				
				cB.setEndStationId(doc.get("end station id").toString());
				cB.setEndStationLatitude(Double.parseDouble(doc.get("end station latitude").toString()));
				cB.setEndStationLongitude(Double.parseDouble(doc.get("end station longitude").toString()));
				cB.setTripduration(doc.get("tripduration").toString());
				cB.setStarttime(doc.get("starttime").toString());
				cB.setStoptime(doc.get("stoptime").toString());
				cB.setStartStationId(doc.get("start station id").toString());
				cB.setStartStationName(doc.get("start station name").toString());
				cB.setStartStationLatitude(Double.parseDouble(doc.get("start station latitude").toString()));
				cB.setStartStationLongitude(Double.parseDouble(doc.get("start station longitude").toString()));
				cB.setEndStationName(doc.get("end station name").toString());
				cB.setBikeId(doc.get("bikeid").toString());
				cB.setUserType(doc.get("usertype").toString());
				cB.setBirthYear(doc.get("birth year").toString());
				cB.setGender(doc.get("gender").toString());
				
				listCB.add(cB);
				System.out.println("citiBike:"+cB.toString());

		
				//collection.find(and(eq("start station name", "Exchange Place"),eq("end station name", "Essex Light Rail"))).sort(new org.bson.Document("triduration", 1)).forEach(printBlock);

				
				
		}
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	
		
		return listCB;
	}

}
