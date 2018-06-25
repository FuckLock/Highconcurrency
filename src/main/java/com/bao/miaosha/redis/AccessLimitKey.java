package com.bao.miaosha.redis;

public class AccessLimitKey extends BaseBeanKey{

    public static AccessLimitKey accessLimitKey;

    public AccessLimitKey(String prefix) {
        super(prefix);
    }

    public AccessLimitKey() {
        super();
    }

    static {
        accessLimitKey = new AccessLimitKey();
    }

}
