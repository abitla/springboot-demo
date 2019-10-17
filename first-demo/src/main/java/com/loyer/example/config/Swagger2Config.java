package com.loyer.example.config;

import com.loyer.example.controller.DemoController;
import com.loyer.example.utils.DESUtil;
import com.loyer.example.utils.GeneralUtil;
import com.loyer.example.utils.MD5Util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kuangq
 * @projectName example
 * @title SwaggerConfig
 * @description Swagger2可视化接口测试（访问路径：/swagger-ui.html）
 * @date 2019-10-14 14:36
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * @param
     * @return springfox.documentation.spring.web.plugins.Docket
     * @author kuangq
     * @description docket配置
     * @date 2019-10-14 16:59
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .globalOperationParameters(getHeaders())
                .apiInfo(getApiInfo())
                ;
    }

    /**
     * @param
     * @return java.util.List<springfox.documentation.service.Parameter>
     * @author kuangq
     * @description 创建信息头
     * @date 2019-10-14 16:57
     */
    private List<Parameter> getHeaders() {
        String token = GeneralUtil.getUUID();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature = MD5Util.encrypt(DESUtil.encrypt(token + "&" + timestamp));
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.modelRef(new ModelRef("string")).parameterType("header").required(true);
        return new ArrayList<Parameter>() {{
            add(parameterBuilder.name("token").description("令牌").defaultValue(token).build());
            add(parameterBuilder.name("timestamp").description("时间戳").defaultValue(timestamp).build());
            add(parameterBuilder.name("signature").description("签名").defaultValue(signature).build());
        }};
    }

    /**
     * @param
     * @return springfox.documentation.service.ApiInfo
     * @author kuangq
     * @description 创建Api接口信息
     * @date 2019-10-14 16:57
     */
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .version(DemoController.version)
                .title("api swagger document")
                .description("example api")
                .build();
    }
}

