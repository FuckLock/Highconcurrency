package com.bao.miaosha.test.rabbitmq.javaClient.cosumeack;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class cosumeAckTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://guest:guest@localhost:5672");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.basicConsume("dianhua", false, new MyConsumer(channel));
        Thread.sleep(5000);
        channel.close();
    }
}
