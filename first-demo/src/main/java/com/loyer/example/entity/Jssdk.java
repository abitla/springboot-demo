package com.loyer.example.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kuangq
 * @projectName example
 * @title Jssdk
 * @description Jssdk实体类
 * @date 2019-08-28 16:03
 */
@Configuration
@ConfigurationProperties(prefix = "jssdk")
public class Jssdk {

    private String url;

    private String appID;

    private String appSecret;

    private String getTokenUrl;

    private String getTicketUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getGetTokenUrl() {
        return getTokenUrl;
    }

    public void setGetTokenUrl(String getTokenUrl) {
        this.getTokenUrl = getTokenUrl;
    }

    public String getGetTicketUrl() {
        return getTicketUrl;
    }

    public void setGetTicketUrl(String getTicketUrl) {
        this.getTicketUrl = getTicketUrl;
    }
}
