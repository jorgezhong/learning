package com.jorgezhong.concurrent.reentrant_lock;

/**
 * 死锁
 *  示例：两个显示同时访问到a()和b() 并且统一获取到锁，进入同步快，相互等待第二把锁，而这个锁是对方线程的第一把锁，未释放，进入死锁状态
 *
 * Project <learning>
 * Created by jorgezhong on 2019/9/11 9:59.
 */
public class Demo3 {

    private Object lock1 = new Object();
    private Object lock2 = new Object();


    public void a()   {
        synchronized (lock1){

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //获取到lock1锁，需要获取lock2锁，此时b未释放lock2锁
            synchronized (lock2) {
                System.out.println("Hello A");
            }
        }
    }

    public void b()   {
        synchronized (lock2) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //获取到lock2锁，需要获取lock1锁，此时a未释放lock1锁
            synchronized (lock1) {
                System.out.println("Hello B");
            }
        }

    }

    public static void main(String[] args) {
        Demo3 demo3 = new Demo3();
        new Thread(demo3::a).start();
        new Thread(demo3::b).start();
    }
}
