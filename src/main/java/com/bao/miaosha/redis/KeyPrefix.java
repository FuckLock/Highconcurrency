package com.bao.miaosha.redis;

public interface KeyPrefix {

    default String setPrefix(){
        return null;
    }

}
