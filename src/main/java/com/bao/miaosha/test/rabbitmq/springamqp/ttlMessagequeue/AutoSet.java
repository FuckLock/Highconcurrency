package com.bao.miaosha.test.rabbitmq.springamqp.ttlMessagequeue;

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
        return new Queue("ttlque1", true);
    }

    @Bean
    public Queue queue2(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 10000);
        return new Queue("ttlque2", true, false,false, arguments);
    }

    @Bean
    public Exchange exchange1(){
        return new FanoutExchange("ttlexchange1", true, false);
    }

    @Bean
    public Exchange exchange2(){
        return new FanoutExchange("ttlexchange2", true, false);
    }

    @Bean
    public Binding binding1(){
        return new Binding("ttlque1", Binding.DestinationType.QUEUE, "ttlexchange1", "", null);
    }

    @Bean
    public Binding binding2(){
        return new Binding("ttlque2", Binding.DestinationType.QUEUE, "ttlexchange2", "", null);
    }
}
