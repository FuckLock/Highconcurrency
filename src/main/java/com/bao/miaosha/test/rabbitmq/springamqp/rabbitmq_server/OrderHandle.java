package com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderHandle {

    public void sendDianhua(byte[] body){
        System.out.println("=====================sendDianhua(byte[] body)=============");
        System.out.println(new String(body));
        System.out.println("=====================打电话成功=============");
    }

    public void sendDianhua(Map body){
        System.out.println("=====================sendDianhua(Map body)=============");
        System.out.println(body.toString());
        System.out.println("=====================打电话成功=============");
    }

    public void sendDianhua(List body){
        System.out.println("=====================sendDianhua(ArrayList body)=============");
        System.out.println(body.toString());
        System.out.println("=====================打电话成功=============");
    }

    public void sendDianhua(Order body){
        System.out.println("=====================sendDianhua(Order body)=============");
        System.out.println(body.toString());
        System.out.println("=====================打电话成功=============");
    }

    public void sendDianhua(String body){
        System.out.println("=====================sendDianhua(String body)=============");
        System.out.println(body.toString());
        System.out.println("=====================打电话成功=============");
    }

    public void sendDianhua(File body){
        System.out.println("=====================sendDianhua(File body)=============");
        System.out.println(body);
        System.out.println("=====================打电话成功=============");
    }
}


