package com.jorgezhong.concurrent.synchronized_;

/**
 * 内置锁
 * - JAVA每一个对象都可以用做锁，这些锁被称为内置锁
 *
 * 锁信息存储在对象的什么地方呢？
 * 答：存储在对象头中
 *
 * 对象头的信息：
 *  - Mark Word：存储对象的Hash值，锁信息，线程id,Epoch,对象的分代年龄信息，是否是偏向锁，锁标志位
 *  - Class Metadata Address：类型地址
 *  - Array Length：数组长度
 *
 * 偏向锁：每次获取锁和释放锁会浪费资源。很多情况下，竞争锁不是由多个线程，而是由一个线程在使用（实际上单线程在用多多线程的逻辑）。
 *        因此为了提高性能，这时候如果是同一个线程id来竞争锁，则不需要在获取锁了，这就是偏向锁
 *        场景：只有一个线程在访问同步代码块的时候，适用偏向锁
 *  - 偏向锁撤销：等到竞争偏向锁的线程出现的时候，那么持有该偏向锁的线程才撤销，释放锁
 *
 * 轻量级锁：多个线程进入同步代码块中，同时都能够获取锁
 *  - 加锁：在线程执行同步代码块之前，JAVA虚拟机会在当前线程的栈帧中创建用于存储锁记录的空间。并将对象头中的Mark Work记录复制过去
 *          然后才开始竞争锁，竞争成功，修改对象头锁标志位为轻量级锁，接着执行同步体。
 *          其他线程竞争失败是由于发现对象头中锁信息已经指向别的线程，修改当前线程栈帧中的锁记录失败导致的，于是失败的线程会不停的修改
 *          直到第一个线程释放了锁之后，竞争的线程修改锁记路成功，才成功竞争到锁。（竞争获取的过程通过自旋锁CAS对比修改实现的）
 *  - 升级：当第二个线程成功竞争到锁之后，执行完，并释放了。就会将轻量级锁升级为重量级锁。
 *          反之，如果第二个竞争的线程其实是第一个线程，那么继续使用轻量级锁
 *
 * 重量级锁：重量级锁会阻塞，重新唤醒比较消耗性能
 */
public class SynchronizedDemo {

}

class Sequence {
    private  int value;

    private static int staticValue;

    /**
     * synchronized放在普通方法上
     * 内置锁：当前类的实例this对象
     * 互斥锁：一个线程获得锁，下一个下次需等待锁释放
     * 线程在进入同步代码块之前都必须先获得锁，这里是内置锁
     *
     * @return
     */
    public synchronized int getNext() {
        return value++;
    }

    /**
     * synchronized放在静态方法上
     * 内置锁：当前的Class字节码对象 Sequence.class
     *
     * @return
     */
    public static synchronized int getPrevious() {
        return staticValue--;
    }

    /**
     * synchronized修饰同步代码块
     * 内置锁：自己指定一个对象
     * @return
     */
    public int xx() {
//        Object lock = new Object();
        Class<Sequence> lock = Sequence.class;
        //monitorenter 指令
        synchronized (lock) {

            if (value > 0) {
                return value;
            } else {
                return -1;
            }
        }
        //monitorexit 指令
    }


    public static void main(String[] args) {
        Sequence sequence = new Sequence();
        Runnable runnable = () -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":" + sequence.getNext());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();


    }
}
