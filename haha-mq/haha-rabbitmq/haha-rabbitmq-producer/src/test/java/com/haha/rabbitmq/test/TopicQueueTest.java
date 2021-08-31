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
public class TopicQueueTest {

    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     * 主题模式
     */
    private static final String TOPIC_EXCHANGE = "TOPIC_EXCHANGE";
    /**
     * topic测试routingkey1
     */
    private static final String TOPIC_KEY1 = "男.#";

    /**
     * topic测试routingkey2
     */
    private static final String TOPIC_KEY2 = "女.#";

    private static final int loop_index = 10;

    @Test
    public void test() {

        String haha = "男.abc";
        String haha2 = "女.efg.haha";

        for (int i = 0; i < loop_index; i++) {
            if (i % 2 == 0)
                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, TOPIC_KEY1, haha + i);
            else
                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, TOPIC_KEY1, haha2 + i);
        }

//        for (int i = 0; i < loop_index; i++) {
//            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//            if (i % 2 == 0)
//                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, TOPIC_KEY2, haha + i, correlationData);
//            else
//                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, TOPIC_KEY2, haha2 + i, correlationData);
//        }
    }

}
