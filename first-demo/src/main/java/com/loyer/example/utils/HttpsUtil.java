package com.loyer.example.utils;

import com.alibaba.fastjson.JSONObject;
import com.loyer.example.enums.HintEnum;
import com.loyer.example.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @author kuangq
 * @projectName example
 * @title HttpsUtil
 * @description Https请求工具类
 * @date 2019-09-04 21:21
 */
public class HttpsUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpsUtil.class);

    public static JSONObject httpsRequest(String url, String method, String params, Map headers) {
        JSONObject jsonObject = new JSONObject();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] trustManagers = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, trustManagers, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            //创建Https请求连接
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) new URL(url).openConnection();
            //设置请求header
            if (headers != null && !headers.isEmpty()) {
                headers.forEach((key, value) -> {
                    httpUrlConn.setRequestProperty(key.toString(), value.toString());
                });
            }
            //设置SSL
            httpUrlConn.setSSLSocketFactory(ssf);
            //允许输入
            httpUrlConn.setDoInput(true);
            //允许输出
            httpUrlConn.setDoOutput(true);
            //不允许缓存
            httpUrlConn.setUseCaches(false);
            //设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(method);
            //如果是get请求
            if (HttpMethod.GET.toString().equalsIgnoreCase(method)) {
                httpUrlConn.connect();
            }
            // 当有数据需要提交时
            if (!StringUtils.isEmpty(params)) {
                outputStream = httpUrlConn.getOutputStream();
                outputStream.write(params.getBytes("UTF-8"));
                outputStream.close();
            }
            // 将请求返回的输入流转换成字json对象
            inputStream = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }
            httpUrlConn.disconnect();
            jsonObject = (JSONObject) JSONObject.parse(stringBuffer.toString());
            logger.info("【https请求出参】{}：{}", httpUrlConn.getResponseCode(), jsonObject);
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1016);
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            } catch (IOException e) {
                throw new BusinessException(HintEnum.HINT_1011);
            }
        }
        return jsonObject;
    }

    public static class MyX509TrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}