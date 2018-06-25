package com.bao.miaosha.test.rabbitmq.springamqp.deadLetterExchange;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class App {

    //    queue x-max-length-bytes超了会发送到dead exchange
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        rabbitTemplate.send("", "deadque6", new Message("11".getBytes(), new MessageProperties()));
        rabbitTemplate.send("", "deadque6", new Message("22".getBytes(), new MessageProperties()));
        rabbitTemplate.send("", "deadque6", new Message("33".getBytes(), new MessageProperties()));
        rabbitTemplate.send("", "deadque6", new Message("4444".getBytes(), new MessageProperties()));
        rabbitTemplate.send("", "deadque6", new Message("666666".getBytes(), new MessageProperties()));
        context.close();
    }

//    queue x-max-length超了会发送到dead exchange
    public static void main3(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        rabbitTemplate.send("", "deadque5", new Message("haha".getBytes(), new MessageProperties()));
        rabbitTemplate.send("", "deadque5", new Message("haha".getBytes(), new MessageProperties()));
        rabbitTemplate.send("", "deadque5", new Message("haha".getBytes(), new MessageProperties()));
        rabbitTemplate.send("", "deadque5", new Message("haha".getBytes(), new MessageProperties()));
        rabbitTemplate.send("", "deadque5", new Message("haha".getBytes(), new MessageProperties()));
        context.close();
    }

//    消息过期ttl 测试前要把consumer注释掉
    public static void main2(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        MessageProperties properties = new MessageProperties();
        properties.setExpiration("5000");
        rabbitTemplate.send("", "deadque2", new Message("dea1".getBytes(), properties));
    }

//    消息拒绝进入dead  letter exchage
    public static void main1(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
//      rabbitTemplate.send("", "deadque1", new Message("dea1".getBytes(), new MessageProperties()));
        rabbitTemplate.send("", "deadque2", new Message("dea1".getBytes(), new MessageProperties()));
        Thread.sleep(5000);
        context.close();
    }
}
