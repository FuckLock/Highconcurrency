package com.bao.miaosha.redis;

public class MiaoshaPathKey extends BaseBeanKey{
    public static MiaoshaPathKey miaoshaPathKey;

    public MiaoshaPathKey(String prefix) {
        super(prefix);
    }

    public MiaoshaPathKey() {
        super();
    }

    static {
        miaoshaPathKey = new MiaoshaPathKey();
    }

}
