package com.haha.rabbitmq.test;

import com.haha.rabbitmq.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: 张文旭
 * @create: 2021-07-10 01:21
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleQueueTest {

    private static final String SIMPLE_QUEUE = "haha";
    private static final String SIMPLE_QUEUE_POJO = "haha_pojo";

    private static final int loop_index = 10;

    //注入 RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendString() {

        for (int i = 0; i < loop_index; i++) {
            rabbitTemplate.convertAndSend(SIMPLE_QUEUE, "SimpleQueueTest boot mq..." + i);
        }
    }

    @Test
    public void sendObject() {

        User user = null;
        for (int i = 0; i < loop_index; i++) {
            user = new User();
            user.setName("张三" + i);
            user.setSex("男");
            user.setAge(i);
            rabbitTemplate.convertAndSend(SIMPLE_QUEUE_POJO, user);
        }
    }

}
