package com.loyer.example.handle;

import com.loyer.example.enums.HintEnum;
import com.loyer.example.exception.BusinessException;
import com.loyer.example.utils.DESUtil;
import com.loyer.example.utils.MD5Util;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kuangq
 * @projectName example
 * @title InterceptorHandle
 * @description 拦截器
 * @date 2019-08-09 11:25
 */
@Configuration
public class InterceptorHandle extends WebMvcConfigurationSupport {

    private static Logger logger = LoggerFactory.getLogger(InterceptorHandle.class);

    private static long expire = 60 * 5;

    /*放行接口*/
    private List releasePort = new ArrayList() {{
        add("/error/**"); //开放异常响应接口
        add("/static/**"); //开放静态资源访问
        add("/demo/showVersion"); //开放版本号查看接口
    }};

    /**
     * @param registry
     * @return void
     * @author kuangq
     * @description 注册拦截器
     * @date 2019-08-09 11:45
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new Interceptor());
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns(releasePort);
        interceptorRegistration.excludePathPatterns("/webjars/**", "/swagger-resources/**", "/v2/**", "/swagger-ui.html/**", "/csrf/**");
    }

    /**
     * @param registry
     * @return void
     * @author kuangq
     * @description 实现resources资源文件添加、处理设置static-path-pattern: /static/**后/swagger-ui.html访问失败问题
     * @date 2019-10-15 16:43
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*将所有/static/**访问都映射到classpath:/static/目录下*/
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        /*swagger2静态文件*/
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    private class Interceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            String browser = userAgent.getBrowser().toString();
            String requestURL = request.getRequestURL().toString();
            String token = request.getHeader("token");
            String timestamp = request.getHeader("timestamp");
            String signature = request.getHeader("signature");
            logger.info("【浏览器】{}；【请求地址】{}", browser, requestURL);
            if (token == null || timestamp == null || signature == null) {
                throw new BusinessException(HintEnum.HINT_1002);
            }
            long currentTime = System.currentTimeMillis();
            long times = Long.valueOf(timestamp);
            if (Math.abs(currentTime - times) > expire * 1000) {
                throw new BusinessException(HintEnum.HINT_1003);
            }
            String desEncryptStr = DESUtil.encrypt(token + "&" + timestamp);
            String md5EncryptStr = MD5Util.encrypt(desEncryptStr);
            if (!signature.equals(md5EncryptStr)) {
                throw new BusinessException(HintEnum.HINT_1004);
            }
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        }
    }
}
