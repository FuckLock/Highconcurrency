package com.bao.miaosha.test.rabbitmq.springamqp.exceptionhandle;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class App {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        System.out.println("spring start");
        context.getBean(RabbitAdmin.class).getQueueProperties("xx");
//        Thread.sleep(50000);
        context.close();
    }
}
