package com.bao.miaosha.test.rabbitmq.springboot;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Rabbitlistenannotaion {

    @RabbitListener(queues = "springbootque2")
    public void listen(byte[] body){
        System.out.println(new String(body));
    }
}
