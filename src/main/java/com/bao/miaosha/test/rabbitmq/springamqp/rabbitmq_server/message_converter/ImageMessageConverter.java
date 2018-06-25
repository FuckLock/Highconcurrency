package com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_server.message_converter;

import com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_server.util.CloseUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageMessageConverter implements MessageConverter {
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(o.toString().getBytes(), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        StringBuilder sb = new StringBuilder();
        String uuid = UUID.randomUUID().toString();
        String path = sb.append(uuid).append(".png").toString();
        File file = new File(path);
        try {
            Files.write(Paths.get(path), message.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


    public Object fromMessage1(Message message) throws MessageConversionException {
        StringBuilder sb = new StringBuilder();
        String uuid = UUID.randomUUID().toString();
        String path = sb.append(uuid).append(".png").toString();
        File file = new File(path);

        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
            bufferedOutputStream.write(message.getBody());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtil.close(bufferedOutputStream);
        }
        return file;
    }
}
