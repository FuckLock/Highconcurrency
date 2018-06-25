package com.bao.miaosha.test.rabbitmq.javaClient.publishConfirm;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class PublishConfirmTest {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://guest:guest@localhost:5672");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.confirmSelect();
//        channel.txCommit();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("deliveryTag" + " " + deliveryTag);
                System.out.println("multiple" + " " + multiple);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("deliveryTag" + deliveryTag);
                System.out.println("multiple" + multiple);
            }
        });

        channel.basicPublish("order", "", new AMQP.BasicProperties().builder().deliveryMode(2).build(), "nihao".getBytes());
//        channel.waitForConfirms()
//        channel.waitForConfirmsOrDie();
        channel.basicPublish("order", "", new AMQP.BasicProperties().builder().deliveryMode(2).build(), "nihao".getBytes());
        Thread.sleep(5000);
        channel.close();
        connection.close();
    }

}
