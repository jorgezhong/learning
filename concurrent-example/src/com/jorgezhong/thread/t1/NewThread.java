package com.jorgezhong.thread.t1;

/**
 * 1. sleep和wait的区别：
 * sleep是超时等待，需要指定时间，指定的时间经过后会冲进进入就绪状态
 * wait是等待，需要经过notify/notifyAll唤醒后才会进入就绪状态
 */
public class NewThread implements Runnable {


    @Override
    public synchronized void run() {
//        while (true) {
        String name = Thread.currentThread().getName();
        try {
            //等待状态，需要唤醒，不唤醒就一直处于该状态
            wait();
            System.out.println(name + " 线程任务 运行了...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "等待状态结束");
    }

//    }
}


class Test {
    public static void main(String[] args) {
        NewThread target = new NewThread();
        //创建线程任务
        /*
         * 创建线程，指定线程任务
         * 初始化状态
         */
        Thread thread = new Thread(target);

        /*
         * 启动线程
         * 就绪状态
         */
        thread.start();

//        while (true) {

        synchronized (target) {

            System.out.println("主线程执行了...");

            try {
                //休眠状态 100ms后进入就绪状态
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*
             *
             * 唤醒所有等待状态
             * wait notify方法必须要跟一个同步监视器，而且该同步监视器所指定的对象必须是当前类的实例
             *
             */
            target.notifyAll();
        }
//        }
    }
}