package com.packt.cardb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardbApplication {

    private static final Logger logger =
            LoggerFactory.getLogger(CardbApplication.class);

	public static void main(String[] args) {

        SpringApplication.run(CardbApplication.class, args);
        logger.info("Application started");
	}

}
