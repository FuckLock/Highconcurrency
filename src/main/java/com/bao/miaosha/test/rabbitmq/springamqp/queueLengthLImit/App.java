package com.bao.miaosha.test.rabbitmq.springamqp.queueLengthLImit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class App {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.send("", "limitque1", new Message(("10" + i).getBytes(), new MessageProperties()));
        }

        for (int i = 0; i < 10; i++) {
            rabbitTemplate.send("", "limitque2", new Message(("10" + i).getBytes(), new MessageProperties()));
        }
        context.close();

    }
}
