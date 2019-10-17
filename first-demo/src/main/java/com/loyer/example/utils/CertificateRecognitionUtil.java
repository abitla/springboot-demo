package com.loyer.example.utils;

import com.alibaba.fastjson.JSONObject;
import com.loyer.example.enums.HintEnum;
import com.loyer.example.exception.BusinessException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author kuangq
 * @projectName example
 * @title CertificateRecognitionUtil
 * @description 护照签注照片识别
 * @date 2019-09-03 17:35
 */
public class CertificateRecognitionUtil {

    //护照页私有云正式地址
    private static final String OCR_URL = "http://221.213.69.69:8085/icr/recognize_passport?crop_image=1";

    //签证页私有云正式地址
    private static final String VISA_URL = "http://221.213.69.69:8086/icr/recognize_visa";

    //识别接口
    public static JSONObject certificateRecognition(String type, InputStream fileInputStream) {
        JSONObject jsonObject = new JSONObject();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            String url = GeneralUtil.decode(new Object[]{type, "1", OCR_URL, "2", VISA_URL}).toString();
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            outputStream = httpURLConnection.getOutputStream();
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
                byteArrayOutputStream = new ByteArrayOutputStream();
                while ((len = inputStream.read(bytes)) != -1) {
                    byteArrayOutputStream.write(bytes, 0, len);
                }
                jsonObject = JSONObject.parseObject(byteArrayOutputStream.toString());
            }
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1017, e.getMessage());
        } finally {
            try {
                if (byteArrayOutputStream != null) byteArrayOutputStream.close();
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
                if (fileInputStream != null) fileInputStream.close();
            } catch (IOException e) {
                throw new BusinessException(HintEnum.HINT_1011);
            }
        }
        return jsonObject;
    }
}
