package com.cloud.vijay.health_check;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class HealthCheckApiApplication {

    private static final Logger LOGGER = LogManager.getLogger(HealthCheckApiApplication.class);
    public static void main(String[] args) {
        LOGGER.info("Debug message");
        SpringApplication.run(HealthCheckApiApplication.class, args);
    }

}
