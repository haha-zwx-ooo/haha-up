package com.haha.leader;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LeaderSelectorDemo {


    private static final String CONNECT_STR = "192.168.213.50:2181";


    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(5 * 1000, 10);

    private static CuratorFramework curatorFramework;


    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    public static void main(String[] args) throws InterruptedException {


        String appName = System.getProperty("appName");

        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(CONNECT_STR, retryPolicy);
        LeaderSelectorDemo.curatorFramework = curatorFramework;
        curatorFramework.start();

        LeaderSelectorListener listener = new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {

                System.out.println(" I' m leader now . i'm , " + appName);

                for (int i = 1; i <= 5; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("阻塞.. " + i);
                }
                System.out.println("释放主角色，开始竞争...");
            }
        };

        LeaderSelector selector = new LeaderSelector(curatorFramework, "/_leader", listener);
        selector.autoRequeue();  // not required, but this is behavior that you will probably expect
        selector.start();
        countDownLatch.await();

    }
}
