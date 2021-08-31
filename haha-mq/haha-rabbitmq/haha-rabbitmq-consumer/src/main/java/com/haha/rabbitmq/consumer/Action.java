package com.haha.rabbitmq.consumer;

/**
 * @description:
 * @author: 张文旭
 * @create: 2021-07-10 13:24
 **/
public enum Action {
    // 处理成功
    ACCEPT,
    // 可以重试的错误
    RETRY,
    // 无需重试的错误
    REJECT;
}
