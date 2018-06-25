package com.bao.miaosha.test.rabbitmq.javaClient.rpc.clent;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class MyConsumer extends DefaultConsumer {

    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("==================收到服务器端发送监听队列到信息=========================");
        System.out.println(new String(body));
        System.out.println(properties.getCorrelationId() + ", 服务器端对消息已经确认了。。。");
    }
}
