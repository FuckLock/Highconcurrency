package com.bao.miaosha.test.rabbitmq.springamqp.deadLetterExchange;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Component
public class Consumer {

//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames("deadque1", "deadque2");
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                System.out.println("=======================正在消费＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
//                System.out.println(new String(message.getBody()));
//                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//                return;
//            }
//        });
//        return container;
//    }

}
