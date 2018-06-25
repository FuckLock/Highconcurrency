package com.bao.miaosha.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfiguration {

    public static final String ORDER_QUEUE = "miaoshaOrder";

    @Bean
    public Queue orderQueue(){
        return  new Queue(ORDER_QUEUE, true, false, false, null);
    }
}
