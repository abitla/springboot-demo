package com.loyer.example.utils;

import com.loyer.example.enums.HintEnum;
import com.loyer.example.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * @author kuangq
 * @projectName example
 * @title DESUtil
 * @description DES加解密
 * @date 2019-08-09 14:14
 */
public class DESUtil {

    private static final Logger logger = LoggerFactory.getLogger(DESUtil.class);

    private static Key key;
    private static String ALGORITHM = "DES";
    private static String ENCRYPT_CHARSET = "UTF-8";
    private static String SECRET_KEY = "loyer";

    static {
        try {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            byte[] keyBytes = SECRET_KEY.getBytes(ENCRYPT_CHARSET);
            byte[] bytes = new byte[8];
            for (int i = 0; i < keyBytes.length && i < bytes.length; i++) {
                bytes[i] = keyBytes[i];
            }
            key = new SecretKeySpec(bytes, ALGORITHM);
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1005, e.getMessage());
        }
    }

    /**
     * @param message
     * @return java.lang.String
     * @author kuangq
     * @description DES加密
     * @date 2019-08-23 16:21
     */
    public static String encrypt(String message) {
        try {
            // 按UTF8编码
            byte[] messageBytes = message.getBytes(ENCRYPT_CHARSET);
            // 获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] doFinal = cipher.doFinal(messageBytes);
            //处理加密信息
            return bytesToHexStr(doFinal);
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1006, e.getMessage());
        }
    }

    /**
     * @param bytes
     * @return java.lang.String
     * @author kuangq
     * @description 字节数组做16进制处理
     * @date 2019-08-23 16:22
     */
    private static String bytesToHexStr(byte[] bytes) throws Exception {
        int length = bytes.length;
        StringBuffer stringBuffer = new StringBuffer(length * 2);
        for (int i = 0; i < length; i++) {
            int intTmp = bytes[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Integer.toString(intTmp, 16));
        }
        return stringBuffer.toString();
    }

    /**
     * @param message
     * @return java.lang.String
     * @author kuangq
     * @description DES解密
     * @date 2019-08-23 16:21
     */
    public static String decrypt(String message) {
        try {
            //待解密信息做16进制处理
            byte[] bytes = hexStrToBytes(message);
            // 获取解密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密
            byte[] doFinal = cipher.doFinal(bytes);
            //处理解密数据
            return new String(doFinal);
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1007, e.getMessage());
        }
    }

    /**
     * @param str
     * @return byte[]
     * @author kuangq
     * @description 字符串做16进制处理
     * @date 2019-08-23 16:22
     */
    private static byte[] hexStrToBytes(String str) throws Exception {
        byte[] strBytes = str.getBytes(ENCRYPT_CHARSET);
        int length = strBytes.length;
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i = i + 2) {
            String tempStr = new String(strBytes, i, 2);
            bytes[i / 2] = (byte) Integer.parseInt(tempStr, 16);
        }
        return bytes;
    }
}