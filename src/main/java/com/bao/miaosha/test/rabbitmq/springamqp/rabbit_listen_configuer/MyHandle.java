package com.bao.miaosha.test.rabbitmq.springamqp.rabbit_listen_configuer;

public class MyHandle {

    public void handleMessage(byte[] body){
        System.out.println("====================handleMessage===============");
        System.out.println(new String(body));
    }
}
