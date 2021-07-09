package com.haha.curator.test;

import com.haha.curator.InitTest;
import com.haha.zk.LeaderChoseDemo;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.utils.CloseableUtils;
import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @description: 测试
 * @author: 张文旭
 * @create: 2021-06-28 17:21
 **/
@Slf4j
public class CuratorTest extends InitTest {

    @Test
    public void testCreate() throws Exception {
        String path = curatorFramework.create().forPath("/curator-node");
        // curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/curator-node","some-data".getBytes())
        log.info("curator create node :{}  successfully.", path);
    }

    @Test
    public void testCreateWithParent() throws Exception {
        String pathWithParent = "/node-parent/sub-node-1";
        String path = curatorFramework.create().creatingParentsIfNeeded().forPath(pathWithParent);
        log.info("curator create node :{}  successfully.", path);
    }

    @Test
    public void testGetData() throws Exception {
        byte[] bytes = curatorFramework.getData().forPath("/curator-node");
        log.info("get data from  node :{}  successfully.", new String(bytes));
    }


    @Test
    public void testSetData() throws Exception {
        curatorFramework.setData().forPath("/curator-node", "changed!".getBytes());
        byte[] bytes = curatorFramework.getData().forPath("/curator-node");
        log.info("get data from  node /curator-node :{}  successfully.", new String(bytes));
    }

    @Test
    public void testDelete() throws Exception {
        String pathWithParent = "/node-parent";
        curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().forPath(pathWithParent);
    }


    @Test
    public void leader() throws InterruptedException {
        LinkedList<LeaderChoseDemo> list = new LinkedList<>();
        LeaderChoseDemo server = null;
        String name = "leaderDemo----";
        for (int i = 0; i < 5; i++) {
            System.out.println("执行 " + i);
            server = new LeaderChoseDemo(curatorFramework, "/zk-master", name + i);
            server.start();
            list.add(server);
        }

        for (int i = 0; i < list.size(); i++) {
            server = list.get(i);
            CloseableUtils.closeQuietly(server);
            // 为方便观察，这里阻塞几秒
            TimeUnit.SECONDS.sleep(3);
        }

    }
}
