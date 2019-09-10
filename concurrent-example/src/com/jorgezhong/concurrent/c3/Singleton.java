package com.jorgezhong.concurrent.c3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单例设计模式下的线程安全问题
 * <p>
 * 线程安全性问题的前提
 * - 多线程环境下
 * - 必须有共享资源
 * - 对资源进行非原子性的操作
 * <p>
 * Project <learning>
 * Created by jorgezhong on 2019/9/10 18:13.
 */
public class Singleton {


    //私有化构造
    private Singleton() {
    }

    //饿汉式,无线程安全性问题
    private static Singleton instance = new Singleton();

    public static Singleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {

        Singleton instance0 = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        System.out.println("instance2 = " + instance2);
        System.out.println("instance1 = " + instance1);
        System.out.println("instance0 = " + instance0);
    }

}


class Singleton2 {


    //私有化构造
    private Singleton2() {
    }

    //
    private volatile static Singleton2 instance;


    /**
     * 解决一：synchronized
     * 偏向锁：当单线程的时候，可以拿着这个这个锁不放。但是多线程调用的时候，显然是不适应的
     * 轻量级锁：第一个线程进来拿到锁开始执行，第二个线程同时也能进来，但是不能执行，会检查对象头标记
     * 发现已经有对象拿到锁了，开始自旋，等待第一个线程释放。自旋的线程多了，性能反而很差
     * <p>
     * 重量级锁：当第三个线程拿到之后，就会升级为重量级锁了，这时其他线程阻塞，重新唤醒也消耗性能
     *
     * @return
     */
    //懒汉式
    public synchronized static Singleton2 getInstance() {
        //线程安全问题：非原子性操作
        if (instance == null) {
            //线程0、线程1同时执行到这行代码
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new Singleton2();
        }
        return instance;
    }

    /**
     * 解决二：双重检查校验机制（降低锁的粒度）
     * 问题：
     *  - 校验代码和实例化代码可能进行指令重排（因为满足不影响结果的情况下指令重排）
     *
     * 解决：共享变量 volatile 修饰，当进行写操作完成时，写入主存，同之其他使用共享变量的读操作被通知到，可见
     * @return
     */
    public static Singleton2 getInstance2() {
        //线程安全问题：非原子性操作
        if (instance == null) {

            synchronized (Singleton2.class) {
                //由于实例化重排序问题，判断为空可能出问题
                if (instance == null) {
                    /**
                     * 实例化指令
                     * - 1.申请一块内存空间
                     * - 2.在这块空间里实例化对象
                     * - 3.instance引用指向这块空间地址
                     *
                     * 问题：指令3可能发送在指令2前
                     */

                    instance = new Singleton2(); //指令重排序

                }
            }
        }
        return instance;
    }


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread()
                    .getName() + ":" + Singleton2.getInstance()));
        }

        executorService.shutdown();
    }

}
