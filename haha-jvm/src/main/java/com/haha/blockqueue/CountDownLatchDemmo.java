package com.haha.blockqueue;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @description: CountDownLatch测试
 * @author: 张文旭
 * @create: 2021-06-23 23:02
 **/
public class CountDownLatchDemmo {


    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Th1(countDownLatch, "张三").start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Th1(countDownLatch, "李四").start();

        System.out.println("主线程开始执行...");
    }


}

class Th1 extends Thread {
    CountDownLatch countDownLatch;
    String name;

    protected Th1() {

    }

    public Th1(CountDownLatch countDownLatch, String name) {
        this.countDownLatch = countDownLatch;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + "正在执行...");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
