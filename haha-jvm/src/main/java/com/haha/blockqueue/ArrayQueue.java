package com.haha.blockqueue;

import lombok.SneakyThrows;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description: 有界阻塞队列
 * @author: 张文旭
 * @create: 2021-06-23 21:28
 **/
public class ArrayQueue {

    static ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        producter producter = new producter();
        new Thread(producter).start();
//        new Thread(producter).start();
//        new Thread(producter).start();
        Consumeer consumeer = new Consumeer();
        new Thread(consumeer).start();
        new Thread(consumeer).start();
        new Thread(consumeer).start();
    }


    static class Consumeer implements Runnable {

        void get() {
            try {
                String take = blockingQueue.take();
                System.out.println(Thread.currentThread().getName() + "消费到任务 -->  " + take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                get();
            }
        }
    }

    static class producter implements Runnable {

        void put(String i) {
            try {
                blockingQueue.put(i);
                System.out.println("生产任务成功 -->  " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @SneakyThrows
        @Override
        public void run() {
            int i = 0;
            while (true) {
                TimeUnit.SECONDS.sleep(1);
                put(String.valueOf(i++));
            }
        }
    }
}

