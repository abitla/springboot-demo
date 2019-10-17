package com;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableEurekaClient
@EnableScheduling
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class, SecurityAutoConfiguration.class})
public class FirstDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(FirstDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FirstDemoApplication.class, args);
        logger.info("FirstDemoApplication start ...");
    }
}
