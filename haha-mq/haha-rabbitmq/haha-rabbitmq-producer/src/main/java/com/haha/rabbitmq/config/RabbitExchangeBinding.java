package com.haha.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Publish/Subscribe 模式
 * @author: 张文旭
 * @create: 2021-07-10 13:11
 **/
@Slf4j
@Configuration
public class RabbitExchangeBinding {
    /**
     * 定向模式
     */
    private static final String DIRECT_EXCHANGE = "DIRECT_EXCHANGE";
    /**
     * 主题模式
     */
    private static final String TOPIC_EXCHANGE = "TOPIC_EXCHANGE";
    /**
     * 广播模式
     */
    private static final String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";

    /**
     * direct测试队列1
     */
    private static final String DIRECT_QUEUE1 = "DIRECT_QUEUE1";
    /**
     * direct测试队列2
     */
    private static final String DIRECT_QUEUE2 = "DIRECT_QUEUE2";
    /**
     * topic测试队列1
     */
    private static final String TOPIC_QUEUE1 = "TOPIC_QUEUE1";
    /**
     * topic测试队列2
     */
    private static final String TOPIC_QUEUE2 = "TOPIC_QUEUE2";
    /**
     * fanout测试队列1
     */
    private static final String FANOUT_QUEUE1 = "FANOUT_QUEUE1";
    /**
     * fanout测试队列2
     */
    private static final String FANOUT_QUEUE2 = "FANOUT_QUEUE2";

    /**
     * direct测试routingkey1
     */
    private static final String DIRECT_KEY1 = "DIRECT_KEY1";
    /**
     * direct测试routingkey2
     */
    private static final String DIRECT_KEY2 = "DIRECT_KEY2";

    /**
     * topic测试routingkey1
     */
    private static final String TOPIC_KEY1 = "男.#";

    /**
     * topic测试routingkey2
     */
    private static final String TOPIC_KEY2 = "女.#";

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }


    @Autowired
    private RabbitAdmin rabbitAdmin;

    /**
     * 定向型交换机
     */
    @Bean
    DirectExchange contractDirectExchange() {
        DirectExchange contractDirectExchange = new DirectExchange(DIRECT_EXCHANGE);
        rabbitAdmin.declareExchange(contractDirectExchange);
        log.info("direct交换机默认实例创建成功");
        return contractDirectExchange;
    }

    /**
     * 主题模式交换机
     *
     * @return
     */
    @Bean
    TopicExchange contractTopicExchange() {
        TopicExchange contractTopicExchange = new TopicExchange(TOPIC_EXCHANGE);
        rabbitAdmin.declareExchange(contractTopicExchange);
        log.info("topic交换机默认实例创建成功");
        return contractTopicExchange;
    }

    /**
     * 广播模式交换机
     *
     * @return
     */
    @Bean
    FanoutExchange contractFanoutExchange() {
        FanoutExchange contractFanoutExchange = new FanoutExchange(FANOUT_EXCHANGE);
        rabbitAdmin.declareExchange(contractFanoutExchange);
        log.info("fanout交换机默认实例创建成功");
        return contractFanoutExchange;
    }

    @Bean
    Queue directQueue1() {
        Queue queue = new Queue(DIRECT_QUEUE1);
        rabbitAdmin.declareQueue(queue);
        log.debug("direct测试队列-1实例化成功");
        return queue;
    }

    @Bean
    Queue directQueue2() {
        Queue queue = new Queue(DIRECT_QUEUE2);
        rabbitAdmin.declareQueue(queue);
        log.debug("direct测试队列-2实例化成功");
        return queue;
    }

    @Bean
    Queue topicQueue1() {
        Queue queue = new Queue(TOPIC_QUEUE1);
        rabbitAdmin.declareQueue(queue);
        log.debug("topic测试队列-1实例化成功");
        return queue;
    }

    @Bean
    Queue topicQueue2() {
        Queue queue = new Queue(TOPIC_QUEUE2);
        rabbitAdmin.declareQueue(queue);
        log.debug("topic测试队列-2实例化成功");
        return queue;
    }


    @Bean
    Queue fanoutQueue1() {
        Queue queue = new Queue(FANOUT_QUEUE1);
        rabbitAdmin.declareQueue(queue);
        log.debug("fanout测试队列-1实例化成功");
        return queue;
    }

    @Bean
    Queue fanoutQueue2() {
        Queue queue = new Queue(FANOUT_QUEUE2);
        rabbitAdmin.declareQueue(queue);
        log.debug("fanout测试队列-2实例化成功");
        return queue;
    }

    @Bean
    Binding bindingDirectQueue1(Queue directQueue1, DirectExchange exchange) {
        //绑定结构：队列-交换机-路由key
        Binding binding = BindingBuilder.bind(directQueue1).to(exchange).with(DIRECT_KEY1);
        rabbitAdmin.declareBinding(binding);
        log.debug("direct队列1/交换机绑定成功");
        return binding;
    }
    @Bean
    Binding bindingDirectQueue2(Queue directQueue2, DirectExchange exchange) {
        //绑定结构：队列-交换机-路由key
        Binding binding = BindingBuilder.bind(directQueue2).to(exchange).with(DIRECT_KEY2);
        rabbitAdmin.declareBinding(binding);
        log.debug("direct队列2/交换机绑定成功");
        return binding;
    }

    @Bean
    Binding bindingTopicQueue1(Queue topicQueue1, TopicExchange exchange) {
        Binding binding = BindingBuilder.bind(topicQueue1).to(exchange).with(TOPIC_KEY1);
        rabbitAdmin.declareBinding(binding);
        log.debug("topic队列-1/交换机绑定成功");
        return binding;
    }

    @Bean
    Binding bindingTopicQueue2(Queue topicQueue2, TopicExchange exchange) {
        Binding binding = BindingBuilder.bind(topicQueue2).to(exchange).with(TOPIC_KEY2);
        rabbitAdmin.declareBinding(binding);
        log.debug("topic队列-2/交换机绑定成功");
        return binding;
    }

    @Bean
    Binding bindingFanoutQueue1(Queue fanoutQueue1, FanoutExchange exchange) {
        Binding binding = BindingBuilder.bind(fanoutQueue1).to(exchange);
        rabbitAdmin.declareBinding(binding);
        log.debug("fanout队列-1/交换机绑定成功");
        return binding;
    }

    @Bean
    Binding bindingFanoutQueue2(Queue fanoutQueue2, FanoutExchange exchange) {
        Binding binding = BindingBuilder.bind(fanoutQueue2).to(exchange);
        rabbitAdmin.declareBinding(binding);
        log.debug("fanout队列-2/交换机绑定成功");
        return binding;
    }

}
