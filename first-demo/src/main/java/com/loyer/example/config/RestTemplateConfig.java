package com.loyer.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author kuangq
 * @projectName example
 * @title RestTemplateConfig
 * @description RestTemplate客户端
 * @date 2019-08-28 20:53
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setReadTimeout(5000);
        simpleClientHttpRequestFactory.setConnectTimeout(5000);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }
}