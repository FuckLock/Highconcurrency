package com.bao.miaosha.test.rabbitmq.springamqp.annotationRabbitlisten;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableRabbit
@ComponentScan
@PropertySource("classpath:app.properties")
public class App {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        System.out.println("spring context start");
        Thread.sleep(5000);
        context.close();
    }
}
