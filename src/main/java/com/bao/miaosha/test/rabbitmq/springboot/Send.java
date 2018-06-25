package com.bao.miaosha.test.rabbitmq.springboot;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Send {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public String send(){
        rabbitTemplate.send("", "springbootque1", new Message("你好啊".getBytes(), new MessageProperties()));
        return "SUCCESS";
    }
}
