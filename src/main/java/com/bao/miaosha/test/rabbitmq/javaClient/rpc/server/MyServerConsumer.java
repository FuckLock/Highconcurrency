package com.bao.miaosha.test.rabbitmq.javaClient.rpc.server;

import com.rabbitmq.client.*;

import java.io.IOException;

public class MyServerConsumer extends DefaultConsumer {
    private Channel channel;

    public MyServerConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("====================已经收到客户端的短信信息正在确认了=====================");
        channel.basicAck(envelope.getDeliveryTag(), false);
        System.out.println("====================消息确认成功,准备客户端发送确认消息=====================");
        channel.basicPublish("", properties.getReplyTo(), properties, "哇哇客户端真的中奖了".getBytes());
        System.out.println("======================发送消息给客户端成功==================================================");

    }
}
