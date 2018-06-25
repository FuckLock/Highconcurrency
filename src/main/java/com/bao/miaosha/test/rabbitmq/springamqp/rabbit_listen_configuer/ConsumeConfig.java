package com.bao.miaosha.test.rabbitmq.springamqp.rabbit_listen_configuer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.*;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumeConfig {

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        return containerFactory;
    }

    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(){
        return new RabbitListenerConfigurer() {
            @Override
            public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
                SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
                endpoint.setId("1");
                endpoint.setQueueNames("duanxin");
                endpoint.setMessageListener(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        System.out.println("======RabbitListenerConfigurer of messageListen======");
                        System.out.println(message.getMessageProperties());
                        System.out.println(new String(message.getBody()));
                    }
                });

                SimpleRabbitListenerEndpoint endpoint1 = new SimpleRabbitListenerEndpoint();
                endpoint1.setId("2");
                endpoint1.setQueueNames("weixing");
                endpoint1.setMessageListener(new MessageListenerAdapter(new MyHandle()));
                registrar.registerEndpoint(endpoint1);
                registrar.registerEndpoint(endpoint);
            }
        };
    }
}
