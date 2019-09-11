package com.jorgezhong.concurrent.c4;

/**
 * 锁重入： 当两个方法都使用了同一个锁对象，当一个方法调用另一个方法时，
 *         由于是同一个锁对象，也是可以进行访问的，而不是因为锁已经被获取过了，无法再次获取，理解为锁重入
 *         （一个线程内部反复使用同一个锁对象时）
 *
 * 重入锁：synchronized ReentrantLock都是重入锁
 * Project <learning>
 * Created by jorgezhong on 2019/9/10 20:39.
 */
public  class Demo {

    public synchronized void a() {
        System.out.println("a");
        b();
    }

    public synchronized void b() {
        System.out.println("b");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();

        //如果锁不可重入，则b()一直会等待
        //不同线程再去获取，就不是重入的概念了，这时候锁竞争，其他锁等待
        new Thread(demo::a).start();
        new Thread(demo::b).start();
    }

}
