package mapr.com.maprdb.util;

import java.util.Random;

import org.ojai.Document;
import org.ojai.store.Connection;
import org.ojai.store.DocumentStore;
import org.ojai.store.DriverManager;

public class MapRDBUtil {

//	private static Connection connection = null;
//	static {
//		connection = DriverManager.getConnection("ojai:mapr:");
//	}
	
	public static boolean writeMsgToMapRDB(final String msg, final String tableName) {

		// Create Document from json Msg
		Connection connection = null;

		if (connection == null) {
			connection = DriverManager.getConnection("ojai:mapr:");
		}

		DocumentStore store = connection.getStore(tableName);

		Document userDocument = connection.newDocument(msg);
		userDocument.setId(Integer.toString(
				userDocument.getString("__Id") == null ? new Random().nextInt(100000) : userDocument.getInt("__Id")));
		store.insertOrReplace(userDocument);
		store.close();
		connection.close();
		return Boolean.TRUE;
	}
	
	public static String readJsonFromID(final String id,final String tableName) 
	{
		Connection connection = null;
		if (connection == null) {
			connection = DriverManager.getConnection("ojai:mapr:");
		}
		
		String data = null;

		System.out.println("Id:"+id);
	    // Get an instance of OJAI DocumentStore
	    final DocumentStore store = connection.getStore(tableName);

	    // fetch the OJAI Document by its '_id' field
	    final Document document = store.findById(id);

	    // Print the OJAI Document
	    data=document.asJsonString();      
	    // Close this instance of OJAI DocumentStore
	    store.close();
	    connection.close();
	    System.out.println("data:"+data);
		
		return data;
	}
	
	public static void main(String[] args) {
		MapRDBUtil.readJsonFromID(args[0], args[1]);
	}
	
}
