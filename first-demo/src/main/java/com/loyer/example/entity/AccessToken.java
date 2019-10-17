package com.loyer.example.entity;

/**
 * @author kuangq
 * @projectName example
 * @title AccessToken
 * @description 获取access_token响应实体类
 * @date 2019-08-28 17:57
 */
public class AccessToken extends BaseMsg {

    private String access_token;

    private Integer expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
