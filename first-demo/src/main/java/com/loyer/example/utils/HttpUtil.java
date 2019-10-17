package com.loyer.example.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author kuangq
 * @projectName example
 * @title HttpUtil
 * @description Http请求工具类
 * @date 2019-08-28 17:46
 */
public class HttpUtil {

    private static RestTemplate restTemplate = ContextUtil.getBean("restTemplate");

    private static HttpHeaders httpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept-Charset", "utf-8");
        httpHeaders.set("Content-type", "text/xml; charset=utf-8");
        httpHeaders.set("Content-type", "application/json; charset=utf-8");
        return httpHeaders;
    }

    public static <T> T doGet(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }

    public static <T> T doGet(String url, Class<T> responseType, Object... uriVariables) {
        return restTemplate.getForObject(url, responseType, uriVariables);
    }

    public static <T> T doGet(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        return restTemplate.getForObject(url, responseType, uriVariables);
    }

    public static <T> T doPost(String url, String str, Class<T> responseType) {
        HttpEntity<String> request = new HttpEntity<>(str, httpHeaders());
        return restTemplate.postForObject(url, request, responseType);
    }

    public static <T> T doPost(String url, String str, Class<T> responseType, Object... uriVariables) {
        HttpEntity<String> request = new HttpEntity<>(str, httpHeaders());
        return restTemplate.postForObject(url, request, responseType, uriVariables);
    }

    public static <T> T doPost(String url, String str, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<String> request = new HttpEntity<>(str, httpHeaders());
        return restTemplate.postForObject(url, request, responseType, uriVariables);
    }
}
