package com.loyer.example.persistence.oracle;

import com.loyer.example.entity.OracleResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author kuangq
 * @projectName example
 * @title OracleDemoMapper
 * @description 测试oracleMapper
 * @date 2019-08-01 11:02
 */
@Mapper
public interface OracleDemoMapper {

    List<OracleResource> queryDataBase() throws Exception;

}
