package com.bao.miaosha.redis;

public class MiaoshaVeriCode extends BaseBeanKey {
    public static MiaoshaVeriCode miaoshaVeriCode;

    public MiaoshaVeriCode(String prefix) {
        super(prefix);
    }

    public MiaoshaVeriCode() {
        super();
    }

    static {
        miaoshaVeriCode = new MiaoshaVeriCode();
    }
}
