package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class SecondDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(SecondDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SecondDemoApplication.class, args);
        logger.info("SecondDemoApplication start ...");
    }

}
