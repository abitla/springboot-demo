package com.loyer.example.service;

import com.loyer.example.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author kuangq
 * @projectName producer
 * @title TestService
 * @description TODO
 * @date 2019-10-17 11:01
 */
@FeignClient("example")
public interface TestService {

    @GetMapping("demo/showVersion")
    String showVersion();


    @GetMapping("demo/getPort/{uuid}")
    public Result getPort(@PathVariable("uuid") String uuid);
}
