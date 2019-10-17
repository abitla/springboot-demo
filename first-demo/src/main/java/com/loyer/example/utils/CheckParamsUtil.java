package com.loyer.example.utils;

import com.loyer.example.exception.BusinessException;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @author kuangq
 * @projectName example
 * @title CheckParamsUtil
 * @description 参数校验
 * @date 2019-09-06 16:47
 */
public class CheckParamsUtil {

    public static void checkPaging(Map params) {
        String[] keyArray = {"pageNum", "pageSize"};
        String[] nameArray = {"查询页数", "分页大小"};
        for (int i = 0; i < keyArray.length; i++) {
            if (!params.containsKey(keyArray[i]) || StringUtils.isBlank(params.get(keyArray[i]).toString())) {
                throw new BusinessException(String.format("%s(%s)不能为空", keyArray[i], nameArray[i]), params);
            }
        }
    }
}
