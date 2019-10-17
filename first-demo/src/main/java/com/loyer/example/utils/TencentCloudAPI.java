package com.loyer.example.utils;

import com.alibaba.fastjson.JSONObject;
import com.loyer.example.enums.HintEnum;
import com.loyer.example.exception.BusinessException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author kuangq
 * @projectName example
 * @title TencentCloudAPI
 * @description 腾讯云接口
 * @date 2019-09-17 14:39
 */
public class TencentCloudAPI {

    private final static String CHARSET = "UTF-8";

    private final static String DOMAINNAME = "https://faceid.tencentcloudapi.com/?%s";

    private final static String SECRETID = "AKIDsVdeS96kQpxpSLuXkeQwC8opZ2A1cpmR";

    private final static String SECRETKEY = "bv2arsd8NOAg4B6k7C3dw5xZv0oPkohK";

    /**
     * @param
     * @return void
     * @author kuangq
     * @description 实名核身鉴权
     * @date 2019-09-17 13:31
     */
    public static void detectAuth() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Map params = new HashMap() {{
            put("Action", "DetectAuth"); //方法名
            put("RedirectUrl", String.format("http://172.17.150.175:9999/demo/detectAuthRedirect%s", uuid)); //回调地址
            put("Signature", getSignature(jointParams(this), SECRETKEY, "HmacSHA1")); //验签
        }};
        String url = String.format(DOMAINNAME, getUrl(params));
        JSONObject jsonObject = HttpsUtil.httpsRequest(url, "GET", null, null).getJSONObject("Response");
        if (!jsonObject.containsKey("BizToken")) {
            throw new BusinessException(HintEnum.HINT_1021, jsonObject);
        }
    }

    /**
     * @param bizToken
     * @return void
     * @author kuangq
     * @description 获取实名核身结果信息
     * @date 2019-09-17 13:31
     */
    public static void getDetectInfo(String bizToken) {
        try {
            Map params = new HashMap() {{
                put("Action", "GetDetectInfo"); //方法名
                put("BizToken", bizToken); //人脸核身流程的标识,调用DetectAuth接口时生成
                put("InfoType", "1"); //指定拉取的结果信息(0:全部;1:文本类;2:身份证正反面;3:视频最佳截图照片;4:视频).如134表示拉取文本类,视频最佳截图照片,视频
                put("Signature", getSignature(jointParams(this), SECRETKEY, "HmacSHA1")); //验签
            }};
            String url = String.format(DOMAINNAME, getUrl(params));
            JSONObject jsonObject = HttpsUtil.httpsRequest(url, "GET", null, null).getJSONObject("Response");
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1001, e.getMessage());
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
     * @description 参数拼接
     * @date 2019-09-17 10:38
     */
    private static String jointParams(Map params) {
        try {
            Objects.requireNonNull(params, HintEnum.HINT_1014.getMsg());
            params.put("Region", "ap-chongqing"); //实例所在区域
            params.put("Version", "2018-03-01"); //接口版本号
            params.put("RuleId", "0"); //用于细分客户使用场景,申请开通服务后,可以在腾讯云慧眼人脸核身控制台自助接入里面创建
            params.put("Nonce", new Random().nextInt(Integer.MAX_VALUE)); //随机正整数
            params.put("Timestamp", System.currentTimeMillis() / 1000); //时间戳
            params.put("SecretId", SECRETID); //密钥ID
            StringBuilder stringBuilder = new StringBuilder("GETfaceid.tencentcloudapi.com/?"); //请求方法 + 请求主机 +请求路径 + ? + 请求字符串
            Map resultMap = new TreeMap(new MapKeyComparator());
            resultMap.putAll(params);
            resultMap.forEach((key, value) -> {
                stringBuilder.append(key).append("=").append(value).append("&");
            });
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1018, e.getMessage());
        }
    }

    /**
     * @param srcStr
     * @param secretKey
     * @param method
     * @return java.lang.String
     * @author kuangq
     * @description 获取验签
     * @date 2019-09-17 10:38
     */
    private static String getSignature(String srcStr, String secretKey, String method) {
        try {
            Mac mac = Mac.getInstance(method);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(CHARSET), mac.getAlgorithm());
            mac.init(secretKeySpec);
            byte[] bytes = mac.doFinal(srcStr.getBytes(CHARSET));
            return DatatypeConverter.printBase64Binary(bytes);
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1019, e.getMessage());
        }
    }

    /**
     * @param params
     * @return java.lang.String
     * @author kuangq
     * @description 拼接请求地址
     * @date 2019-09-17 10:38
     */
    private static String getUrl(Map<String, Object> params) {
        try {
            StringBuilder url = new StringBuilder();
            // 需要对请求串进行urlencode，由于key都是英文字母，故此处仅对其value进行urlencode
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                url.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(), CHARSET)).append("&");
            }
            return url.substring(0, url.length() - 1);
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1020, e.getMessage());
        }
    }
}