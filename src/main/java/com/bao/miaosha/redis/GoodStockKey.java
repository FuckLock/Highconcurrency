package com.bao.miaosha.redis;

public class GoodStockKey extends BaseBeanKey {

    public static GoodStockKey goodStockKey;

    public GoodStockKey(String prefix) {
        super(prefix);
    }

    public GoodStockKey() {
        super();
    }

    static {
        goodStockKey = new GoodStockKey();
    }

}
