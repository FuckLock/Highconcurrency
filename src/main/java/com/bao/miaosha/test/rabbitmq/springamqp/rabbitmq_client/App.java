package com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@ComponentScan
public class App {

    private static AnnotationConfigApplicationContext context;

    static {
        context = new AnnotationConfigApplicationContext(App.class);
    }

    public static void  sendOrder() throws JsonProcessingException {
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        Order order = new Order(1, 1, 1, 11.22, new Date());
        ObjectMapper objectMapper = new ObjectMapper();
        String orderString = objectMapper.writeValueAsString(order);
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        rabbitTemplate.send("order", "", new Message(orderString.getBytes(), properties));
    }

    public static void  sendOrderList() throws JsonProcessingException {
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        Order order = new Order(1, 1, 1, 11.22, new Date());
        Order order1 = new Order(2, 2, 2, 11.22, new Date());
        List<Order> list = Arrays.asList(order, order1);
        ObjectMapper objectMapper = new ObjectMapper();
        String orderString = objectMapper.writeValueAsString(list);
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        rabbitTemplate.send("order", "", new Message(orderString.getBytes(), properties));
    }

    public static void  sendOrderWith__TypeId__() throws JsonProcessingException {
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        Order order = new Order(1, 1, 1, 11.22, new Date());
        ObjectMapper objectMapper = new ObjectMapper();
        String orderString = objectMapper.writeValueAsString(order);
        MessageProperties properties = new MessageProperties();
//        properties.getHeaders().put("__TypeId__", "com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_server.Order");
        properties.getHeaders().put("__TypeId__", "order");
        properties.setContentType("application/json");
        rabbitTemplate.send("order", "", new Message(orderString.getBytes(), properties));
    }

    public static void  sendOrderListWith__TypeId__And__ContentTypeId__() throws JsonProcessingException {
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        Order order = new Order(1, 1, 1, 11.22, new Date());
        Order order1 = new Order(2, 2, 2, 11.22, new Date());
        List<Order> list = Arrays.asList(order, order1);
        ObjectMapper objectMapper = new ObjectMapper();
        String orderString = objectMapper.writeValueAsString(list);
        MessageProperties properties = new MessageProperties();
//        properties.getHeaders().put("__TypeId__", "com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_server.Order");
        properties.getHeaders().put("__TypeId__", "java.util.List");
        properties.getHeaders().put("__ContentTypeId__", "com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_server.Order");
        properties.setContentType("application/json");
        rabbitTemplate.send("order", "", new Message(orderString.getBytes(), properties));
    }

    public static void  sendOrderMapWith__TypeId__And__ContentTypeId__And__KeyTypeId__() throws JsonProcessingException {
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        Order order = new Order(1, 1, 1, 11.22, new Date());
        Order order1 = new Order(2, 2, 2, 11.22, new Date());
        Map<String, Order> map = new HashMap<>();
        map.put("10", order);
        map.put("20", order1);
        ObjectMapper objectMapper = new ObjectMapper();
        String orderString = objectMapper.writeValueAsString(map);
        MessageProperties properties = new MessageProperties();
//        properties.getHeaders().put("__TypeId__", "com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_server.Order");
        properties.getHeaders().put("__TypeId__", "java.util.Map");
        properties.getHeaders().put("__ContentTypeId__", "order");
        properties.getHeaders().put("__KeyTypeId__", "java.lang.String");
        properties.setContentType("application/json");
        rabbitTemplate.send("order", "", new Message(orderString.getBytes(), properties));
    }

    public static void  sendText(){
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        MessageProperties properties = new MessageProperties();
        properties.setContentType("text");
        rabbitTemplate.convertAndSend("order", "", new Message("你么好啊".getBytes(), properties));
    }

    public static void  sendImage() throws IOException {
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

//        StringBuilder sb = new StringBuilder();
//        BufferedInputStream buff = new BufferedInputStream(new FileInputStream("/Users/baodong/rails_projects/miaosha/a.png"));
//        byte[] bytes = new byte[102];
//        int len = 0;
//        while((len = buff.read(bytes)) != -1){
//            sb.append(new String(bytes, 0, len));
//        }
//        String content = sb.toString();

//        StringBuilder builder = new StringBuilder();
//        BufferedReader bufferedReader = null;
//        try {
//            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/baodong/Desktop/img/bomb_0.gif")));
//            String line = null;
//            while ((line = bufferedReader.readLine()) != null){
//                builder.append(line);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            CloseUtil.close(bufferedReader);
//        }
//
//        String content = builder.toString();

//        StringBuilder builder = new StringBuilder();
//
//        try {
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
//            String line = null;
//            while ((line = bufferedReader.readLine()) != null){
//                builder.append(line);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            CloseUtil.close(bufferedReader);
//        }
//
//        String content = builder.toString();

        byte[] content = Files.readAllBytes(Paths.get("/Users/baodong/rails_projects/miaosha", "a.png"));
        MessageProperties properties = new MessageProperties();
        properties.setContentType("image");
        rabbitTemplate.convertAndSend("order", "", new Message(content, properties));
    }

    public static void main(String[] args) throws Exception {
//        App.sendOrder();
//        App.sendOrderList();
//        App.sendOrderWith__TypeId__();
//        App.sendOrderListWith__TypeId__And__ContentTypeId__();
//        App.sendOrderMapWith__TypeId__And__ContentTypeId__And__KeyTypeId__();
//        App.sendText();
        App.sendImage();
        context.close();
    }
}
