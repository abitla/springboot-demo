package com.loyer.example.controller;

import com.loyer.example.entity.Result;
import com.loyer.example.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kuangq
 * @projectName producer
 * @title TestController
 * @description WebMvc模式接口、调用远程服务
 * @date 2019-10-17 9:57
 */
@RestController
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    DemoService demoService;

    /**
     * @param
     * @return com.loyer.example.entity.Result
     * @author kuangq
     * @description 远程first-demo服务接口调用
     * @date 2019-10-17 14:00
     */
    @GetMapping("showVersion")
    public Result showVersion() {
        //获取注册中心注册服务的实例
        List<ServiceInstance> listServiceInstance = discoveryClient.getInstances("FIRST-DEMO");
        listServiceInstance.forEach(serviceInstance -> {
            logger.info("host{}；port{}", serviceInstance.getHost(), serviceInstance.getPort());
        });
        return demoService.showVersion();
    }


}
