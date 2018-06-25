package com.bao.miaosha.test.rabbitmq.springamqp.annotationRabbitlisten;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "dianhua", containerFactory = "rabbitListenerContainerFactory2")
public class My2Handle {

    @RabbitHandler
    public void handle(byte[] body){
        System.out.println("=============handle(byte[] body)===================");
        System.out.println(new String(body));
    }

    @RabbitHandler
    public void handle(String body){
        System.out.println("=============handle(String body)===================");
        System.out.println(body);
    }

    @RabbitHandler
    public void handle(User body){
        System.out.println("=============handle(User body)===================");
        System.out.println(body.toString());
    }
}
