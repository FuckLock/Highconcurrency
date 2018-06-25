package com.bao.miaosha.redis;

public enum RedisPrefix {
    GOODSTOCK_PREFIX("goodStock");

    private String prefix;

    private RedisPrefix(String prefix){
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
