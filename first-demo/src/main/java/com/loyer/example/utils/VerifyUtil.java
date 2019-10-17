package com.loyer.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kuangq
 * @projectName example
 * @title VerifyUtil
 * @description 校验工具类
 * @date 2019-10-15 17:35
 */
public class VerifyUtil {

    /**
     * @param phoneNumber
     * @return boolean
     * @author kuangq
     * @description 校验手机号
     * @date 2019-10-15 17:35
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        String PHONE_REGULAR = "^1[3|4|5|7|8]\\d{9}$";
        Pattern p = Pattern.compile(PHONE_REGULAR);
        Matcher matcher = p.matcher(phoneNumber);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    /**
     * @param idCard
     * @return boolean
     * @author kuangq
     * @description 校验身份证号
     * @date 2019-10-15 17:36
     */
    public static boolean checkIdCard(String idCard) {
        String IDCARD_REGULAR = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern p = Pattern.compile(IDCARD_REGULAR);
        Matcher matcher = p.matcher(idCard);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    /**
     * @param passWord
     * @return boolean
     * @author kuangq
     * @description 校验密码强度
     * @date 2019-10-15 17:36
     */
    public static boolean checkPassWord(String passWord) {
        Pattern p1 = Pattern.compile("[-\\da-zA-Z`=\\\\ ;',./~!@#$%^&*()_+|{}:<>?]{6,40}"); //总的
        Pattern p2 = Pattern.compile(".*\\d.*"); //含数字
        Pattern p3 = Pattern.compile(".*[a-z].*"); //含小写字母
        Pattern p4 = Pattern.compile(".*[A-Z].*"); //含大写字母
        Pattern p5 = Pattern.compile(".*[-`=\\\\ ;',./~!@#$%^&*()_+|{}:<>?].*"); //含特殊字符
        Matcher matcher1 = p1.matcher(passWord);
        Matcher matcher2 = p2.matcher(passWord);
        Matcher matcher3 = p3.matcher(passWord);
        Matcher matcher4 = p4.matcher(passWord);
        Matcher matcher5 = p5.matcher(passWord);
        if (matcher1.matches() && matcher2.matches() && matcher3.matches() && matcher4.matches() && matcher5.matches()) {
            return true;
        }
        return false;
    }
}
