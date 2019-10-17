package com.loyer.example.aspect;

import com.alibaba.fastjson.JSONObject;
import com.loyer.example.exception.BusinessException;
import com.loyer.example.utils.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author kuangq
 * @projectName example
 * @title LogAspect
 * @description 统一异常日志处理
 * @date 2019-07-19 10:22
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("execution(public * com.loyer.example.controller.DemoController.*(..) throws Exception)")
    private void DemoController() {
    }

    @Before("DemoController()")
    private void doBefore(JoinPoint joinPoint) {
        Map requestMap = getRequestMap(joinPoint);
        logger.info("【请求入参】{}", requestMap);
    }

    @AfterReturning(pointcut = "DemoController()", returning = "object")
    private void doAfterReturning(JoinPoint joinPoint, Object object) {
        logger.info("【响应出参】{}", JSONObject.toJSON(object));
    }

    @AfterThrowing(pointcut = "DemoController()", throwing = "throwable")
    private void exceptionDispose(JoinPoint joinPoint, Throwable throwable) {
        Exception e = (Exception) throwable;
        Map requestMap = getRequestMap(joinPoint);
        logger.error("【异常接口】{}", requestMap);
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            logger.error("【业务异常】{}", businessException.getMsg());
        } else {
            String uuid = UUID.randomUUID().toString();
            redisUtil.setValue(uuid, e.getMessage(), 3600L);
            logger.error("【系统异常】{}", uuid);
        }
    }

    private Map getRequestMap(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = attributes.getRequest();
        return new HashMap() {{
            put("ip", httpServletRequest.getRemoteAddr());
            put("url", httpServletRequest.getRequestURL());
            put("type", httpServletRequest.getMethod());
            put("class", joinPoint.getSignature().getDeclaringTypeName());
            put("method", joinPoint.getSignature().getName());
            put("args", joinPoint.getArgs().length > 0 ? JSONObject.toJSON(joinPoint.getArgs()) : null);
            put("params", !httpServletRequest.getParameterMap().isEmpty() ? JSONObject.toJSON(httpServletRequest.getParameterMap()) : null);
        }};
    }
}
