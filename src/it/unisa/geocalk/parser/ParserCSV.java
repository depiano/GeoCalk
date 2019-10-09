/*
 * Author: Antonio De Piano
 * Email: depianoantonio@gmail.com	
 * Web site: http://www.depiano.it
 * 
 * Script execution time: about an hour
 */
package it.unisa.geocalk.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.sun.jmx.snmp.Timestamp;


public class ParserCSV{

	 public static void main(String[] args) {
		 
		 	//Dataset
	        String csvFile = "/Users/depiano/Desktop/WorkspaceEE/GeoCalk/WebContent/dataset/JC-201902-citibike-tripdata.csv";
	       
	        //File csv generati dal file di origine!
	        String csvUser="/Users/depiano/Desktop/WorkspaceEE/GeoCalk/WebContent/dataset/user.csv";
	        String csvStation="/Users/depiano/Desktop/WorkspaceEE/GeoCalk/WebContent/dataset/station.csv";
	        String csvBike="/Users/depiano/Desktop/WorkspaceEE/GeoCalk/WebContent/dataset/bike.csv";
	        String csvTrip="/Users/depiano/Desktop/WorkspaceEE/GeoCalk/WebContent/dataset/trip.csv";
	        
	        BufferedReader br = null;
	        BufferedWriter bwUser=null;
	        BufferedWriter bwStation=null;
	        BufferedWriter bwTrip=null;
	        BufferedWriter bwBike=null;
	        FileWriter writerUser=null;
	        FileWriter writerStation=null;
	        FileWriter writerBike=null;
	        FileWriter writerTrip=null;
	        
	        String line = "";
	        String cvsSplitBy = ",";
	        
	        boolean header=true;
	        
	        String contentUser = "";
	        String contentBike = "";
	        String contentTrip = "";
	        String contentStation = "";

	        // If the file doesn't exists, create and write to it
			// If the file exists, truncate (remove all content) and write to it
	        try
	        {
	        	writerUser = new FileWriter(csvUser);
	        	writerBike = new FileWriter(csvBike);
	        	writerTrip = new FileWriter(csvTrip);
	        	writerStation = new FileWriter(csvStation);
	        	
	            bwUser = new BufferedWriter(writerUser);
	            bwBike = new BufferedWriter(writerBike);
	            bwTrip = new BufferedWriter(writerTrip);
	            bwStation = new BufferedWriter(writerStation);
	        
	            br = new BufferedReader(new FileReader(csvFile));
	            
	            System.out.println("Loading . . .\n");
	            
	            while ((line = br.readLine()) != null) {
	 
	                // use comma as separator
	                String[] result = line.split(cvsSplitBy);
	                
	                if(!header)
	                {
	                	Timestamp timestampUser = new Timestamp(System.currentTimeMillis());
	                	long UUIDuser = timestampUser.getDateTime();
	                	
	                	try {
							TimeUnit.MILLISECONDS.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                	
	                	Timestamp timestampTrip = new Timestamp(System.currentTimeMillis());
	                	long UUIDtrip = timestampTrip.getDateTime();
	                	
	                	try {
							TimeUnit.MILLISECONDS.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                	
	                	Timestamp timestampStartStation = new Timestamp(System.currentTimeMillis());
	                	long UUIDstartStation = timestampStartStation.getDateTime();
	                	
	                	try {
							TimeUnit.MILLISECONDS.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                	
	                	Timestamp timestampStopStation = new Timestamp(System.currentTimeMillis());
	                	long UUIDstopStation = timestampStopStation.getDateTime();
	                	
	                	try {
							TimeUnit.MILLISECONDS.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                	
	                	Timestamp timestampBike = new Timestamp(System.currentTimeMillis());
	                	long UUIDbike = timestampBike.getDateTime();
	                	
	                	try {
							TimeUnit.MILLISECONDS.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                	
	                	//bike : UUIDbike,bikeid
	                	contentBike=contentBike+UUIDbike+","+result[11]+"\n";
	                	
	                	//user : UUID,usertype,birtyear,gender
	                	contentUser=contentUser+UUIDuser+","+result[12]+","+result[13]+","+result[14]+"\n";
	                	
	                	//start station : UUID, id, station name, station lat, station long
	                	contentStation=contentStation+UUIDstartStation+","+result[3]+","+result[4]+","+result[5]+","+result[6]+"\n";
	                	
	                	//stop station : UUID, id, station name, station lat, station long
	                	contentStation=contentStation+UUIDstopStation+","+result[7]+","+result[8]+","+result[9]+","+result[10]+"\n";
	                	
	                	//trip : UUID, tripduration,starttime,stoptime,uuid station start,uuid station stop, uuid bike, uuiduser
	                	contentTrip=contentTrip+UUIDtrip+","+result[0]+","+result[1]+","+result[2]+","+UUIDstartStation+","+UUIDstopStation+","+
	                				UUIDbike+","+UUIDuser+"\n";
	                	
	                }
	                else
	                	header=false;
	            }
	            
	            System.out.println("Generated file user.csv\n");
	            bwUser.write(contentUser);
	            System.out.println("Generated file trip.csv\n");
	            bwTrip.write(contentTrip);
	            System.out.println("Generated file station.csv\n");
	            bwStation.write(contentStation);
	            System.out.println("Generated file bike.csv\nFinished!");
	            bwBike.write(contentBike);
	 
	        }
	        catch (FileNotFoundException e)
	        {
	            e.printStackTrace();
	        }
	        catch (IOException e)
	        {
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
	            if (bwUser != null)
	            {
	                try
	                {
	                    bwUser.close();
	                }catch (IOException e)
	                {
	                    e.printStackTrace();
	                }
	            }
	            if (bwTrip != null)
	            {
	                try
	                {
	                    bwTrip.close();
	                }
	                catch (IOException e)
	                {
	                    e.printStackTrace();
	                }
	            }
	            if (bwBike != null)
	            {
	                try
	                {
	                    bwBike.close();
	                }
	                catch (IOException e)
	                {
	                    e.printStackTrace();
	                }
	            }
	            if (bwStation != null)
	            {
	                try
	                {
	                    bwStation.close();
	                } 
	                catch (IOException e)
	                {
	                    e.printStackTrace();
	                }
	            }
	        }
	 
	    }
}



