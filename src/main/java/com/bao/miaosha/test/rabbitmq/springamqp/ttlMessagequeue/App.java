package com.bao.miaosha.test.rabbitmq.springamqp.ttlMessagequeue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class App {

    // MEssage ttl and queue ttl 以小到为主
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        System.out.println("spring start");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        MessageProperties properties = new MessageProperties();
        properties.setExpiration("4000");
        rabbitTemplate.send("ttlexchange2", "", new Message("nihao".getBytes(), properties));
        Thread.sleep(50000);
        context.close();
    }

//    queue ttl
    public static void main2(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        System.out.println("spring start");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        MessageProperties properties = new MessageProperties();
        rabbitTemplate.send("ttlexchange2", "", new Message("nihao".getBytes(), properties));
        Thread.sleep(50000);
        context.close();
    }

    //    message ttl
    public static void main1(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        System.out.println("spring start");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        MessageProperties properties = new MessageProperties();
        properties.setExpiration("10000");
        rabbitTemplate.send("ttlexchange1", "", new Message("nihao".getBytes(), properties));
        Thread.sleep(50000);
        context.close();
    }
}
