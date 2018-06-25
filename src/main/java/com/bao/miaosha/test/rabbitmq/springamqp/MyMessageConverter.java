package com.bao.miaosha.test.rabbitmq.springamqp;


import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class MyMessageConverter implements MessageConverter {
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        return  new Message(o.toString().getBytes(), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        String ContentType = message.getMessageProperties().getContentType();
        if (StringUtils.isNotBlank(ContentType)){
            if (ContentType.contains("text")){
                return  new String(message.getBody());
            }
        }
        return message.getBody();
    }
}
