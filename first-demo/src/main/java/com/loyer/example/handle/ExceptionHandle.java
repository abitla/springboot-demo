package com.loyer.example.handle;

import com.alibaba.fastjson.JSON;
import com.loyer.example.controller.DemoController;
import com.loyer.example.entity.Result;
import com.loyer.example.enums.HintEnum;
import com.loyer.example.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kuangq
 * @projectName example
 * @title ExceptionHandle
 * @description 统一处理异常响应结果
 * @date 2019-07-19 9:36
 */
@ControllerAdvice(assignableTypes = {DemoController.class})
public class ExceptionHandle {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    private Object handle(Exception e, HttpServletRequest httpServletRequest) {
        logger.info("【异常格式处理】{}", httpServletRequest.getRequestURI());
        Result result = null;
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            result = new Result().failure(businessException.getCode(), businessException.getMsg(), businessException.getData());
        } else {
            result = new Result().failure(HintEnum.HINT_1001.getCode(), HintEnum.HINT_1001.getMsg(), e.getMessage());
        }
        if (checkPort(httpServletRequest)) {
            return JSON.toJSONString(result);
        }
        return result;
    }

    private boolean checkPort(HttpServletRequest httpServletRequest) {
        String port = httpServletRequest.getRequestURI().substring("/demo/".length());
        List bstPort = new ArrayList() {{
            add("especially");
        }};
        return bstPort.contains(port);
    }
}
