package com.bao.miaosha.test.rabbitmq.springamqp.annotationRabbitlisten;


import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class MyHandle {

    //    @RabbitListener( queues = {"dianhua"} )
    public void dianhua(byte[] body) {
        System.out.println("===============dianhua(byte[] body)===============");
        System.out.println(new String(body));
    }

    //    @RabbitListener( queues = {"dianhua"} )
    public void dianhua1(@Payload byte[] body, @Headers Map<String, Object> headers) {
        System.out.println("===============dianhua(byte[] body)===============");
        System.out.println(new String(body));
        System.out.println(headers.toString());
    }

    //    @RabbitListener( queues = {"dianhua"} )
    public void dianhua2(@Payload byte[] body, @Header String filename) {
        System.out.println("===============dianhua(byte[] body)===============");
        System.out.println(new String(body));
        System.out.println(filename);
    }

//    @RabbitListener(queues = {"dianhua"})
    public void dianhua3(@Payload byte[] body, @Header(value = "filename", defaultValue = "2.png") String filename) {
        System.out.println("===============dianhua(byte[] body)===============");
        System.out.println(new String(body));
        System.out.println(filename);
    }

//    @RabbitListener(queues = {"dianhua"})
    public void dianhua4(@Payload byte[] body, @Header(value = "filename", required = false) String filename) {
        System.out.println("===============dianhua(byte[] body)===============");
        System.out.println(new String(body));
        System.out.println(filename);
    }

//    @RabbitListener(queues = {"dianhua"})
    public void dianhua5(Message message) {
        System.out.println("===============dianhua(byte[] body)===============");
        System.out.println(new String((byte[]) message.getPayload()));
        System.out.println(message.getHeaders());
    }

//    @RabbitListener(queues = {"dianhua", "weixing"})
    public void dianhua6(Message message) {
        System.out.println("===============dianhua(byte[] body)===============");
        System.out.println(new String((byte[]) message.getPayload()));
        System.out.println(message.getHeaders());
    }

//    @RabbitListener(queues = {"${dianhuaQue}", "${weixingQue}"})
    public void dianhua7(Message message) {
        System.out.println("===============dianhua(byte[] body)===============");
        System.out.println(new String((byte[]) message.getPayload()));
        System.out.println(message.getHeaders());
    }

//    @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = "recive", durable = "true"), exchange = @Exchange(value = "send"), key = "se-re")})
    public void dianhua8(Message message) {
        System.out.println("===============dianhua(byte[] body)===============");
        System.out.println(new String((byte[]) message.getPayload()));
        System.out.println(message.getHeaders());
    }
}
