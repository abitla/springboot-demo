package com.loyer.example.utils;

import com.loyer.example.enums.HintEnum;
import com.loyer.example.exception.BusinessException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author kuangq
 * @projectName example
 * @title ConvertPDF
 * @description PDF转换工具类
 * @date 2019-10-10 10:29
 */

public class ConvertPDF {

    /**
     * @param file
     * @return java.lang.String
     * @author kuangq
     * @description PDF文件转BASE64
     * @date 2019-10-10 10:30
     */
    public static String PDFToBase64(File file) {
        BASE64Encoder encoder = new BASE64Encoder();
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            byteArrayOutputStream = new ByteArrayOutputStream();
            bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
            byte[] buffer = new byte[1024];
            int len = bufferedInputStream.read(buffer);
            while (len != -1) {
                bufferedOutputStream.write(buffer, 0, len);
                len = bufferedInputStream.read(buffer);
            }
            bufferedOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return encoder.encodeBuffer(bytes).trim();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedOutputStream != null) bufferedOutputStream.close();
                if (byteArrayOutputStream != null) byteArrayOutputStream.close();
                if (bufferedInputStream != null) bufferedInputStream.close();
                if (fileInputStream != null) fileInputStream.close();
            } catch (Exception e) {
                throw new BusinessException(HintEnum.HINT_1011);
            }
        }
        return null;
    }


    /**
     * @param base64Content
     * @param filePath
     * @return void
     * @author kuangq
     * @description PDFbase64转File本地保存
     * @date 2019-10-10 10:30
     */
    public static void Base64ToPDF(String base64Content, String filePath) {
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(base64Content);
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
            bufferedInputStream = new BufferedInputStream(byteInputStream);
            File file = new File(filePath);
            File path = file.getParentFile();
            if (!path.exists()) {
                path.mkdirs();
            }
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            byte[] buffer = new byte[1024];
            int length = bufferedInputStream.read(buffer);
            while (length != -1) {
                bufferedOutputStream.write(buffer, 0, length);
                length = bufferedInputStream.read(buffer);
            }
            bufferedOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedOutputStream != null) bufferedOutputStream.close();
                if (fileOutputStream != null) fileOutputStream.close();
                if (bufferedInputStream != null) bufferedInputStream.close();
            } catch (Exception e) {
                throw new BusinessException(HintEnum.HINT_1011);
            }
        }
    }
}
