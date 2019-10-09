package it.unisa.geocalk.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.sun.jmx.snmp.Timestamp;

public class ParserHospital {
	
	 public static void main(String[] args) {
		 
		 	//Dataset
	        String input = "/Users/depiano/Desktop/input.txt";
	       
	        //File csv generati dal file di origine!
	        String output="/Users/depiano/Desktop/output.txt";
	       
	        
	        BufferedReader br = null;
	        BufferedWriter bwOutput=null;
	        FileWriter writerOutput=null;
	        
	        String line = "";
	        
	        String contentOutput = "";

	        // If the file doesn't exists, create and write to it
			// If the file exists, truncate (remove all content) and write to it
	        try
	        {
	        	writerOutput = new FileWriter(output);
	        	
	            bwOutput = new BufferedWriter(writerOutput);
	        
	            br = new BufferedReader(new FileReader(input));
	            
	            System.out.println("Loading . . .\n");
	            
	            while ((line = br.readLine()) != null) {
	 
	                System.out.println(line);
	                	contentOutput=contentOutput+"<option value=\""+line+"\">"+line+"</option>\n";
	                	
	            }
	            
	            System.out.println("Generated file output.txt\n");
	            System.out.println(contentOutput);
	            bwOutput.write(contentOutput);
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
	            
	        }
	 
	    }
}

