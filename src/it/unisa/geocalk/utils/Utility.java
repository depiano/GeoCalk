package it.unisa.geocalk.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class Utility {
	public static Logger logger = Logger.getLogger(Constants.appName);
	
	public static void info(String message) {
		logger.info(message);
	}
	
	public static void severe(String message) {
		logger.severe(message);
	}	
	
	public static String encode(String text) {
		String encoded = "";

		if (text != null) {
			try {
				encoded = URLEncoder.encode(text, java.nio.charset.StandardCharsets.UTF_8.toString());
			} catch (UnsupportedEncodingException e) {
			}
		}

		return encoded;
	}
	
	public static void sleep(long delay)
	{
		if(delay <= 0) return;
		try
		{
			Thread.sleep(delay);
		}
		catch(InterruptedException e) {}		
	}	
}
