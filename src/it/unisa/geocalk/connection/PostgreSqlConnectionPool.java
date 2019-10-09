package it.unisa.geocalk.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mongodb.MongoClient;

import it.unisa.geocalk.exception.LocalException;
import it.unisa.geocalk.utils.Constants;

public class PostgreSqlConnectionPool
{
	public static synchronized Connection PostgreSqlConnectionPool()
	{
		Connection connection=null;
		try
		{
			connection = DriverManager.getConnection("jdbc:postgresql://"+Constants.postgresqlIp+":"+Constants.postgresqlPort+"/"+Constants.postgresqlDb, Constants.postgresqlLogin, Constants.postgresqlPwd);

		}
		catch (SQLException e)
		{
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public static synchronized void close(Connection postgresConnection) throws LocalException
	{
		if(postgresConnection!=null)
			try {
				postgresConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		else
			throw new LocalException("PostgreSQL is down!");
	}
	
	public static synchronized ResultSet runQuery(Connection postgresConnection, String query) throws SQLException
	{
	
		ResultSet resultSet=null;
		Statement statement = postgresConnection.createStatement();
		resultSet = statement.executeQuery(query);
		
		return resultSet;
	}
	
}
