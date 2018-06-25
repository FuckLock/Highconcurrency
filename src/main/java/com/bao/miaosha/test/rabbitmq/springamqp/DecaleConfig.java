package com.bao.miaosha.test.rabbitmq.springamqp;

import com.alibaba.druid.sql.visitor.functions.Bin;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DecaleConfig {

    @Bean
    public Exchange directExchange(){
        return new DirectExchange("direact1", true, false);
    }

    @Bean
    public Exchange topicExchange(){
        return new TopicExchange("topic1", true, false);
    }

    @Bean
    public Exchange fannoutExchange(){
        return  new FanoutExchange("fanout1", true, false);
    }

    @Bean
    public Exchange headerExchange(){
        return  new HeadersExchange("header1", true, false);
    }

    @Bean
    public Queue queue1(){
        return new Queue("zhidongque1", true);
    }

    @Bean
    public Queue queue2(){
        return new Queue("zhidongque2", true);
    }

    @Bean
    public Queue queue3(){
        return new Queue("zhidongque3", true);
    }
    
    @Bean
    public Queue queue4(){
        return new Queue("zhidongque4", true);
    }

    @Bean
    public Binding binding1(){
        return new Binding("zhidongque1", Binding.DestinationType.QUEUE, "direact1", "bind1", null);
    }

    @Bean
    public Binding binding2(){
        return new Binding("zhidongque2", Binding.DestinationType.QUEUE, "topic1", "bind2", null);
    }

    @Bean
    public Binding binding3(){
        return new Binding("zhidongque3", Binding.DestinationType.QUEUE, "fanout1", "", null);
    }

    @Bean
    public Binding binding4(){
        Map<String, Object> map = new HashMap<>();
        map.put("x-match", "any");
        map.put("size", 1);
        return new Binding("zhidongque4", Binding.DestinationType.QUEUE, "header1", "", map);
    }

    @Bean
    public List<Queue> queueList(){
        List<Queue> list = new ArrayList<>();
        list.add(new Queue("listqueue1", true));
        list.add(new Queue("listqueue2", true));
        list.add(new Queue("listqueue3", true));
        list.add(new Queue("listqueue4", true));
        return list;
    }

    @Bean
    public List<Exchange> exchangeList(){
        List<Exchange> list = new ArrayList<>();
        list.add(new DirectExchange("listdirect1", true, false));
        list.add(new TopicExchange("listtopic1", true, true));
        list.add(new FanoutExchange("listfanout1", true, true));
        list.add(new HeadersExchange("listheader1", true, true));
        return list;
    }

    @Bean
    public List<Binding> bindingList(){
        List<Binding> list = new ArrayList<>();
        list.add(new Binding("listqueue1", Binding.DestinationType.QUEUE, "listdirect1", "xian1", null));
        list.add(new Binding("listqueue2", Binding.DestinationType.QUEUE, "listtopic1", "#", null));
        list.add(new Binding("listqueue3", Binding.DestinationType.QUEUE, "listfanout1", "", null));
        Map<String, Object> map = new HashMap<>();
        map.put("x-match", "all");
        map.put("size", 2);
        list.add(new Binding("listqueue4", Binding.DestinationType.QUEUE, "listheader1", "", map));
        return list;
    }
}
