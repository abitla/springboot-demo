package com.loyer.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author kuangq
 * @projectName example
 * @title CorsFilterConfig
 * @description 全局跨域过滤器
 * @date 2019-08-26 10:20
 */
@Configuration
public class CorsFilterConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setMaxAge(3600L); //设置缓存跨域访问信息时长
        corsConfiguration.setAllowCredentials(true); //设置启用cookie
        corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL); //设置允许跨域请求的域名
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL); //设置允许跨域的请求头
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL); //设置允许跨域的请求类型
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}