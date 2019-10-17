package com.loyer.example.oceansoft;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author oceansoft
 * @projectName example
 * @title DesUtils
 * @description 身份证加解密工具类
 * @date 2019-07-16 15:26
 */
public class DesUtils {

    private static String key1 = "58563214";
    private static String key2 = "32128158";

    public static String decrypt(String message) {
        try {
            byte[] bytesrc = new BASE64Decoder().decodeBuffer(message);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key1.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encrypt(String message) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key1.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            return new BASE64Encoder().encode(cipher.doFinal(message.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
