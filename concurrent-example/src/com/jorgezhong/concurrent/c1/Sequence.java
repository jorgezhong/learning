package com.jorgezhong.concurrent.c1;

/**
 * 线程安全问题
 */
public class Sequence {
    private int value;

    public int getNext() {
        /**
         * 分几步：
         * 1. 从主存中取value值，
         * 2. +1操作，
         * 3. 赋值给操作数栈中的value变量,
         * 4. 再写回主存，
         * 5. 返回操作数栈中的value变量
         *
         * 多线程的时候，由于存爱线程1还未写入主存，其他线程就已经从主存中取值了，因此操作数栈中计算的值其实是不对的，存在安全问题
         *
         * 解决：加锁
         */
        return value++;
    }


    public static void main(String[] args) {
        Sequence sequence = new Sequence();

        Runnable runnable = () -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() +":" + sequence.getNext());
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
