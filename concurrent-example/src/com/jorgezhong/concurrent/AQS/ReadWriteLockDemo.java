package com.jorgezhong.concurrent.AQS;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 排他锁和共享锁
 * - 排他锁：同一时刻只允许一个线程进行访问（写线程之间排他锁，互斥）
 * - 共享锁：同一时刻，允许多个线程进行读（读线程之间共享锁）
 * <p>
 * 当写线程进来，为了安全，必须让当前所有读线程执行完毕之后，写才执行工作
 * <p>
 * <p>
 * Project <learning>
 * Created by jorgezhong on 2019/9/16 15:07.
 */
public class ReadWriteLockDemo {

    private Map<String, Object> map = new HashMap<>();

    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    private Lock rl = rwl.readLock();

    private Lock wl = rwl.writeLock();

    public Object get(String key) {
        rl.lock();
        System.out.println(Thread.currentThread().getName() + ":读操在执行");
        try {
            sleep(3000);
            return map.get(key);
        } finally {
            rl.unlock();
            System.out.println(Thread.currentThread().getName() + ":读操在执行完毕");
        }
    }

    public void put(String key, Object value) {
        wl.lock();
        System.out.println(Thread.currentThread().getName() + ":写操在执行");
        try {
            sleep(3000);
            map.put(key, value);
        } finally {
            wl.unlock();
            System.out.println(Thread.currentThread().getName() + ":写操作执行完毕");
        }
    }


    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();


        Runnable writeRunnable1 = () -> {
            readWriteLockDemo.put("a","value");
        };
        Runnable writeRunnable2 = () -> {
            readWriteLockDemo.put("a","value");
        };
        Runnable readRunnable = () -> {
            while (true) {
                System.out.println(readWriteLockDemo.get("a"));
            }
        };

        //写线程独占锁，其他线程阻塞
        new Thread(writeRunnable1).start();
        new Thread(writeRunnable2).start();

        //读线程共享锁，不阻塞
        new Thread(readRunnable).start();
        new Thread(readRunnable).start();
        new Thread(readRunnable).start();

    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
