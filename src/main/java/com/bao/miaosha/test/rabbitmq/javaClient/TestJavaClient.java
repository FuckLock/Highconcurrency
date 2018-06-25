package com.bao.miaosha.test.rabbitmq.javaClient;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class TestJavaClient {

    private static Logger logger = LoggerFactory.getLogger(TestJavaClient.class);

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare("bao1", BuiltinExchangeType.DIRECT.getType());
            channel.exchangeDeclare("bao2", BuiltinExchangeType.TOPIC.getType());
            channel.exchangeDeclare("bao3", BuiltinExchangeType.FANOUT.getType());
            channel.exchangeDeclare("bao4", BuiltinExchangeType.HEADERS.getType());

            channel.exchangeDeclare("bao5", BuiltinExchangeType.DIRECT.getType(), true);
            channel.exchangeDeclare("bao6", BuiltinExchangeType.DIRECT.getType(), false);

            Map<String, Object>  map = new HashMap<String, Object>();
            channel.exchangeDeclare("bao7", BuiltinExchangeType.DIRECT.getType(), true, true, map);
            channel.exchangeDeclare("bao8", BuiltinExchangeType.DIRECT.getType(), true, false, map);

            channel.queueDeclare("baoque1", true, false, false, null);
            channel.queueDeclare("baoque2", false, false, true, null);

//            channel.queueDeclare("baoque3", true, true, true, null);
//            Thread.sleep(10000);
            channel.queueDeclare("baoque4", true, false, false, null);

            channel.queueBind("baoque1", "bao1", "bao1-baoque1");
            channel.exchangeBind("bao1", "bao2", "bao1-bao2");

            AMQP.BasicProperties props = new AMQP.BasicProperties().builder().deliveryMode(2).contentEncoding("UTF-8").build();
            channel.basicPublish("bao1","bao1-baoque1", props, "撒风的".getBytes());
            channel.basicPublish("bao1","bao1-baoque1", props, "大哥说".getBytes());

//            channel.basicConsume("baoque1", new SimpleConsume(channel));
             channel.basicConsume("baoque1", true, new SimpleConsume(channel));

            Thread.sleep(10000);

        } catch (IOException e) {
            logger.error("connection error", e);
        } catch (TimeoutException e) {
            logger.error("connection timeout error", e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}




