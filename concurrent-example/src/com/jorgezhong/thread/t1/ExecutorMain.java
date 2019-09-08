package com.jorgezhong.thread.t1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorMain {

    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        Object monitor = new Object();

        RunnableWait runnableWait = new RunnableWait(monitor);
        RunnableNotify runnableNotify = new RunnableNotify(monitor);
        //创建线程任务，初始化状态
        Thread thread = new Thread(runnableWait);

        //启动线程，就绪状态
        thread.start();

        runnableNotify.run();

    }

    private static void test1() {
        //lock is monitor
        Object monitor = new Object();
        ExecutorService exe = Executors.newCachedThreadPool();
        exe.execute(new RunnableWait(monitor));
        exe.execute(new RunnableNotify(monitor));
        exe.shutdown();
    }
}
