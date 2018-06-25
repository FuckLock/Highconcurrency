package com.bao.miaosha.test.rabbitmq.springamqp.queueLengthLImit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AutoSet {

    @Bean
    public Queue queue1(){
        Map<String, Object> map = new HashMap<>();
        map.put("x-max-length", 5);
        return new Queue("limitque1", true, false, false, map);
    }

    @Bean
    public Queue queue2(){
        Map<String, Object> map = new HashMap<>();
        map.put("x-max-length-bytes", 10);
        return new Queue("limitque2", true, false, false, map);
    }

}
