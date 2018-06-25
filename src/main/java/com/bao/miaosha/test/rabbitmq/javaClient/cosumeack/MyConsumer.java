package com.bao.miaosha.test.rabbitmq.javaClient.cosumeack;

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
        System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝收到消息了＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        if (properties.getHeaders().get("error") != null){
            System.out.println("拒接消费");
            this.getChannel().basicNack(envelope.getDeliveryTag(), false, false);
//            this.getChannel().basicReject(envelope.getDeliveryTag(), false);
            return;
        }
        this.getChannel().basicAck(envelope.getDeliveryTag(), false);
        System.out.println("消息内容" + new String(body));
        System.out.println("消息属性" + properties);
        System.out.println("消费成功");
    }
}
