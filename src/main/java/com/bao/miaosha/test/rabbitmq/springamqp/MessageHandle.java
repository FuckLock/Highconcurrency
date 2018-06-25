package com.bao.miaosha.test.rabbitmq.springamqp;

public class MessageHandle {

    public void handleMessage(byte[] body){
        System.out.println("================handleMessage(byte[] body)============");
        System.out.println(new String(body));
    }

    public void sendDianhua(byte[] body){
        System.out.println("================sendDianhua(byte[] body)============");
        System.out.println(new String(body));
    }

    public void sendWeixing(byte[] body){
        System.out.println("================ sendWeixing(byte[] body)============");
        System.out.println(new String(body));
    }

    public void sendDuanxin(byte[] body){
        System.out.println("================sendDuanxin(byte[] body)============");
        System.out.println(new String(body));
    }

    public void sendDianhua(String body){
        System.out.println("================sendDianhua(String body)============");
        System.out.println(body);
    }

    public void sendWeixing(String body){
        System.out.println("================ sendWeixing(String body)============");
        System.out.println(body);
    }

    public void sendDuanxin(String body){
        System.out.println("================sendDuanxin(String body)============");
        System.out.println(body);
    }


}
