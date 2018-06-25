package com.bao.miaosha.test.rabbitmq.springamqp.cusumeack;

import com.rabbitmq.client.Channel;
import com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri("amqp://guest:guest@localhost");
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }

//    手动确认
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames("dianhua");
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                System.out.println("==============收到消息了，正在进行消息确认=====================");
//                if (message.getMessageProperties().getHeaders().get("error") != null){
////                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//                    System.out.println("==============消息错误，进行丢弃=====================");
//                } else {
//                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//                    System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝恭喜你消费成功了。＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
//                }
//            }
//        });
//        return container;
//    }


//    设置Auto方式 第一种：抛出异常会拒绝，然后requeue = true会死循环注意
//        @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames("dianhua");
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                System.out.println("===============收到消息了,正在进行消息确认==================");
//                throw new NoSuchThreadPoolException();
//            }
//        });
//        return container;
//    }

//    设置Auto方式 第二种：抛出异常会拒绝，设置setDefaultRequeueRejected ＝ false,则requeue=false，失败就丢弃
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames("dianhua");
//        container.setDefaultRequeueRejected(false);
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                System.out.println("===============收到消息了,正在进行消息确认==================");
//                throw new NoSuchThreadPoolException();
//            }
//        });
//        return container;
//    }

    //    设置Auto方式 第三种：抛出特殊异常会拒绝,则requeue=false，失败就丢弃
//        @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames("dianhua");
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                System.out.println("===============收到消息了,正在进行消息确认==================");
//                throw new AmqpRejectAndDontRequeueException("拒绝");
//            }
//        });
//        return container;
//    }

    //    设置Auto方式 第四种：抛出特殊异常会成功消费
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames("dianhua");
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                System.out.println("===============收到消息了,正在进行消息确认==================");
                throw new ImmediateAcknowledgeAmqpException("成功");
            }
        });
        return container;
    }



}
