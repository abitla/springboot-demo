package com.loyer.example.exception;

import com.loyer.example.enums.HintEnum;

/**
 * @author kuangq
 * @projectName example
 * @title BusinessException
 * @description 自定义业务异常
 * @date 2019-07-19 9:21
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    private String msg;

    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BusinessException(String msg, Object data) {
        this.code = -1;
        this.msg = msg;
        this.data = data;
    }

    public BusinessException(HintEnum hintEnum) {
        this.code = hintEnum.getCode();
        this.msg = hintEnum.getMsg();
    }

    public BusinessException(HintEnum hintEnum, Object data) {
        this.code = hintEnum.getCode();
        this.msg = hintEnum.getMsg();
        this.data = data;
    }
}
