package com.loyer.example.persistence.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kuangq
 * @projectName example
 * @title MysqlDemoMapper
 * @description 测试mysqlMapper
 * @date 2019-08-01 11:02
 */
public interface MysqlDemoMapper {

    List<HashMap> queryDataBase() throws Exception;

    List<HashMap> queryUserInfo(Map params) throws Exception;
}
