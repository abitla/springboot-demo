package com.loyer.example.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author kuangq
 * @projectName example
 * @title OracleDataSourceConfig
 * @description 配置oralce数据源
 * @date 2019-08-08 15:15
 */
@Configuration //SpringBoot启动将该类作为配置类，同配置文件一起加载
@MapperScan(basePackages = "com.loyer.example.persistence.oracle", sqlSessionFactoryRef = "oracleSqlSessionFactory") //扫描mapper包
public class OracleDataSourceConfig {

    private final String locationPattern = "classpath:com/loyer/example/persistence/oracle/mapper/*.xml"; //扫描xml文件路径

    @Bean(name = "oracleDataSource") //将该实体注入到IOC容器中
    @ConfigurationProperties(prefix = "spring.datasource.oracle") //读取配置文件中的数据源进行创建
    public DataSource buildDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oracleSqlSessionFactory")
    public SqlSessionFactory buildSqlSessionFactory(@Qualifier("oracleDataSource") DataSource dataSource) throws Exception { //指定注入数据源名称
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(locationPattern));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(); //配置_符号改驼峰式命名
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactoryBean.setConfiguration(configuration);
        return sessionFactoryBean.getObject();
    }
}