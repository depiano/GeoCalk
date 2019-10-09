package it.unisa.geocalk.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Constants {
	public static String appName = "GeoCalk";
	public static final String appLocalUrl = "http://localhost:8080/" + appName;
	

	public static String postgresqlIp = "localhost";
	public static String postgresqlPort = "5432";
	public static String postgresqlDb = "citibike";
	public static String postgresqlLogin = "postgres";
	public static String postgresqlPwd = "root";
	
	public static String mongoIp="localhost";
	public static String mongoPort="27017";
	public static String mongoDb="test";
	public static String mongoCollectionNeighborhoods="neighborhoods";
	public static String mongoCollectionRestaurants="restaurants";
	public static String mongoCollectionCitiBike="citibike";
	
	/*
	
	public static String postgresqlIp = "localhost";
	public static String postgresqlPort = "5432";
	public static String postgresqlDb = "test";
	public static String postgresqlLogin = "postgres";
	public static String postgresqlPwd = "root";
	
	public static String mongoIp="localhost";
	public static String mongoPort="27017";
	public static String mongoDb="test";
	public static String mongoCollectionNeighborhoods="neighborhoods";
	public static String mongoCollectionCitiBike="citibike";
	*/
	
}