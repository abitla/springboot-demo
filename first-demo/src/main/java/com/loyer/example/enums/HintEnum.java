package com.loyer.example.enums;

/**
 * @author kuangq
 * @projectName example
 * @title HintEnum
 * @description 业务处理提示信息
 * @date 2019-07-19 9:22
 */
public enum HintEnum {

    HINT_1000(1000, "操作成功"),
    HINT_1001(1001, "服务器内部错误"),
    HINT_1002(1002, "非法访问"),
    HINT_1003(1003, "验签失效"),
    HINT_1004(1004, "验签失败"),
    HINT_1005(1005, "DES创建Key失败"),
    HINT_1006(1006, "DES加密失败"),
    HINT_1007(1007, "DES解密失败"),
    HINT_1008(1008, "MD5加密失败"),
    HINT_1009(1009, "该目录不存在"),
    HINT_1010(1010, "图片上传失败"),
    HINT_1011(1011, "关闭流失败"),
    HINT_1012(1012, "获取accessToken失败"),
    HINT_1013(1013, "获取apiTicket失败"),
    HINT_1014(1014, "入参不能为空"),
    HINT_1015(1015, "url不能为空"),
    HINT_1016(1016, "https请求异常"),
    HINT_1017(1017, "护照签注照片识别异常"),
    HINT_1018(1018, "参数拼接失败"),
    HINT_1019(1019, "获取验签失败"),
    HINT_1020(1020, "拼接请求地址失败"),
    HINT_1021(1021, "实名核身鉴权失败"),
    HINT_1022(1022, "获取实名核身结果失败"),
    HINT_1023(1023, "下载Excel失败"),
    ;

    private Integer code;

    private String msg;

    private Object data;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static HintEnum setData(HintEnum hintEnum, Object data) {
        hintEnum.setData(data);
        return hintEnum;
    }

    HintEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
