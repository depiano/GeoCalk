package it.unisa.geocalk.connection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;

import java.net.UnknownHostException;

import it.unisa.geocalk.exception.LocalException;
import it.unisa.geocalk.utils.Constants;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;



public class DBMongoConnectionPool {
	

	public static synchronized MongoClient createMongoDBConnection() throws UnknownHostException {
		
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
	    
		return mongoClient;
	      
	}
	
	public static synchronized MongoDatabase selectDB(MongoClient mongoClient)
	{
		return mongoClient.getDatabase(Constants.mongoDb);
	}
	
	public static synchronized void close(MongoClient mongoClient) throws LocalException
	{
		if(mongoClient!=null)
			mongoClient.close();
		else
			throw new LocalException("MongoDB is down!");
	}
	
	
	
}



