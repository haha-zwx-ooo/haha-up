package com.haha.rabbitmq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @description: 广播模式测试
 * @author: 张文旭
 * @create: 2021-07-10 13:58
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class FanoutTest {


    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 广播模式
     */
    private static final String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";
    /**
     * fanout测试队列1
     */
    private static final String FANOUT_QUEUE1 = "FANOUT_QUEUE1";
    /**
     * fanout测试队列2
     */
    private static final String FANOUT_QUEUE2 = "FANOUT_QUEUE2";

    private static final int loop_index = 10;

    @Test
    public void test() {
        for (int i = 0; i < loop_index; i++) {
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(FANOUT_EXCHANGE, null, "我是FANOUT_EXCHANGE信息  " + i, correlationData);
        }
    }
}
