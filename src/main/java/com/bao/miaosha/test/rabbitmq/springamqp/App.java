package com.bao.miaosha.test.rabbitmq.springamqp;

import com.bao.miaosha.util.ObjectMapperUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ComponentScan
public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * 处理json数据
     * @param args
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // 发送对象转化为json
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
//        RabbitTemplate rabbit = context.getBean(RabbitTemplate.class);
//        Order order = new Order(1, 1, 1, 11.22, new Date());
//        ObjectMapper objectMapper = new ObjectMapper();
//        String str = objectMapper.writeValueAsString(order);
//        rabbit.convertAndSend("dianhua", str);

        // 读取json
        SimpleMessageListenerContainer container = context.getBean(SimpleMessageListenerContainer.class);
        container.setQueueNames("dianhua");
        MessageListenerAdapter adapter = new MessageListenerAdapter(new OrderHandle());
        adapter.setMessageConverter(new Jackson2JsonMessageConverter());
        Map<String, String> map = new HashMap<>();
        map.put("dianhua", "sendDianhua");
        adapter.setQueueOrTagToMethodName(map);
        container.setMessageListener(adapter);

        Thread.sleep(20000);
        container.stop();
        context.close();
    }

    /**
     *SimpleMessageListenerContainer 使用messageListenAdpter来处理message
     * @param args
     * @throws InterruptedException
     */
    public static void main6(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        SimpleMessageListenerContainer container = context.getBean(SimpleMessageListenerContainer.class);
        container.setQueueNames("dianhua", "weixing", "duanxin");
        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageHandle());
        Map<String, String> map = new HashMap<>();
        map.put("dianhua", "sendDianhua");
        map.put("weixing", "sendWeixing");
        map.put("duanxin", "sendDianhua");
        adapter.setQueueOrTagToMethodName(map);
        adapter.setMessageConverter(new MyMessageConverter());
        container.setMessageListener(adapter);

        Thread.sleep(20000);
        context.close();
    }

    /**
     *SimpleMessageListenerContainer 对queue进行监听，使用MessageListener监听message
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main5(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        /**
         * 自己管理SimpleMessageListenerContainer
         */
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(context.getBean(ConnectionFactory.class));
//        container.setQueues(new Queue("que2"));
//        container.setAutoStartup(false);
//        container.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                System.out.println("-=--===============");
//                System.out.println(message.getMessageProperties());
//                System.out.println(new String(message.getBody()));
//            }
//        });
//        container.start();
//        container.stop();

        /**
         * 消费多个队列
         */
        SimpleMessageListenerContainer container = context.getBean(SimpleMessageListenerContainer.class);
        //设置consumerTag必须在设置队列之前设置， 经测试证明

        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            int count = 0;
            @Override
            public String createConsumerTag(String s) {
                return "order" + s + "" + (++count);
            }
        });

        Map<String, Object> map = new HashMap<>();
        map.put("module", "order");
        container.setConsumerArguments(map);
        container.setConcurrentConsumers(4);
        container.setQueueNames("weixing", "dianhua", "duanxin");
        container.setAfterReceivePostProcessors(new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                StringBuilder sb = new StringBuilder();
                message.getMessageProperties().getHeaders().put("desc", "进行处理的message");
                return new Message(sb.append("处理过的消息题").append(new String(message.getBody())).toString().getBytes(), message.getMessageProperties());
            }
        });
        container.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println(message.getMessageProperties());
                System.out.println("-================收到队列＝＝＝＝＝＝＝＝＝＝＝＝" + message.getMessageProperties().getConsumerQueue() + "的消息");
                System.out.println(new String(message.getBody()));
                if ("dianhua".equals(message.getMessageProperties().getConsumerQueue())){
                    System.out.println("打电话");
                } else if ("weixing".equals(message.getMessageProperties().getConsumerQueue())){
                    System.out.println("发微信");
                } else if ("duanxin".equals(message.getMessageProperties().getConsumerQueue())){
                    System.out.println("发短信");
                }
            }
        });

        Thread.sleep(20000);
        container.addQueueNames("qq");
        context.close();
    }

    /**
     *
     * rabbitmq 的两种发送方式
     * @param args
     */
    public static void main4(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        /**
         * 利用rabbitmq的send方法发送信息
         */
//        MessageProperties mp = new MessageProperties();
//        rabbitTemplate.send("que2", new Message("world".getBytes(), mp));
//        rabbitTemplate.send("bao2", "bao2-que2", new Message("hello".getBytes(), mp));
//        rabbitTemplate.send("bao2", "bao2-que2", new Message("222".getBytes(), mp), new CorrelationData());


        /**
         *
         *利用rabbitmq的convertAndSend发送信息
         */
        rabbitTemplate.convertAndSend("que2", "你好啊");
        rabbitTemplate.convertAndSend("bao2", "bao2-que2", "草鸡啊");
        rabbitTemplate.convertAndSend("que2", "大哥我在这".getBytes(), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                StringBuilder sb = new StringBuilder();
                String body = new String(message.getBody());
                return new Message(sb.append("hehehe").append(body).toString().getBytes(), new MessageProperties());
            }
        });

        rabbitTemplate.convertAndSend("bao2", "bao2-que2", "小弟你在哪额", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                StringBuilder sb = new StringBuilder();
                String body = new String(message.getBody());
                return new Message(sb.append(body).append("aaaa====我在这里啊").toString().getBytes(), new MessageProperties());
            }
        });
    }

    /**
     *
     * 测试自动创建通过DecaleConfig文件来创建exchange, queue, binding
     * @param args
     */
    public static void main3(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        context.getBean(RabbitAdmin.class).getQueueProperties("xx");
    }

    /**
     * 测试通过rabbitAdmin来创建exchange, queue, binding
     * @param args
     */
    public static void main2(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        RabbitAdmin rabbit = context.getBean(RabbitAdmin.class);
        rabbit.declareExchange(new DirectExchange("bao1"));
        rabbit.declareExchange(new DirectExchange("bao2", true, true));
        Map<String, Object> map = new HashMap<>();
        map.put("alternate-exchange", "my-ae");
        rabbit.declareExchange(new DirectExchange("bao3", true, false, map));

        rabbit.declareExchange(new TopicExchange("bao4"));
        rabbit.declareExchange(new TopicExchange("bao5", true, true));
        rabbit.declareExchange(new TopicExchange("bao6", true, true, map));

        rabbit.declareExchange(new FanoutExchange("bao7"));
        rabbit.declareExchange(new FanoutExchange("bao8", true, true));
        rabbit.declareExchange(new FanoutExchange("bao9", true, true, map));

        rabbit.declareExchange(new HeadersExchange("bao10"));
        rabbit.declareExchange(new HeadersExchange("bao11", true, true));
        rabbit.declareExchange(new HeadersExchange("bao12", true, true, map));


        rabbit.declareQueue(new Queue("que1"));
        rabbit.declareQueue(new Queue("que2", true));
        rabbit.declareQueue(new Queue("que3", true, false, true));
        Map<String, Object> map1 = new HashMap<>();
        map.put("x-max-length", 100);
        rabbit.declareQueue(new Queue("que4", true, false, true, map));

//        rabbit.declareBinding(new Binding("que2", Binding.DestinationType.QUEUE, "bao2", "bao2-que2", null));
//        rabbit.declareBinding(new Binding("que3", Binding.DestinationType.QUEUE, "bao4", "bao4-que3", null));
//        rabbit.declareBinding(new Binding("que3", Binding.DestinationType.QUEUE, "bao8", "", null));
//        Map<String, Object> map2 = new HashMap<>();
//        map2.put("x-match", "any");
//        map2.put("size", 1);
//        rabbit.declareBinding(new Binding("que3", Binding.DestinationType.QUEUE, "bao11", "", map2));

        rabbit.declareBinding(BindingBuilder.bind(new Queue("que2")).to(new DirectExchange("bao2")).with("bao2-que2"));

        rabbit.declareBinding(BindingBuilder.bind(new Queue("que3")).to(new TopicExchange("bao4")).with("bao4-que3"));
        rabbit.declareBinding(BindingBuilder.bind(new Queue("que3")).to(new FanoutExchange("bao8")));
        Map<String, Object> map3 = new HashMap<>();
        map3.put("size", 2);
       rabbit.declareBinding(BindingBuilder.bind(new Queue("que3")).to(new HeadersExchange("bao11")).whereAny(map3).match());


        context.close();
    }

}
