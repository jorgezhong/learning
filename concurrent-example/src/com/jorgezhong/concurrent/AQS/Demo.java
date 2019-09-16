package com.jorgezhong.concurrent.AQS;

/**
 * AQS:AbstractQueuedSynchronizer类
 * - 是一个抽象类，通过它实现锁，或者其他同步组件才有意义
 * - 相当于一个模板方法模式
 * <p>
 * AQS
 * AbstractQueuedSynchronizer的父类AbstractOwnableSynchronizer持有一个独占线程exclusiveOwnerThread，存储当前拿到锁的线程
 * AbstractQueuedSynchronizer的持有一个成员变量state,用于记录是否获取锁，以及重入锁的次数
 * AQS模板定义了共享模式和独占模式，上述两点是基于独占模式阐述的。
 * AQS定义的抽象模板方法,需要是否是独占模式来去实现它，才有意义
 * - boolean tryAcquire(int arg)
 * - boolean tryRelease(int arg)
 * - int tryAcquireShared(int arg)
 * - boolean tryReleaseShared(int arg)
 * - boolean isHeldExclusively()
 * <p>
 * ReentrantLock 使用了AQS 其内部类Sync继承了AQS，分别实现类公平以及非公平的锁
 * <p>
 * 非公平举例加锁：
 * - 获取当前线程，获取state对比
 * - state = 0 则通过CAS（自旋）将state设置为1，并且设置exclusiveOwnerThread为当前线程，表示独占，获取锁成功
 * - state != 0 则通过exclusiveOwnerThread和当前线程判断是否是同一个，同一个则表示锁重入，设置state + 1，获取锁成功
 * <p>
 * 非公平举例释放锁：
 * - 获取 state - 1
 * - 获取 exclusiveOwnerThread 并判断是否为当前线程（不是当前线程没有意义，抛出异常）
 * - 判断之前算的 state - 1 == 0 如实是，则表示没有释放最外层锁（无重入锁了），释放成功
 * - 设置 exclusiveOwnerThread = null
 * - 设置 state = 0
 * <p>
 * <p>
 * Project <learning>
 * Created by jorgezhong on 2019/9/11 16:39.
 */
public class Demo {

    private int value;

    private CustomAQSLock lock = new CustomAQSLock();

    public int next() {
        lock.lock();
        try {
            Thread.sleep(300);
            return value++;
        } catch (InterruptedException e) {
            throw new RuntimeException();
        } finally {
            lock.unlock();
        }
    }


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
//        customAQSLockTest();
        reentrantCustomLockTest();

    }

    private static void reentrantCustomLockTest() {
        Demo demo = new Demo();
        Runnable runnable = demo::a;

        new Thread(runnable).start();
    }

    private static void customAQSLockTest() {
        Demo demo = new Demo();

        Runnable runnable = () -> {
            while (true) {
                System.out.println(Thread.currentThread().getId() + ":" + demo.next());
            }

        };
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }

}
