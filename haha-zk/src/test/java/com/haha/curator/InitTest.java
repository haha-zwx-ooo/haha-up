package com.haha.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;

/**
 * @description: 基础测试包
 * @author: 张文旭
 * @create: 2021-06-28 15:53
 **/
@Slf4j
public class InitTest {

    public static final String CONN_URL = "192.168.213.50:2181";
    public static final int SESSION_TIMEOUT = 50000;
    public CuratorFramework curatorFramework;


    @Before
    public void init() {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONN_URL)
                .sessionTimeoutMs(SESSION_TIMEOUT)  // 会话超时时间
                .connectionTimeoutMs(SESSION_TIMEOUT) // 连接超时时间
                .retryPolicy(retryPolicy)
//                .namespace("base") // 包含隔离名称
                .build();
        curatorFramework.start();
    }
}
