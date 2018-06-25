package com.bao.miaosha.redis;

public class MiaoshaUserKey extends BaseBeanKey {

    public static MiaoshaUserKey token = new MiaoshaUserKey("token");
    public static MiaoshaUserKey id = new MiaoshaUserKey("userId");

    public MiaoshaUserKey(String prefix) {
        super(prefix);
    }

    public MiaoshaUserKey() {
        super();
    }
}
