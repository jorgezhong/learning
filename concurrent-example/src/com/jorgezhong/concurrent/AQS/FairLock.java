package com.jorgezhong.concurrent.AQS;

import java.util.ArrayList;
import java.util.List;

/**
 * 公平锁
 * 公平是针对锁的获取而言的，如果一个锁是公平的，那么锁的获取顺序就应该符合请求的绝对时间顺序
 * <p>
 *
 * 本例使用synchronized实现了一个可重入的公平锁
 *
 * 思路：将等待和唤醒封装起来。并使用一个队列来维护
 *
 * 加锁：
 * - 获取锁的时候，先创建QueueObject,并同步加入等待队列waitingThreads
 * - 调用先前创建的QueueObject执行等待doWait()
 * 解锁
 * - 等到释放锁的时候再调用doNotify()，抛出异常InterruptedException
 * - 抛出的异常被捕获后，将等待队列的该对象删除，waitingThreads.remove(queueObject);
 *
 * 锁重入：
 * - 使用了isLocked判断是否已获取过锁
 * - 使用lockingThread记录当前获取到锁的线程
 * - 锁重入了直接执行doWait()方法，等待释放的时候捕获线程即可
 * - 非锁重入的时候呢，直接删除等待队列的当前等待唤醒对象，记录当前获取到锁的线程即可
 *
 * Project <learning>
 * Created by jorgezhong on 2019/9/16 14:17.
 */
public class FairLock {


    private boolean isLocked = false;
    private Thread lockingThread;
    private List<QueueObject> waitingThreads = new ArrayList<>();


    public void lock() throws InterruptedException {
        QueueObject queueObject = new QueueObject();

        boolean isLockedForThisThread = true;

        synchronized (this) {
            waitingThreads.add(queueObject);
        }
        //锁重入
        while (isLockedForThisThread) {
            synchronized (this) {
                //已锁，或者当前队列的第一个等待唤醒对象已经不是当前线程刚才创建的等待唤醒对象了
                //满足上述条件表示锁重入了
                isLockedForThisThread = isLocked || waitingThreads.get(0) != queueObject;
                if (!isLockedForThisThread) {
                    isLocked = true;
                    waitingThreads.remove(queueObject);
                    lockingThread = Thread.currentThread();
                    return;
                }
            }

            try {
                queueObject.doWait();
            } catch (InterruptedException e) {
                synchronized (this) {
                    waitingThreads.remove(queueObject);
                }
                throw e;
            }
        }


    }

    public synchronized void unlock() {


        if (this.lockingThread != Thread.currentThread()) {
            throw new IllegalMonitorStateException("Calling thread has not locked this lock");
        }
        isLocked = false;
        lockingThread = null;

        if (waitingThreads.size() > 0) {
            waitingThreads.get(0).doNotify();
        }

    }


    /**
     * 等待唤醒对象
     *  方便自定义控制等待唤醒的状态
     *
     */
    public static class QueueObject {

        private boolean isNotified = false;

        public synchronized void doWait() throws InterruptedException {

            while (!isNotified) {
                this.wait();
            }
            this.isNotified  = false;
        }

        public synchronized void doNotify() {
            this.isNotified = true;
            this.notify();
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj;
        }
    }
}
