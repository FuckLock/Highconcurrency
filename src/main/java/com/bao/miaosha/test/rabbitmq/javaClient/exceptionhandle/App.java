package com.bao.miaosha.test.rabbitmq.javaClient.exceptionhandle;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.DefaultExceptionHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class App {

//handleConfirmListenerException
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://guest:guest@localhost:5672");
        connectionFactory.setExceptionHandler(new DefaultExceptionHandler(){
            @Override
            public void handleConfirmListenerException(Channel channel, Throwable exception) {
                exception.printStackTrace();
            }
        });
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                channel.basicAck(deliveryTag, false);
                throw new NullPointerException("dsfdsfds");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {

            }
        });
        channel.basicPublish("", "exceptionque1", new AMQP.BasicProperties(), "你好啊".getBytes());
        Thread.sleep(5000);
        channel.close();
        connection.close();

    }


//    重新定制handleConsumerException异常，一定要在创建连接之前，要不然还是默认的
    public static void main2(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://guest:guest@localhost:5672");
        connectionFactory.setExceptionHandler(new DefaultExceptionHandler(){
            @Override
            public void handleConsumerException(Channel channel, Throwable exception, Consumer consumer, String consumerTag, String methodName) {
                exception.printStackTrace();
//                super.handleConsumerException(channel, exception, consumer, consumerTag, methodName);
            }
        });
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        channel.basicPublish("", "exceptionque1", new AMQP.BasicProperties(), "你好啊".getBytes());
        channel.basicConsume("exceptionque1", false, new Myhandle(channel));
        Thread.sleep(5000);
        channel.close();
        connection.close();

    }

    //默认在handleDeliver抛出异常的时候会关闭channnel.这里在关闭会出现异常
    public static void main1(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://guest:guest@localhost:5672");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.basicPublish("", "exceptionque1", new AMQP.BasicProperties(), "你好啊".getBytes());
        channel.basicConsume("exceptionque1", false, new Myhandle(channel));
        Thread.sleep(5000);
        channel.close();
    }
}
