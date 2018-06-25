package com.bao.miaosha.interceptor;

import com.bao.miaosha.domain.MiaoshaUser;

public class UserContext {
    private static ThreadLocal<MiaoshaUser> threadLocal;

    public static void set(MiaoshaUser user){
        threadLocal.set(user);
    }

    public static MiaoshaUser get(){
        return threadLocal.get();
    }

    static {
        threadLocal = new ThreadLocal<>();
    }
}
