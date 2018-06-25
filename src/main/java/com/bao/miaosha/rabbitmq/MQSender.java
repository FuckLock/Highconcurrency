package com.bao.miaosha.rabbitmq;

import com.bao.miaosha.util.ObjectMapperUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void  sendMessage(MiaoshaMessage message){
        String msg = ObjectMapperUtil.beanToString(message);
        rabbitTemplate.convertAndSend("", MQConfiguration.ORDER_QUEUE, msg);
    }
}
