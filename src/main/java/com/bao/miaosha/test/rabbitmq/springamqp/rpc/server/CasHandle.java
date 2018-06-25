package com.bao.miaosha.test.rabbitmq.springamqp.rpc.server;

import java.util.Map;

public class CasHandle {

    public User handleMessage(byte[] body){
        System.out.println("===================服务器端收到消息了，正在处理======================");
        System.out.println(new String(body));
        User user = new User();
        user.setId(1);
        user.setName("bao");
        return user;
    }

    public User handleMessage(Map<String, Object> body){
        System.out.println("===================服务器端收到消息了，正在处理======================");
        User user = new User();
        user.setId((Integer) body.get("id"));
        user.setName((String) body.get("name"));
        return user;
    }
}
