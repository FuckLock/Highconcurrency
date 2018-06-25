package com.bao.miaosha.test.rabbitmq.javaClient.rpc.clent;

import com.bao.miaosha.test.rabbitmq.javaClient.rpc.RpcUtil;
import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class RpcSend {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        Channel channel = RpcUtil.channel();
        String id = UUID.randomUUID().toString();
        String reply = "amq.rabbitmq.reply-to";
        channel.basicConsume(reply, true, new MyConsumer(channel));
        channel.basicPublish("sms", "smskey", new BasicProperties().builder().replyTo(reply).correlationId(id).build(), "你好，你中奖了".getBytes());

    }

    public static void main1(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        Channel channel = RpcUtil.channel();

        String id = UUID.randomUUID().toString();
        String reply = "smsreply";
        channel.queueDeclare("smsreply", true, false, true, null);
        channel.basicPublish("sms", "smskey", new BasicProperties().builder().replyTo(reply).correlationId(id).build(), "你好，你中奖了".getBytes());
        channel.basicConsume(reply, true, new MyConsumer(channel));

    }
}
