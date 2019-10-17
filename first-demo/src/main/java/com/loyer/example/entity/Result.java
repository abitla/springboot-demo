package com.loyer.example.entity;

import com.loyer.example.enums.HintEnum;

/**
 * @author kuangq
 * @projectName example
 * @title Result
 * @description API响应实体类
 * @date 2019-08-01 9:40
 */
public class Result<T> {

    private static final int success = 1000;
    private static final int failure = 1001;
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

    public Result(boolean flag, int code) {
        this.flag = flag;
        this.code = code;
    }

    public Result(boolean flag, int code, String msg) {
        this.flag = flag;
        this.code = code;
        this.msg = msg;
    }

    public Result(boolean flag, int code, String msg, T data) {
        this.flag = flag;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(String msg) {
        return new Result(true, success, msg, null);
    }

    public static Result success(int code, String msg) {
        return new Result(true, code, msg, null);
    }

    public static Result success(int code, String msg, Object data) {
        return new Result(true, code, msg, data);
    }

    public static Result failure(String msg) {
        return new Result(false, failure, msg, null);
    }

    public static Result failure(int code, String msg) {
        return new Result(false, code, msg, null);
    }

    public static Result failure(int code, String msg, Object data) {
        return new Result(false, code, msg, data);
    }

    public static Result hintEnum(HintEnum hintEnum) {
        return new Result(hintEnum == HintEnum.HINT_1000, hintEnum.getCode(), hintEnum.getMsg(), null);
    }

    public static Result hintEnum(HintEnum hintEnum, Object data) {
        return new Result(hintEnum == HintEnum.HINT_1000, hintEnum.getCode(), hintEnum.getMsg(), data);
    }
}
