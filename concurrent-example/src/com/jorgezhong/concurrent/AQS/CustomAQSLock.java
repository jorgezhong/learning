package com.jorgezhong.concurrent.AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 通过继承AQS实现自定义锁
 * Project <learning>
 * Created by jorgezhong on 2019/9/12 17:33.
 */
public class CustomAQSLock implements Lock {


    private Sync sync = new Sync();

    private class Sync extends AbstractQueuedSynchronizer {


        @Override
        protected boolean tryAcquire(int arg) {


            /**
             * 考虑的问题：
             * 1.如果第一个线程进来，可以拿到锁，因此我们可以返回true
             * 2.如果第二个线程进来，拿不到锁，阻塞，返回false
             *  - 如果当前进来的线程和当前保存的线程同一个线程，则允许拿到锁（锁重入）
             *  - 此时需要更新state值
             * 3.如何判断是第一个线程进来，还是第二个，还是其他线程进来
             *
             * 通过 AQS 提供的state属性表示线程的状态
             * 通过 AQS 提供的exclusiveOwnerThread属性表示独占锁的线程
             * 通过 AQS 提供的CAS自旋方法，修改状态
             */


            Thread currentThread = Thread.currentThread();
            int state = getState();

            if (state == 0) {
                if (compareAndSetState(0, arg)) {
                    setExclusiveOwnerThread(currentThread);
                    return true;
                }
            }else if(currentThread == getExclusiveOwnerThread()){
                //锁重入逻辑
                setState(state + 1);
                return true;
            }
            return false;

        }

        @Override
        protected boolean tryRelease(int arg) {

            /**
             * 考虑的问题：
             * 1.锁的获取和释放是一一对应的，那么调用此方法的线程一定是当前线程
             */

            if (Thread.currentThread() != getExclusiveOwnerThread()) {
                throw new RuntimeException();
            }
            //支持锁重入
            int state = getState() - arg;

            boolean flag = false;

            if (state == 0) {
                flag = true;
                setExclusiveOwnerThread(null); //help GC
            }
            //支持锁重入
            setState(state);
            return flag;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }


    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}

