package it.unisa.geocalk.parser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import it.unisa.geocalk.connection.PostgreSqlConnectionPool;
import it.unisa.geocalk.exception.LocalException;
import it.unisa.geocalk.model.Geometry;
import it.unisa.geocalk.model.GeometryData;
import it.unisa.geocalk.model.Neighborhoods;
import it.unisa.geocalk.model.dao.NeighborhoodsDao;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.jmx.snmp.Timestamp;

public class ParserGeoJson {
    public static void main(String[] args) throws Exception {
    	
    	
    	String jsonFile = "/Users/depiano/Desktop/WorkspaceEE/GeoCalk/WebContent/dataset/neighborhoods.json";
        BufferedReader br = null;

        String line = "";

    	try
    	{
    		br = new BufferedReader(new FileReader(jsonFile));
    		
    		System.out.println("Loading . . .\n");
    	  
    	    while ((line = br.readLine()) != null) {
    	   	 
    	    	if(line.indexOf("\"type\":\"Polygon\"")!=-1)
    	    	{
    	    		GeometryData gg=new GeometryData();
        	    	
        	    	
        	    	Geometry g=new Gson().fromJson(line, Geometry.class);
        	    	if(g.getGeometry().getType().equals("Polygon"))
        	    	{
    	    	    	gg.setCoordinates(g.getGeometry().getCoordinates());
    	    	    	gg.setType(g.getGeometry().getType());
    	    	    	
    	    	    	Neighborhoods district=new Neighborhoods();
    	    	    	district.setGeoemtry(gg);
    	    	    	
    	    	    	JSONObject obs=new JSONObject(line);
    		    	        
    	    	    	district.setName((String)obs.get("name"));
    	
    	    	    	NeighborhoodsDao districtDAO=new NeighborhoodsDao();
    	    	    	districtDAO.insertNeighborhoodsIntoPostgreSQL(district);
        	    	}
    	    	}
    	      
    	    }
    	    
    	    System.out.println("The Neighborhoods table has been populated\nFinished!");
        
            
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}
    	finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
     
   }
}


