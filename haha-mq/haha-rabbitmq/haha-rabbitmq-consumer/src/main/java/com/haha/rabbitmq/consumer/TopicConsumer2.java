package com.haha.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: 主题模式擦拭
 * @author: 张文旭
 * @create: 2021-07-10 14:32
 **/
@Profile("8083")
@Slf4j
@Component
public class TopicConsumer2 {


    /**
     * topic测试队列1
     */
    private static final String TOPIC_QUEUE1 = "TOPIC_QUEUE1";
    /**
     * topic测试队列2
     */
    private static final String TOPIC_QUEUE2 = "TOPIC_QUEUE2";

    @RabbitListener(queues = TOPIC_QUEUE2)
    public void process(String content, Message message, Channel channel, @Payload String body, @Headers Map<String, Object> headers) {
        Action action = Action.ACCEPT;
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("string message header:     " + headers);
            System.out.println("string message:    " + body);
        } catch (Exception e) {
            //根据异常种类决定是ACCEPT、RETRY还是 REJECT
            action = Action.RETRY;
            e.printStackTrace();
        } finally {
            try {
                if (action == Action.ACCEPT) {
                    // 确认收到消息，消息将被队列移除；false只确认当前consumer一个消息收到，true确认所有consumer获得的消息。
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } else if (action == Action.RETRY) {
                    //确认否定消息，第一个boolean表示一个consumer还是所有，第二个boolean表示requeue是否重新回到队列，true重新入队
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                } else {
                    //拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列。
                    channel.basicNack(tag, false, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

