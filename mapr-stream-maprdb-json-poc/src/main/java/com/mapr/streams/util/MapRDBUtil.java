package com.mapr.streams.util;

import com.mapr.db.MapRDB;
import com.mapr.db.Table;

public class MapRDBUtil {

	 private static Table getTable(String tablePath) {

		    if ( ! MapRDB.tableExists(tablePath)) {
		      return MapRDB.createTable(tablePath);
		    } else {
		      return MapRDB.getTable(tablePath);
		    }

		  }
}
