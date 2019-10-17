package com.loyer.example.entity;

/**
 * @author kuangq
 * @projectName example
 * @title ApiTicket
 * @description 获取api_ticket响应实体类
 * @date 2019-08-28 22:50
 */
public class ApiTicket extends BaseMsg {

    private String ticket;

    private Integer expires_in;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
