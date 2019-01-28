package com.mapr.server.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.mapr"})
public class MaprSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaprSpringbootApplication.class, args);
	}

}

