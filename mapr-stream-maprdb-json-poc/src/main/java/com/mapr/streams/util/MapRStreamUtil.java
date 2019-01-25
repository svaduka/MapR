package com.mapr.streams.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.common.io.Resources;

public class MapRStreamUtil {
	/**
	 * 
	 * @param producerProperties
	 * @return
	 * @throws IOException
	 * 
	 *             User have to provide consumer and producer properties or default
	 *             to producer.props
	 */
	public static KafkaProducer<String, String> createProducer(String producerProperties) throws IOException {
		KafkaProducer<String, String> producer;
		if (StringUtils.isEmpty(producerProperties)) {
			producerProperties = "producer.props";
		}
		try (InputStream props = Resources.getResource(producerProperties).openStream()) {
			Properties properties = new Properties();
			properties.load(props);
			producer = new KafkaProducer<>(properties);
		}
		return producer;
	}

	public static boolean closeProducer(KafkaProducer<String, String> producer) {
		if (producer != null) {
			producer.close();
		}
		producer = null; // Garbage
		return Boolean.TRUE;
	}

	public static boolean sendRecord(KafkaProducer<String, String> producer, final String topicName,
			final String data) {
		System.out.println("data producing");
		producer.send(new ProducerRecord<String, String>(topicName, data));
		producer.flush();
		return Boolean.TRUE;
	}

	public static KafkaConsumer<String, String> createConsumer(String consumerProperties) throws IOException {

		KafkaConsumer<String, String> consumer;
		
		if (StringUtils.isEmpty(consumerProperties)) {
			consumerProperties = "consumer.props";
		}
		System.out.println("Using Below consumer properties:"+consumerProperties);
		try (InputStream props = Resources.getResource("consumer.props").openStream()) {
			Properties properties = new Properties();
			System.out.println("Loading");
			properties.load(props);
			consumer = new KafkaConsumer<String, String>(properties);
		}

		return consumer;
	}
}
