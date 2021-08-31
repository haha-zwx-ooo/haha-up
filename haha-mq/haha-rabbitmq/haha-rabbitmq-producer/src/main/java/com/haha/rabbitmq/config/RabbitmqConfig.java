package com.haha.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @description: rabbitmq配置
 * @author: 张文旭
 * @create: 2021-07-10 00:58
 **/
@Slf4j
@Configuration
public class RabbitmqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        //指定 ConfirmCallback
        rabbitTemplate.setConfirmCallback(this);
        //指定 ReturnCallback
        rabbitTemplate.setReturnCallback(this);

    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息发送成功:,{}", correlationData);
        } else {
            log.info("消息发送失败:,{}", cause);
        }
    }

    /**
     * 回调
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息主体:,{}", message);
        log.info("应答码:,{}", replyCode);
        log.info("描述:,{}", replyText);
        log.info("消息使用的交换器 exchange :,{}", exchange);
        log.info("消息使用的路由键 routing :,{}", routingKey);
    }

}

