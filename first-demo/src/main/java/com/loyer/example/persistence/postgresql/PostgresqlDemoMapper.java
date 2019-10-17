package com.loyer.example.persistence.postgresql;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kuangq
 * @projectName example
 * @title AliyunDemoMapper
 * @description 测试postgresqlMapper
 * @date 2019-08-01 11:02
 */
public interface PostgresqlDemoMapper {

    List<HashMap> queryDataBase() throws Exception;

    int saveFile(@Param("fileInfo") List<Map> params) throws Exception;

    int deleteFile(Map params) throws Exception;

    List<HashMap> queryFileInfo(Map params) throws Exception;
}
