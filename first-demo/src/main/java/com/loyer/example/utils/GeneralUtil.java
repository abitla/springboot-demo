package com.loyer.example.utils;

import com.loyer.example.entity.Result;
import com.loyer.example.enums.HintEnum;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author kuangq
 * @projectName example
 * @title GeneralUtil
 * @description 通用工具类
 * @date 2019-09-04 21:36
 */
public class GeneralUtil {

    /**
     * @param
     * @return java.lang.String
     * @author kuangq
     * @description 获取UUID
     * @date 2019-09-04 21:58
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * @param obj1
     * @param obj2
     * @return java.lang.Object
     * @author kuangq
     * @description 封装Oracle/nvl函数
     * @date 2019-10-09 15:34
     */
    public static Object nvl(Object obj1, Object obj2) {
        return obj1 == null || "".equals(obj1) ? obj2 : obj1;
    }

    /**
     * @param obj1
     * @param obj2
     * @param obj3
     * @return java.lang.Object
     * @author kuangq
     * @description 封装Oracle/nvl2函数
     * @date 2019-10-09 15:35
     */
    public static Object nvl2(Object obj1, Object obj2, Object obj3) {
        return obj1 == null || "".equals(obj1) ? obj3 : obj2;
    }

    /**
     * @param objAry
     * @return java.lang.Object
     * @author kuangq
     * @description 封装Oracle/decode函数默认返回null
     * @date 2019-10-09 15:34
     */
    public static Object decode(Object... objAry) {
        int length = objAry.length;
        int count = (length & 1) == 0 ? length / 2 - 1 : length / 2;
        for (int i = 0; i < count; i++) {
            if (objAry[0].getClass() == objAry[2 * i + 1].getClass() && objAry[0].equals(objAry[2 * i + 1])) {
                return objAry[2 * i + 2];
            }
        }
        return (length & 2) == 0 ? objAry[length - 1] : null;
    }

    /**
     * @param path
     * @param newFileName
     * @return java.lang.String
     * @author kuangq
     * @description 修改文件名
     * @date 2019-10-10 12:42
     */
    public static String rename(String path, String newFileName) {
        File[] files = new File(path).listFiles(); // 将该目录下文件全部放入数组
        if (files == null) {
            return path + "：该路径不存在";
        }
        for (int i = 0, j = 1; i < files.length; i++, j++) {
            if (files[i].isDirectory()) { // 判断是文件还是文件夹
                rename(files[i].getAbsolutePath(), newFileName); // 获取文件绝对路径递归
            } else {
                String rootPath = files[i].getParent(); // 获取文件路径
                String fileName = files[i].getName(); // 获取文件名
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);// 获取文件后缀名
                Object[] objAry = {suffix, "png", "jpg", suffix}; // 后缀名修改规则
                suffix = "." + GeneralUtil.decode(objAry).toString(); // 生成新的后缀名
                File newFile = new File(rootPath + File.separator + newFileName + "_" + j + suffix); // 生成新的文件名对象
                files[i].renameTo(newFile); // 修改文件名
            }
        }
        return "文件名修改成功";
    }

    /**
     * @param filePath
     * @return com.loyer.example.entity.Result
     * @author kuangq
     * @description 删除空文件夹
     * @date 2019-10-10 15:29
     */
    public static Result DeleteEmptyDirectory(String filePath) {
        File source = new File(filePath);
        if (!source.exists()) {
            return Result.hintEnum(HintEnum.HINT_1009);
        }
        List list = new ArrayList();
        for (File file : source.listFiles()) {
            if (file.listFiles() == null) {
                break;
            }
            if (file.isDirectory()) {
                DeleteEmptyDirectory(file.getPath());
                if (file.listFiles().length == 0) {
                    list.add(file.getPath() + (file.delete() ? "删除成功" : "删除失败"));
                }
            }
        }
        return Result.hintEnum(HintEnum.HINT_1000, list);
    }
}
