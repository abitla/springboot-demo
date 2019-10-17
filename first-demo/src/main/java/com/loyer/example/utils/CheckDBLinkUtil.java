package com.loyer.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * @author kuangq
 * @projectName example
 * @title CheckDBLinkUtil
 * @description 检测数据库连接
 * @date 2019-08-30 14:24
 */
public class CheckDBLinkUtil {

    private static final Logger logger = LoggerFactory.getLogger(CheckDBLinkUtil.class);

    public static boolean postgresqlDataSource() {
        try {
            DataSource dataSource = (DataSource) ContextUtil.getApplicationContext().getBean("postgresqlDataSource");
            dataSource.getConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean mysqlDataSource() {
        try {
            DataSource dataSource = (DataSource) ContextUtil.getApplicationContext().getBean("mysqlDataSource");
            dataSource.getConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean oracleDataSource() {
        try {
            DataSource dataSource = (DataSource) ContextUtil.getApplicationContext().getBean("oracleDataSource");
            dataSource.getConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
