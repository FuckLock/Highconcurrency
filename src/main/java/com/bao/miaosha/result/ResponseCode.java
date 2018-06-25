package com.bao.miaosha.result;

public enum ResponseCode {

    // 用户
    SUCCESS(0, "SUCCESS"),
    ERROR(-1, "ERROR");

    private int code;
    private String msg;

    private ResponseCode(int code, String msg){
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
}
