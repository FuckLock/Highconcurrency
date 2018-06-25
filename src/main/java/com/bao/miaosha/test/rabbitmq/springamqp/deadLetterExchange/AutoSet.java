package com.bao.miaosha.test.rabbitmq.springamqp.deadLetterExchange;

import com.alibaba.druid.sql.visitor.functions.Bin;
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
        map.put("x-dead-letter-exchange", "deadexchange1");
        return new Queue("deadque1", true, false, false, map);
    }

    @Bean
    public Queue queue2(){
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", "deadexchange2");
        map.put("x-dead-letter-routing-key", "deadkey2");
        return new Queue("deadque2", true, false, false, map);
    }

    @Bean
    public Queue queue3(){
        return new Queue("deadque3", true, false, false, null);
    }

    @Bean
    public Queue queue4(){
        return new Queue("deadque4", true, false, false, null);
    }

    @Bean
    public Queue queue5(){
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", "deadexchange2");
        map.put("x-dead-letter-routing-key", "deadkey2");
        map.put("x-max-length", 3);
        return new Queue("deadque5", true, false, false, map);
    }

    @Bean
    public Queue queue6(){
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", "deadexchange2");
        map.put("x-dead-letter-routing-key", "deadkey2");
        map.put("x-max-length-bytes", 10);
        return new Queue("deadque6", true, false, false, map);
    }


    @Bean
    public Exchange exchange1(){
        return new DirectExchange("deadexchange1", true, false);
    }

    @Bean
    public Exchange exchange2(){
        return new DirectExchange("deadexchange2", true, false);
    }


    @Bean
    public Binding binding1(){
        return new Binding("deadque3", Binding.DestinationType.QUEUE, "deadexchange1",  "deadexchange1-deadque3", null);
    }

    @Bean
    public Binding binding3(){
        return new Binding("deadque4", Binding.DestinationType.QUEUE, "deadexchange2",  "deadkey2", null);
    }

}
