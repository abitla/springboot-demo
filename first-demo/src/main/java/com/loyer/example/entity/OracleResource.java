package com.loyer.example.entity;

/**
 * @author kuangq
 * @projectName example
 * @title OracleResource
 * @description oracle数据库资源
 * @date 2019-08-01 17:08
 */
public class OracleResource {

    private String objectType;

    private Integer objectCount;

    public OracleResource() {
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Integer getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(Integer objectCount) {
        this.objectCount = objectCount;
    }
}

