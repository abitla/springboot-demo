package com.loyer.example.utils;

import com.loyer.example.enums.HintEnum;
import com.loyer.example.exception.BusinessException;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author kuangq
 * @projectName example
 * @title MD5Util
 * @description MD5加解密
 * @date 2019-08-12 10:14
 */
public class MD5Util {

    /**
     * @param message
     * @return java.lang.String
     * @author kuangq
     * @description 加密
     * @date 2019-08-12 10:49
     */
    public static String encrypt(String message) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(message.getBytes("UTF-8"));
            return new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1008, e.getMessage());
        }
    }
}
