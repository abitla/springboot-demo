package com.loyer.example.entity;

/**
 * @author kuangq
 * @projectName example
 * @title BaseMsg
 * @description jssdk响应父类
 * @date 2019-08-28 22:50
 */
public class BaseMsg {

    private Integer errcode;

    private String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
