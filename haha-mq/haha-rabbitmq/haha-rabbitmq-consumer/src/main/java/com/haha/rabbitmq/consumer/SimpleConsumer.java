package com.haha.rabbitmq.consumer;


import com.rabbitmq.client.Channel;
import com.haha.rabbitmq.pojo.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: 简单队列测试
 * @author: 张文旭
 * @create: 2021-07-10 01:24
 **/
@Component
public class SimpleConsumer {

    private static final String SIMPLE_QUEUE = "haha";
    private static final String SIMPLE_QUEUE_POJO = "haha_pojo";


    /**
     * 定义方法进行信息的监听
     * RabbitListener中的参数用于表示监听的是哪一个队列
     *
     * @param body
     * @param headers
     */
    @RabbitListener(queues = SIMPLE_QUEUE)
    public void ListenerQueueString(@Payload String body, @Headers Map<String, Object> headers, Message message, Channel channel) {
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

    @RabbitListener(queues = SIMPLE_QUEUE_POJO)
    public void ListenerQueuePojo(@Payload User body, @Headers Map<String, Object> headers, Message message, Channel channel) {
        Action action = Action.ACCEPT;
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("pojo message header:     " + headers);
            System.out.println("pojo message:    " + body);
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
