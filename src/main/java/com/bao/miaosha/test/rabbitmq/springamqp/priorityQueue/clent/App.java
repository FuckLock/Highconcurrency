package com.bao.miaosha.test.rabbitmq.springamqp.priorityQueue.clent;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Random;

@ComponentScan
public class App {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        for (int i = 0; i < 10; i++) {
            int a = (int)(Math.random() * 6 + 1);
            MessageProperties properties = new MessageProperties();
            properties.setPriority(a);
            rabbitTemplate.send("", "priorityque1", new Message("fe".getBytes(), properties));
            Thread.sleep(1000);
        }
        context.close();
    }

}
