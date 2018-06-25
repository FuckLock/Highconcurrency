package com.bao.miaosha.redis;

public class MiaoshaOrderKey extends BaseBeanKey {

    public static MiaoshaOrderKey miaoshaOrderKey;

    public MiaoshaOrderKey(String prefix) {
        super(prefix);
    }

    public MiaoshaOrderKey() {
        super();
    }

    static {
        miaoshaOrderKey = new MiaoshaOrderKey();
    }

}
