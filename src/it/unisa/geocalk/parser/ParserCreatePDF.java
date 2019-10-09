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

public class ParserCreatePDF {
	 public static void main(String[] args) {
		 
		 	//Dataset
	        String input = "/Users/depiano/Desktop/import.txt";
	       
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
	            	
	            	String[] result = line.split("=>");
	            	
	            	//String replaceString=line.replace("</h6></label></td></tr>","\\n");
	            	
	            	contentOutput=contentOutput+"$('#"+result[0]+"').val("+result[0]+");\n";
	            	
	            }
	            
	            System.out.println("Generated file output.txt\n\n");
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

