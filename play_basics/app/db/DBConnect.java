package db;

import java.net.UnknownHostException;



import com.mongodb.DB;
import com.mongodb.MongoClient;



/**
 * Connection for MongoDB
 * @author mike
 *
 */
public class DBConnect {
	/**
	 * name of the mongodb collection
	 */
	public static final String COLLECTION_THINGS = "things";

	private static MongoClient mongo;
	private static DB db;
	
	/**
	 * get mondo DB 
	 * @return
	 */
	public static DB getDB(){
		if(null == db){
			try {
				mongo = new MongoClient( "localhost" , 27017 );
			} catch (UnknownHostException e) {
				throw new RuntimeException("Failed to connect to db", e);
			}
			db = mongo.getDB("play_basics");
		}
		
		return db;
	}
	
}
