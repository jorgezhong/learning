package com.jorgezhong.thread.t0;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * happens-before:
 * - 用来指定两个内存操作之间的执行顺序。提供跨线程的内存可见性
 * - 在JAVA内存模型中，如果一个操作执行的结果需要对另一个操作可见，那么这两个操作之间必然存在happens-before关系
 * <p>
 * happens-before规则如下
 * - 1.程序顺序规则：单个线程中的每个操作，总是前一个操作 happens-before于该线程中任意后续操作
 * - 2.监视器锁规则：对一个锁的解锁，总是happens-before于随后对这个锁的加锁
 * - 3.volatile变量规则：对一个 volatile域的写，happens-before于后续对这个volatile域的读
 * - 4.传递性：A happens-before B ; B happens-before C ; 则 A happens-before C
 * - 5.Start规则：对于一个线程A启动另一个线程B的线程方法 那么 线程A happens-before B 线程（理解为，线程A对共享变量的修改对线程B可见）
 * - 6.Interrupt规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生
 * - 7.Join规则：假定线程A执行过程中，通过制定ThreadB.join()来等待线程B终止，那么 线程B happens-before 线程A
 * - 8.对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始；
 * <p>
 * a happens-before b ：表示A的操作一定是对B的操作是可见的
 *
 * 上面八条是原生Java满足Happens-before关系的规则，但是我们可以对他们进行推导出其他满足happens-before的规则：
 *
 * 将一个元素放入一个线程安全的队列的操作Happens-Before从队列中取出这个元素的操作
 * 将一个元素放入一个线程安全容器的操作Happens-Before从容器中取出这个元素的操作
 * 在CountDownLatch上的倒数操作Happens-Before CountDownLatch#await()操作
 * 释放Semaphore许可的操作Happens-Before获得许可操作
 * Future表示的任务的所有操作Happens-Before Future#get()操作
 * 向Executor提交一个Runnable或Callable的操作Happens-Before任务开始执行操作
 *
 *
 */
public class HappensBefore {

    public static void main(String[] args) {

    }

}

/**
 * 程序顺序规则
 * - 单个线程中的每个操作，总是前一个操作 happens-before于该线程中任意后续操作
 */
class HappensBeforeDemo1 {

    private int a;
    private int b;
    private int c;

    /**
     * 1 happens-before 2
     * 2 happens-before 3
     * 3 happens-before 4
     */
    public void a() {
        a = 2; //操作1
        b = 2; //操作2
        c = a + b; //操作3
        System.out.println(c);
    }

}

/**
 * 监视器锁规则
 *  - 对一个锁的解锁，总是happens-before于随后对这个锁的加锁
 *
 *  示例：操作1 happens-before 操作2
 */
class HappensBeforeDemo2 {
    private Lock lock = new ReentrantLock();

    public void a() {
        lock.lock();
        System.out.println("...");
        lock.unlock(); //操作1 解锁
    }

    public void b() {
        lock.lock(); //操作2 加锁
        System.out.println("...");
        lock.unlock();
    }
}

/**
 * Start规则
 *  - 对于一个线程A启动另一个线程B的线程方法 那么线程A的所有代码 happens-before B 线程
 *
 *  示例：1 happens-before 2
 */
class HappensBeforeDemo3 {

    public void a() {
        System.out.println("a"); //1 启动另外一个线程的 线程方法
        new Thread(() -> {
            System.out.println("b"); //2
        }).start();
    }
}
