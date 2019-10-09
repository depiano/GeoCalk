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

public class WriteFileOFVDDocument {
	
	 public static void main(String[] args) {
		 
		  //File csv generati dal file di origine!
	        String output="/Users/depiano/Desktop/outputDocument.txt";
	       
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
	        
	            
	            System.out.println("Loading . . .\n");
	            
	            int i=1;
	            while (i<=116) {
	 
	                
	                	contentOutput=contentOutput+"s.append(\"answer"+i+"\",question.getAnswer"+i+"());\n";
	                	i++;
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
	       
	    }
}

