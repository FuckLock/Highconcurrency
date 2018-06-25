package com.bao.miaosha.test.rabbitmq.springamqp.alternateExchange;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri("amqp://guest:guest@localhost");
        factory.setPublisherReturns(true);
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Exchange createExchange1(){
        Map<String, Object> map = new HashMap<>();
        map.put("alternate-exchange", "error");
        return new DirectExchange("log", true, false, map);
    }

    @Bean
    public Exchange createExchange2(){
        Map<String, Object> map = new HashMap<>();
        map.put("alternate-exchange", "error");
        return new FanoutExchange("error", true, false);
    }


    @Bean
    public Queue queue1(){
        return new Queue("logque", true);
    }

    @Bean
    public Queue queue2(){
        return new Queue("errorque", true);
    }

    @Bean
    public Binding createBinding1(){
        return new Binding("logque", Binding.DestinationType.QUEUE, "log", "log-que", null);
    }

    @Bean
    public Binding createBinding2(){
        return new Binding("errorque", Binding.DestinationType.QUEUE, "error", "", null);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("message" + new String(message.getBody()));
                System.out.println("replyCode" + replyCode);
                System.out.println("replyText" + replyText);
                System.out.println("exchange" + exchange);
                System.out.println("routingKey" + routingKey);
            }
        });
        return rabbitTemplate;
    }
}
