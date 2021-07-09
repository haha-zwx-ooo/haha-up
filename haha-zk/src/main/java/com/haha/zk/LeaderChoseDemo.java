package com.haha.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.io.Closeable;
import java.sql.Time;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @description: 选举
 * @author: 张文旭
 * @create: 2021-06-29 10:47
 **/
public class LeaderChoseDemo extends LeaderSelectorListenerAdapter implements Closeable {

    /**
     * server name
     **/
    private String serverName;

    /**
     * listener
     */
    private LeaderSelector leaderSelector;

    public void start() {
        leaderSelector.start();
        System.out.println(getServerName() + "开始运行了！");
    }

    @Override
    public void close() {
        leaderSelector.close();
        System.out.println(getServerName() + "释放资源了！");
    }


    public LeaderChoseDemo(CuratorFramework client, String path, String serverName) {
        this.serverName = serverName;
        /** client, zk-path, listener */
        leaderSelector = new LeaderSelector(client, path, this);
    }


    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
        try {
            System.out.println(getServerName() + "是Master, 执行到takeLeadership()方法了！");
            //阻塞10秒
            TimeUnit.SECONDS.sleep(10);
            //重新参与 选主
            leaderSelector.autoRequeue();
        } catch (InterruptedException e) {
            System.err.println(getServerName() + " was interrupted!");
            Thread.currentThread().interrupt();
        }
    }

    public String getServerName() {
        return serverName;
    }

    public LeaderSelector getLeaderSelector() {
        return leaderSelector;
    }

    public static void main(String[] args) throws InterruptedException {
        String CONN_URL = "192.168.213.50:2181";
        int SESSION_TIMEOUT = 50000;
        LinkedList<LeaderChoseDemo> list = new LinkedList<>();
        LeaderChoseDemo server = null;
        String name = "leaderDemo----";
        for (int i = 0; i < 5; i++) {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                    .connectString(CONN_URL)
                    .sessionTimeoutMs(SESSION_TIMEOUT)
                    .connectionTimeoutMs(SESSION_TIMEOUT)
                    .retryPolicy(retryPolicy)
                    .build();
            curatorFramework.start();

            server = new LeaderChoseDemo(curatorFramework, "/zk-master", name + i);
            server.start();
            list.add(server);
        }
        // 为方便观察，这里阻塞300秒
        TimeUnit.SECONDS.sleep(300);
        for (int i = 0; i < list.size(); i++) {
            server = list.get(i);
            CloseableUtils.closeQuietly(server);
        }
    }

}
