package com.loyer.example.entity;

/**
 * @author kuangq
 * @projectName example
 * @title Result
 * @description API响应实体类
 * @date 2019-08-01 9:40
 */
public class Result<T> {

    private boolean flag;
    private int code;
    private String msg;
    private T data;

    public boolean isSucc() {
        return flag;
    }

    public void setSucc(boolean flag) {
        this.flag = flag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" + "flag=" + flag + ", code=" + code + ", msg='" + msg + ", data=" + data + '}';
    }
}
