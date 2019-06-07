

import java.util.concurrent.ConcurrentHashMap;

import org.ojai.store.Connection;
import org.ojai.store.DocumentStore;
import org.ojai.store.DriverManager;

public class AmwayDBUtil {

    private static Connection connection;

    private static ConcurrentHashMap<String, DocumentStore> storeHashMap = new ConcurrentHashMap<String, DocumentStore>();

	/*
	 * static { connection = DriverManager.getConnection("ojai:mapr:"); }
	 */
    public static Connection getConnection() {

        if(connection == null){
            connection = DriverManager.getConnection("ojai:mapr:");
        }
        return connection;
    }

    public static DocumentStore getAmwayDocumentStore(String tablePath){

		if (!storeHashMap.containsKey(tablePath)) {
			storeHashMap.putIfAbsent(tablePath, getConnection().getStore(tablePath));
		}
		return storeHashMap.get(tablePath);
    }


}
