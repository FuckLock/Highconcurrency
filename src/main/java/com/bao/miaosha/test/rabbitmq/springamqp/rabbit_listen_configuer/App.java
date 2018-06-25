package com.bao.miaosha.test.rabbitmq.springamqp.rabbit_listen_configuer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@EnableRabbit
@ComponentScan
public class App {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        System.out.println("spring start");
        Thread.sleep(5000);
        context.close();
    }
}
