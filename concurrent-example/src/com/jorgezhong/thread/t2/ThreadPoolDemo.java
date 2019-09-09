package com.jorgezhong.thread.t2;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池使用
 */
public class ThreadPoolDemo {

    private void executorsDemo() {
        //固定容量的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> System.out.println(Thread.currentThread().getName() + "线程任务 执行中.."));
        }

        //线程池执行完毕之后，并不会销毁，调用shutdown销毁
        threadPool.shutdown();
    }

    public static void main(String[] args) {
        new ThreadPoolDemo().executorsDemo();
    }

}
