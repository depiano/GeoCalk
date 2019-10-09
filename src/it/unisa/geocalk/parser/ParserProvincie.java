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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.sun.jmx.snmp.Timestamp;


public class ParserProvincie{
	
	
	public static void listFilesForFolder(final File folder)
	{
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            
	            
	            
	            BufferedWriter writer = null;
				try {
					writer = new BufferedWriter(new FileWriter("/Users/depiano/Desktop/file.csv", true));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	            try {
	            	String file=fileEntry.getParent();
	            	String array_str[]=file.split("/");
	            	
	            	String file1=fileEntry.getName();
	            	
	            	System.out.println(array_str[array_str.length-1]+";"+file1+"\n");
					writer.write(array_str[array_str.length-1]+";"+file1+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	}

	 public static void main(String[] args) {
		 
		 final File folder = new File("/Users/depiano/Desktop/OFVD");
			listFilesForFolder(folder);

	    }
}



