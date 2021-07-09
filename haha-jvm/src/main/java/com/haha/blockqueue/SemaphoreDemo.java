package com.haha.blockqueue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @description: 信号量测试
 * @author: 张文旭
 * @create: 2021-06-23 22:32
 **/
public class SemaphoreDemo extends Thread {

    /**
     * 默认非公平，初始化型号量大小
     */
    Semaphore semaphore = new Semaphore(2);
    /**
     * 公平
     */
    Semaphore fair_semaphore = new Semaphore(2, true);


    public static void main(String[] args) {
        int index = 10;
        for (int i = 0; i < index; i++) {
            new SemaphoreDemo().start();
        }
    }


    public void work() {
        boolean ex = false;
        try {
            System.out.println(Thread.currentThread().getName() + "阻塞...");
            //一直阻塞直到成功
            semaphore.acquire();
            //超时阻塞
//            boolean b = semaphore.tryAcquire(1, TimeUnit.SECONDS);
//            if (!b) {
//                throw new RuntimeException("获取信号量失败");
//            }
            if (Thread.currentThread().getId() % 2 == 0) {
                throw new RuntimeException("模拟线程出异常降级");
            }
            System.out.println(Thread.currentThread().getName() + "进入, 开始执行业务员逻辑...");
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            ex = true;
        } finally {
            if (ex) {
                fallback();
            }
            semaphore.release();
        }

    }

    public void fallback() {
        System.err.println(Thread.currentThread().getName() + "降级...");
    }

    @Override
    public void run() {
        this.work();
    }
}
