package com.bao.miaosha.result;

import com.sun.tools.javac.jvm.Code;

public class CodeMsg {
    // 用户
    public static final CodeMsg SERVER_ERROR;
    public static final CodeMsg MOBILE_EMPTY;
    public static final CodeMsg PASSWORD_EMPTY;
    public static final CodeMsg PHONENUM_FORMAT;
    public static final CodeMsg USER_NOT_EXIST;
    public static final CodeMsg PASSWORD_ERROR;
    public static final CodeMsg UPDATE_PASSWORD_FAIL;
    public static final CodeMsg UPDATE_PASSWORD_SUCCESS;
    public static final CodeMsg TOKEN_ERROR;
    public static final CodeMsg BIND_ERROR;
    public static final CodeMsg USER_NOT_LOGIN;
    public static final CodeMsg ORDER_NOT_EXIST;
    public static final CodeMsg ILLEGAL_REQUEST;
    public static final CodeMsg REQUEST_FREQUENT;
    // 订单
    public static final CodeMsg REPEATE_MIAOSHA;
    public static final CodeMsg OUT_OF_STOCK;
    public static final CodeMsg VERIFY_CODE_ERROR;

    private int code;
    private String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        return new CodeMsg(code, String.format(msg, args));
    }

    static {
        // 用户
        SERVER_ERROR = new CodeMsg(50010, "服务器异常");
        MOBILE_EMPTY = new CodeMsg(50011, "手机号为空");
        PASSWORD_EMPTY = new CodeMsg(50012, "密码为空");
        PHONENUM_FORMAT = new CodeMsg(50013, "手机号格式错误");
        USER_NOT_EXIST = new CodeMsg(50014, "用户不存在");
        PASSWORD_ERROR = new CodeMsg(50015, "密码错误");
        UPDATE_PASSWORD_FAIL = new CodeMsg(50016, "更新密码失败");
        UPDATE_PASSWORD_SUCCESS = new CodeMsg(50017, "更新密码成功");
        TOKEN_ERROR = new CodeMsg(50018, "token错误");
        BIND_ERROR = new CodeMsg(50019, "参数校验异常：%s");
        USER_NOT_LOGIN = new CodeMsg(50020, "用户未登录");

        // 订单
        OUT_OF_STOCK = new CodeMsg(50110, "库存不足");
        REPEATE_MIAOSHA = new CodeMsg(50111, "您订单已经存在请不要重复订购");
        ORDER_NOT_EXIST = new CodeMsg(50112, "订单不存在");
        VERIFY_CODE_ERROR = new CodeMsg(50113, "验证码错误");

        // 通用
        ILLEGAL_REQUEST = new CodeMsg(50210, "非法请求");
        REQUEST_FREQUENT = new CodeMsg(50211, "请求过于频繁，请稍后再试");
    }
}
