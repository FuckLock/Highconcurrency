package com.bao.miaosha.test.rabbitmq.springamqp.exceptionhandle;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class AutoSet {

    @Bean
    public Queue queue1(){
      return  new Queue("exque1");
    }

    @Bean
    public Queue queue2(){
        return  new Queue("exque2");
    }

}
