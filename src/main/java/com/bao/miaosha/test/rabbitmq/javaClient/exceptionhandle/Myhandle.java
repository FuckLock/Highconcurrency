package com.bao.miaosha.test.rabbitmq.javaClient.exceptionhandle;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class Myhandle extends DefaultConsumer {
    private Channel channel;

    public Myhandle(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("===========收到消息正在确认==============");
        channel.basicAck(envelope.getDeliveryTag(), false);
        throw  new NullPointerException("控制是颠三倒四");
    }
}
