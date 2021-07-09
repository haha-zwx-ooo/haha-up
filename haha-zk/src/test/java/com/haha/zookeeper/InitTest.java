package com.haha.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @description: 基础测试包
 * @author: 张文旭
 * @create: 2021-06-28 15:52
 **/
@Slf4j
public class InitTest {

    public static final String ZK_ADDRESS="192.168.213.50:2181";

    public static final int SESSION_TIMEOUT = 5000;

    public static ZooKeeper zooKeeper;

    public static final String ZK_NODE="/zk-node";

    @Before
    public void init() throws IOException, InterruptedException {
        log.info("开始尝试连接...");
        final CountDownLatch countDownLatch=new CountDownLatch(1);
        zooKeeper=new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
            if (event.getState()== Watcher.Event.KeeperState.SyncConnected &&
                    event.getType()== Watcher.Event.EventType.None){
                countDownLatch.countDown();
                log.info("连接成功！");
            }
        });
        log.info("连接中....");
        countDownLatch.await();
    }

}
