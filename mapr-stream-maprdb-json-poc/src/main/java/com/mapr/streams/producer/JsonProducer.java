package com.mapr.streams.producer;

import java.io.IOException;

import org.apache.kafka.clients.producer.KafkaProducer;

import com.mapr.streams.util.FileUtil;
import com.mapr.streams.util.MapRStreamUtil;

public class JsonProducer {
	
	
	public void doProducerActivity(final String topicName,final String inputFileLocation, int producerCount, String producerPropertyFile) throws IOException
	{
		final String jsonString  = FileUtil.mkString(inputFileLocation);
		System.out.println("producing json string:"+jsonString);
		
		KafkaProducer<String, String> producer = MapRStreamUtil.createProducer(producerPropertyFile);
		
		for(int i=0;i<producerCount;i++) {
			MapRStreamUtil.sendRecord(producer, topicName, jsonString);
		}
		
	}
	public static void main(String[] args) throws IOException {
		
		if(args.length < 2) {
			System.out.println("Java Usage: JsonProducer<TOPIC-NAME> <INPUT-LOCATION> [produceCount/1] [producer.properties]");
			return;
		}
		final String topicName = args[0];
		System.out.println("Current Topic:"+topicName);
		
		final String inputFileLocation = args[1];
		System.out.println("Reading json file location:"+inputFileLocation);
		
		int produceCount = 1;
		
		if(args.length >= 3) {
			produceCount=Integer.parseInt(args[2]);
		}
		
		String producerPropertyFile=null;
		if(args.length >= 4) {
			producerPropertyFile=args[3];
			System.out.println("Using producerProperty File:"+producerPropertyFile);
		}
		
		
		JsonProducer jsonProducer = new JsonProducer();
		jsonProducer.doProducerActivity(topicName, inputFileLocation, produceCount, producerPropertyFile);
	}

}
