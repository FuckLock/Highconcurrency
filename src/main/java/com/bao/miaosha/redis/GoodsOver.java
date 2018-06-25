package com.bao.miaosha.redis;

public class GoodsOver extends BaseBeanKey {
    public static GoodsOver goodsOver;

    public GoodsOver(String prefix) {
        super(prefix);
    }

    public GoodsOver() {
        super();
    }

    static {
        goodsOver = new GoodsOver();
    }
}
