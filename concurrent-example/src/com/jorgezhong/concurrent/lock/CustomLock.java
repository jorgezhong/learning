package com.jorgezhong.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 手动实现一个可重入锁
 * Project <learning>
 * Created by jorgezhong on 2019/9/11 15:14.
 */
public class CustomLock implements Lock {
    //单纯使用标记实现的锁是不可重入的，第二次获取该锁的时候，就发现为true,因此陷入无限等待
    private boolean isLocked = false;

    /**
     * 记录被谁锁了
     * 在判断锁重入时做比较
     */
    private Thread lockBy = null;

    /**
     * 记录锁的数量
     */
    private int lockCount = 0;

    @Override
    public synchronized void lock() {

        Thread currentThread = Thread.currentThread();
        /**
         * 判断锁是否被获取了
         * 判断是否锁重入，当前线程是否等于当前获取到锁的线程
         */
        while (isLocked && currentThread != lockBy) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isLocked = true;
        lockBy = currentThread;
        //锁重入时也会自增
        lockCount++;
    }


    @Override
    public synchronized void unlock() {

        Thread currentThread = Thread.currentThread();
        //只有当前线程的时候才需要解锁
        if (currentThread == lockBy) {
            lockCount--;
            //若锁次数为0，则说明是最外层的解锁
            if (lockCount == 0) {
                notify();
                isLocked = false;
            }
        }

    }


    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

}

/**
 * 自定义锁测试
 */
class Sequence {

    private CustomLock lock = new CustomLock();

    private int value;

    public int getNext() {
        lock.lock();
        int result = value++;
        lock.unlock();
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
                sleep(10);
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();

    }

}

/**
 * 自定义锁可重入测试
 */
class CustomLockDemo {
    CustomLock lock = new CustomLock();


    public void a() {
        lock.lock();
        System.out.println("a");
        b();
        lock.unlock();
    }

    public void b() {
        lock.lock();
        System.out.println("b");
        lock.unlock();
    }

    public static void main(String[] args) {


        CustomLockDemo customLockDemo = new CustomLockDemo();

        /**
         * a()调用了b()
         * a()、b()使用的是同一把锁
         * 若CustomLock是不可重入的，那么当a()调用b()的时候，b()等待a()释放锁,陷入无限期的等待，活跃性问题，因此能调通则说明了是可重入的
         */
        new Thread(customLockDemo::a).start();


    }

}