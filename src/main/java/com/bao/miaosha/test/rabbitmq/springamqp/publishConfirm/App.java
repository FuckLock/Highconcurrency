package com.bao.miaosha.test.rabbitmq.springamqp.publishConfirm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;

@ComponentScan
public class App {

    public static Order createOrder(){
        Order order = new Order();
        order.setId(1);
        order.setProductId(1);
        order.setUserId(1);
        order.setPrice(11.22);
        order.setCreateTime(new Date());
        return  order;
    }

    public static  void  save(Order order){
        System.out.println("===============保存订单到数据库成功==================");
    }

    public static void main(String[] args) throws JsonProcessingException, InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        System.out.println("spring start success");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        Order order = createOrder();
        save(order);
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(order);
        rabbitTemplate.send("order", "", new Message(bytes, new MessageProperties()), new CorrelationData(String.valueOf(order.getId())));
        Thread.sleep(5000);
        context.close();
    }
}
