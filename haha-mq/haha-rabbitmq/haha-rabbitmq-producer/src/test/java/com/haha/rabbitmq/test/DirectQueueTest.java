package com.haha.rabbitmq.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @description: 定向queue测试
 * @author: 张文旭
 * @create: 2021-07-10 13:26
 **/

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DirectQueueTest {

    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     * 定向模式
     */
    private static final String DIRECT_EXCHANGE = "DIRECT_EXCHANGE";
    /**
     * direct测试routingkey1
     */
    private static final String DIRECT_KEY1 = "DIRECT_KEY1";

    /**
     * direct测试routingkey2
     */
    private static final String DIRECT_KEY2 = "DIRECT_KEY2";

    private static final int loop_index = 10;

    @Test
    public void test() {
        for (int i = 0; i < loop_index; i++) {
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_KEY1, "我是DIRECT_EXCHANGE信息  " + i, correlationData);
        }

        for (int i = loop_index; i < loop_index * 2; i++) {
            System.out.println(i);
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_KEY2, "我是DIRECT_EXCHANGE信息  " + i, correlationData);
        }
    }

}
