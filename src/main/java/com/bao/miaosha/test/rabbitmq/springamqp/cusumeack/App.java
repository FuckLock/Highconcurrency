package com.bao.miaosha.test.rabbitmq.springamqp.cusumeack;

import org.omg.PortableServer.THREAD_POLICY_ID;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class App {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        System.out.println("spring start succes");
        Thread.sleep(5000);
        context.close();
    }
}
