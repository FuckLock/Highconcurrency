package com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_server;

import com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_server.message_converter.ImageMessageConverter;
import com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_server.message_converter.TextMessageConverter;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

@ComponentScan
public class App {

    private static AnnotationConfigApplicationContext context;
//    private static SimpleMessageListenerContainer container;

    static {
        context = new AnnotationConfigApplicationContext(App.class);
//        container = context.getBean(SimpleMessageListenerContainer.class);
//        container.setAutoStartup(false);
    }

    public static void receiveOrder() throws InterruptedException {
        SimpleMessageListenerContainer container = context.getBean(SimpleMessageListenerContainer.class);
        container.setAutoStartup(false);
        container.setQueueNames("dianhua");
        MessageListenerAdapter adapter = new MessageListenerAdapter(new OrderHandle());

        Map<String, String> map = new HashMap<>();
        map.put("dianhua", "sendDianhua");
        adapter.setQueueOrTagToMethodName(map);
        adapter.setMessageConverter(new Jackson2JsonMessageConverter());
        container.setMessageListener(adapter);
        container.start();
        Thread.sleep(5000);
        container.stop();
    }

    public static void receiveOrderList() throws InterruptedException {
        SimpleMessageListenerContainer container = context.getBean(SimpleMessageListenerContainer.class);
        container.setAutoStartup(false);
        container.setQueueNames("dianhua");
        MessageListenerAdapter adapter = new MessageListenerAdapter(new OrderHandle());
        Map<String, String> map = new HashMap<>();
        map.put("dianhua", "sendDianhua");
        adapter.setQueueOrTagToMethodName(map);
        adapter.setMessageConverter(new Jackson2JsonMessageConverter());
        container.setMessageListener(adapter);
        container.start();
        Thread.sleep(5000);
        container.stop();
    }

    public static void receiveOrderWith__TypeId__() throws InterruptedException {
        SimpleMessageListenerContainer container = context.getBean(SimpleMessageListenerContainer.class);
        container.setAutoStartup(false);
        container.setQueueNames("dianhua");
        MessageListenerAdapter adapter = new MessageListenerAdapter(new OrderHandle());
        Map<String, String> map = new HashMap<>();
        map.put("dianhua", "sendDianhua");
        adapter.setQueueOrTagToMethodName(map);

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        Map<String, Class<?>> map1 = new HashMap<>();
        map1.put("order", Order.class);
        typeMapper.setIdClassMapping(map1);
        converter.setJavaTypeMapper(typeMapper);
        adapter.setMessageConverter(converter);
        container.setMessageListener(adapter);

        container.start();
        Thread.sleep(5000);
        container.stop();
    }

    public static void receiveOrderWith__TypeId__And__ContentTypeId__() throws InterruptedException {
        SimpleMessageListenerContainer container = context.getBean(SimpleMessageListenerContainer.class);
        container.setAutoStartup(false);
        container.setQueueNames("dianhua");
        MessageListenerAdapter adapter = new MessageListenerAdapter(new OrderHandle());
        Map<String, String> map = new HashMap<>();
        map.put("dianhua", "sendDianhua");
        adapter.setQueueOrTagToMethodName(map);

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
//        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
//        Map<String, Class<?>> map1 = new HashMap<>();
//        map1.put("list", Order.class);
//        typeMapper.setIdClassMapping(map1);
//        converter.setJavaTypeMapper(typeMapper);
        adapter.setMessageConverter(converter);
        container.setMessageListener(adapter);

        container.start();
        Thread.sleep(5000);
        container.stop();
    }

    public static void receiveOrderMapWith__TypeId__And__ContentTypeId__And__KeyTypeId__() throws InterruptedException {
        SimpleMessageListenerContainer container = context.getBean(SimpleMessageListenerContainer.class);
        container.setAutoStartup(false);
        container.setQueueNames("dianhua");
        MessageListenerAdapter adapter = new MessageListenerAdapter(new OrderHandle());
        Map<String, String> map = new HashMap<>();
        map.put("dianhua", "sendDianhua");
        adapter.setQueueOrTagToMethodName(map);

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        Map<String, Class<?>> map1 = new HashMap<>();
        map1.put("order", Order.class);
        typeMapper.setIdClassMapping(map1);
        converter.setJavaTypeMapper(typeMapper);
        adapter.setMessageConverter(converter);
        container.setMessageListener(adapter);

        container.start();
        Thread.sleep(5000);
        container.stop();
    }

    public static void receiveDelefateContenttype() throws InterruptedException {
        SimpleMessageListenerContainer container = context.getBean(SimpleMessageListenerContainer.class);
        container.setAutoStartup(false);
        container.setQueueNames("dianhua");
        MessageListenerAdapter adapter = new MessageListenerAdapter(new OrderHandle());
        Map<String, String> map = new HashMap<>();
        map.put("dianhua", "sendDianhua");
        adapter.setQueueOrTagToMethodName(map);
        ContentTypeDelegatingMessageConverter delegatingMessageConverter = new ContentTypeDelegatingMessageConverter();
        delegatingMessageConverter.addDelegate("text", new TextMessageConverter());
        delegatingMessageConverter.addDelegate("image", new ImageMessageConverter());
        adapter.setMessageConverter(delegatingMessageConverter);
        container.setMessageListener(adapter);
        container.start();
        Thread.sleep(5000);
        container.stop();
    }

    public static void main(String[] args) throws InterruptedException {
//        App.receiveOrder();
//        App.receiveOrderList();
//        App.receiveOrderWith__TypeId__();
//        App.receiveOrderWith__TypeId__And__ContentTypeId__();
//        App.receiveOrderMapWith__TypeId__And__ContentTypeId__And__KeyTypeId__();
        App.receiveDelefateContenttype();
        context.close();

    }
}
