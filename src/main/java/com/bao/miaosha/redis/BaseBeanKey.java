package com.bao.miaosha.redis;

import org.thymeleaf.util.StringUtils;

public abstract class BaseBeanKey implements KeyPrefix {

    private String prefix;

    public BaseBeanKey(String prefix) {
        this.prefix = prefix;
    }

    public BaseBeanKey() {
        super();
    }

    @Override
    public String setPrefix() {
        StringBuilder sb = new StringBuilder();
        String ClassName = getClass().getSimpleName();
        if (StringUtils.isEmpty(prefix)){
            return sb.append(ClassName).append(":").toString();
        }
        return sb.append(ClassName).append(":").append(prefix).toString();
    }
}
