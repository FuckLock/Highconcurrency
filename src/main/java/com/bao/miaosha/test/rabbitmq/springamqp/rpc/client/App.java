package com.bao.miaosha.test.rabbitmq.springamqp.rpc.client;

import com.bao.miaosha.test.rabbitmq.springamqp.rpc.server.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.UUID;

@ComponentScan
public class App {

    public static void main(String[] args) throws InterruptedException, IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        rabbitTemplate.setReplyTimeout(20000);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        rabbitAdmin.declareQueue(new Queue("casque1", true, false, false, null));
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        User user = new User();
        user.setId(1);
        user.setName("大哥");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(user);
        Message message = rabbitTemplate.sendAndReceive("", "casque1", new Message(bytes, properties), new CorrelationData(UUID.randomUUID().toString()));
        System.out.println("================收到服务器端的确认了=============================");
        System.out.println(new String(message.getBody()));
        System.out.println(message.getMessageProperties());
    }

    public static void main1(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        rabbitTemplate.setReplyTimeout(20000);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        rabbitAdmin.declareQueue(new Queue("casque1", true, false, false, null));
        MessageProperties properties = new MessageProperties();
        Message message = rabbitTemplate.sendAndReceive("", "casque1", new Message("你好，你中大奖了，请马上来领奖".getBytes(), properties), new CorrelationData(UUID.randomUUID().toString()));
        System.out.println("================收到服务器端的确认了=============================");
        System.out.println(new String(message.getBody()));
        System.out.println(message.getMessageProperties());
    }
}
