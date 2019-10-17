package com.loyer.example.service;

import com.loyer.example.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author kuangq
 * @projectName producer
 * @title DemoService
 * @description FIRST-DEMO服务接口绑定
 * @date 2019-10-17 11:01
 */
@FeignClient("FIRST-DEMO")
public interface DemoService {

    @GetMapping("demo/showVersion")
    Result showVersion();

    @GetMapping("demo/getPort/{uuid}")
    Result getPort(@PathVariable("uuid") String uuid);
}
