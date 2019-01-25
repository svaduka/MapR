package com.mapr.streams.maprdb.consumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.ojai.Document;
import org.ojai.store.Connection;
import org.ojai.store.DocumentStore;
import org.ojai.store.DriverManager;

import com.mapr.streams.util.MapRStreamUtil;

//TODO Do logging
public class MapRDBConsumer {
	
	private static Connection connection =null;
	static {
		connection = DriverManager.getConnection("ojai:mapr:");
	}
	
	public void doConsumerActivity(final String topicName,final String tableName, String consumerPropertyFile) throws IOException, InterruptedException 
	{
		Consumer<String, String> consumer = MapRStreamUtil.createConsumer(consumerPropertyFile);
		consumer.subscribe(Arrays.asList(topicName));
		while (true) {
			
			ConsumerRecords<String, String> records = consumer.poll(100);
			
			if (records.count() != 0) {
				
				for (ConsumerRecord<String, String> record : records) {
					final String jsonString = record.value();
					System.out.println("-----JSON STRING -----");
					System.out.println(jsonString);
					writeMsgToMapRDB(jsonString, tableName);
				}
				
			}
		}
		
	}
	
	public boolean writeMsgToMapRDB(final String msg, final String tableName) {
		
		//Create Document from json Msg
		
		if(connection == null) {
			connection = DriverManager.getConnection("ojai:mapr:");
		}
		
		DocumentStore store = connection.getStore(tableName);
		
		Document userDocument = connection.newDocument(msg);
		userDocument.setId( Integer.toString(userDocument.getString("__Id")==null?new Random().nextInt(100000):userDocument.getInt("__Id")));
		store.insertOrReplace(userDocument);
		
		return Boolean.TRUE;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		if(args.length < 2) {
			System.out.println("Java Usage: MapRDBConsumer <TOPIC-NAME> <MAPRDB-TABLE-NAME> [CONSUMER-PROPERTIES-FILE]");
			return;
		}
		
		final String topicName = args[0];
		System.out.println("Current Topic:"+topicName);
		
		final String tableName = args[1];
		System.out.println("Populating table:"+tableName);
		
		String consumerPropertyFile=null;
		if(args.length == 3) {
			consumerPropertyFile=args[2];
			System.out.println("Using consumerProperty File:"+consumerPropertyFile);
			
			MapRDBConsumer mapRDBConsumer = new MapRDBConsumer();
			mapRDBConsumer.doConsumerActivity(topicName, tableName, consumerPropertyFile);
		}
		
		
	}
}
