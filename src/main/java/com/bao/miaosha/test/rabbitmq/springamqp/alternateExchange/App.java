package com.bao.miaosha.test.rabbitmq.springamqp.alternateExchange;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class App {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        System.out.println("spring start");
//        context.getBean(RabbitAdmin.class).getQueueProperties("xx");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
//                rabbitTemplate.send("log", "log-que", new Message("你好".getBytes(), new MessageProperties()));
//        rabbitTemplate.send("log", "dsf", new Message("你好".getBytes(), new MessageProperties()));
                rabbitTemplate.send("bao2", "dsf", new Message("你好".getBytes(), new MessageProperties()));
        Thread.sleep(5000);
        context.close();
    }
}
