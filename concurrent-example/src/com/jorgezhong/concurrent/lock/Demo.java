package com.jorgezhong.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock接口
 * 对比：
 *  - 需要显示地获取和释放锁 ： 虽繁琐，但是更灵活
 *  - synchronized 不需要显示地获取和释放锁：简单
 *
 *  - 使用Lock可以方便实现公平性
 *
 *
 * Project <learning>
 * Created by jorgezhong on 2019/9/11 14:51.
 */
public class Demo {
}


class Sequecnce {

    private int value;
    //公平的可重入锁
    ReentrantLock reentrantLock = new ReentrantLock();

    public int getNext() {
        //锁
        reentrantLock.lock();
        //原子操作
        int result = value++;
        //释放锁
        reentrantLock.unlock();
        return result;
    }


    private static void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Sequecnce sequecnce = new Sequecnce();
        Runnable runnable = () -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":" + sequecnce.getNext());
                sleep(20);
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();

    }

}