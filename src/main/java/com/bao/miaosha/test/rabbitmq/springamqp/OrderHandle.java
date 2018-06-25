package com.bao.miaosha.test.rabbitmq.springamqp;

public class OrderHandle {

    public void sendDianhua(byte[] body){
        System.out.println("============ sendDianhua(byte[] body)=============");
        System.out.println(new String(body));
    }

}
