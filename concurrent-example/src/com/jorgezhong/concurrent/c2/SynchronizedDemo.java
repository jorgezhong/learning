package com.jorgezhong.concurrent.c2;

/**
 * 内置锁
 * - JAVA每一个对象都可以用做锁，这些锁被称为内置锁
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
        synchronized (lock) {

            if (value > 0) {
                return value;
            } else {
                return -1;
            }
        }
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
