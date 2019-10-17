package com.loyer.example.service;

import com.loyer.example.entity.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author kuangq
 * @projectName example
 * @title DemoService
 * @description 测试Service
 * @date 2019-07-16 15:26
 */
public interface DemoService {

    Result queryDataBase() throws Exception;

    Result upload(HttpServletRequest httpServletRequest);

    Result saveFile(List<Map> params) throws Exception;

    Result deleteFile(Map params) throws Exception;

    Result queryFileInfo(Map params) throws Exception;

    Result certificateRecognition(HttpServletRequest httpServletRequest);

    Result threadTest(Map params) throws Exception;

    Result queryUserInfo(Map params) throws Exception;
}
