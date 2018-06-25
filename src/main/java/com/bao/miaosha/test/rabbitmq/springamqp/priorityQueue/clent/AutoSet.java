package com.bao.miaosha.test.rabbitmq.springamqp.priorityQueue.clent;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
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
        map.put("x-max-priority", 5);
        return new Queue("priorityque1", true, false, false, map);
    }

}
