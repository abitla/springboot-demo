package com.loyer.example.utils;

import com.alibaba.fastjson.JSON;
import com.loyer.example.entity.AccessToken;
import com.loyer.example.entity.ApiTicket;
import com.loyer.example.entity.Jssdk;
import com.loyer.example.enums.HintEnum;
import com.loyer.example.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.util.*;

/**
 * @author kuangq
 * @projectName example
 * @title JssdkUtil
 * @description 微信jssdk获取登录密令
 * @date 2019-08-28 15:53
 */
public class JssdkUtil {

    private static final Logger logger = LoggerFactory.getLogger(JssdkUtil.class);

    private static Jssdk jssdk = ContextUtil.getBean("jssdk");

    private static RestTemplate restTemplate = ContextUtil.getBean("restTemplate");

    private static RedisUtil redisUtil = ContextUtil.getBean("redisUtil");

    /**
     * @param
     * @return java.lang.String
     * @author kuangq
     * @description 获取accessToken令牌
     * @date 2019-08-28 23:30
     */
    private static String getAccessToken() throws Exception {
        if (redisUtil.exists("accessToken")) {
            return redisUtil.getValue("accessToken").toString();
        }
        String getTokenUrl = String.format(jssdk.getGetTokenUrl(), jssdk.getAppID(), jssdk.getAppSecret());
        AccessToken accessToken = HttpUtil.doGet(getTokenUrl, AccessToken.class);
        logger.info("【accessToken】{}", JSON.toJSONString(accessToken));
        Objects.requireNonNull(accessToken, HintEnum.HINT_1012.getMsg());
        if (accessToken.getErrcode() != null && accessToken.getErrcode() != 0) {
            throw new BusinessException(HintEnum.HINT_1012, accessToken);
        }
        redisUtil.setValue("accessToken", accessToken.getAccess_token(), (long) accessToken.getExpires_in());
        return accessToken.getAccess_token();
    }

    /**
     * @param
     * @return java.lang.String
     * @author kuangq
     * @description 获取apiTicket卡券
     * @date 2019-08-28 23:30
     */
    private static String getApiTicket() throws Exception {
        if (redisUtil.exists("apiTicket")) {
            return redisUtil.getValue("apiTicket").toString();
        }
        String getTicketUrl = String.format(jssdk.getGetTicketUrl(), getAccessToken());
        ApiTicket apiTicket = HttpUtil.doGet(getTicketUrl, ApiTicket.class);
        logger.info("【apiTicket】{}", JSON.toJSONString(apiTicket));
        Objects.requireNonNull(apiTicket, HintEnum.HINT_1013.getMsg());
        if (apiTicket.getErrcode() != 0) {
            throw new BusinessException(HintEnum.HINT_1013, apiTicket);
        }
        redisUtil.setValue("apiTicket", apiTicket.getTicket(), (long) apiTicket.getExpires_in());
        return apiTicket.getTicket();
    }

    /**
     * @param params
     * @return java.util.Map
     * @author kuangq
     * @description 生成验签
     * @date 2019-08-28 23:30
     */
    public static Map getSignature(Map params) throws Exception {
        checkParams(params);
        Map map = new HashMap() {{
            put("noncestr", UUID.randomUUID().toString().replaceAll("-", ""));
            put("jsapi_ticket", getApiTicket());
            put("timestamp", System.currentTimeMillis() / 1000);
            put("url", jssdk.getUrl());
        }};
        map.put("signature", encode(sealingParams(map)));
        map.put("appId", jssdk.getAppID());
        return map;
    }

    /**
     * @param params
     * @return void
     * @author kuangq
     * @description 入参校验
     * @date 2019-08-29 9:54
     */
    private static void checkParams(Map params) throws Exception {
        Objects.requireNonNull(params, HintEnum.HINT_1014.getMsg());
        if (!params.containsKey("url") || params.get("url").toString().isEmpty()) {
            throw new BusinessException(HintEnum.HINT_1015, params);
        }
        jssdk.setUrl(params.get("url").toString());
        if (params.containsKey("appID") && !params.get("appID").toString().isEmpty()) {
            jssdk.setAppID(params.get("appID").toString());
        }
        if (params.containsKey("appSecret") && !params.get("appSecret").toString().isEmpty()) {
            jssdk.setAppID(params.get("appSecret").toString());
        }
    }

    /*自定义比较器类：根据Map的Key值*/
    private static class MapKeyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * @param params
     * @return java.lang.String
     * @author kuangq
     * @description 封装入参
     * @date 2019-08-09 17:21
     */
    public static String sealingParams(Map params) {
        Objects.requireNonNull(params, HintEnum.HINT_1014.getMsg());
        StringBuilder stringBuilder = new StringBuilder();
        Map resultMap = new TreeMap(new MapKeyComparator());
        resultMap.putAll(params);
        resultMap.forEach((key, value) -> {
            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(value);
            stringBuilder.append("&");
        });
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * @param bytes
     * @return java.lang.String
     * @author kuangq
     * @description 把密文转换成十六进制的字符串形式
     * @date 2019-08-28 23:19
     */
    private static String getFormatStr(byte[] bytes) {
        final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * @param str
     * @return java.lang.String
     * @author kuangq
     * @description SHA1算法加密
     * @date 2019-08-28 23:20
     */
    private static String encode(String str) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        messageDigest.update(str.getBytes());
        return getFormatStr(messageDigest.digest());
    }
}
