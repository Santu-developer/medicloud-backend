package com.medicloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicloudBackendApplication {

	private static final Logger log = LoggerFactory.getLogger(MedicloudBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MedicloudBackendApplication.class, args);
		log.info("MediCloud Backend started successfully");
	}
}
