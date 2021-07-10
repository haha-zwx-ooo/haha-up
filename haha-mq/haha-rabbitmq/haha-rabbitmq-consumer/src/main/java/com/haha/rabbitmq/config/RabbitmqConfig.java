package com.haha.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: rabbitmq配置
 * @author: 张文旭
 * @create: 2021-07-10 00:58
 **/
@Configuration
public class RabbitmqConfig {

    private static final String SIMPLE_QUEUE = "haha";
    private static final String SIMPLE_QUEUE_POJO = "haha_pojo";

    @Bean("haha")
    public Queue haha() {
        return new Queue(SIMPLE_QUEUE, true);
    }

    @Bean("haha_pojo")
    public Queue hahaPojo() {
        return new Queue(SIMPLE_QUEUE_POJO, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
