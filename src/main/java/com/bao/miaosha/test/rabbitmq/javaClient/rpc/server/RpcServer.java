package com.bao.miaosha.test.rabbitmq.javaClient.rpc.server;

import com.bao.miaosha.test.rabbitmq.javaClient.rpc.RpcUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RpcServer {

    public static void main(String[] args) throws URISyntaxException, IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException {
        Channel channel = RpcUtil.channel();
        channel.basicConsume("smsque1", false, new MyServerConsumer(channel));
        System.out.println("server start");
    }
}
