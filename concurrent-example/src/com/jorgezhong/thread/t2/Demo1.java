package com.jorgezhong.thread.t2;

import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Demo1 {


    /**
     * 命名线程的名字示例
     */
    private static void threadNameDemo() {
        ExtendsThread d1 = new ExtendsThread("first-thread");
        ExtendsThread d2 = new ExtendsThread("second-thread");
        d1.start();
        d2.start();
    }

    /**
     * 守护线程示例
     * 守护线程是支持线程，例如垃圾回收器
     * 守护线程会随着主线程的停止而停止，不会因为 主线程生成的其他线程 还在工作而继续工作
     */
    private static void daemonThreadDemo() {
        //lambda + Runnable匿名内部类 实现线程
        Thread thread = new Thread(() -> {
            ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
            while (true) {
                System.out.println(threadGroup + "线程任务 执行了");
            }
        }, "daemon-thread");

        //设置守护线程
        thread.setDaemon(true);
        thread.start();

        try {
            //休眠2S后发现 daemon-thread同时也休眠了
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用线程组示例
     * 线程组是树结构，可以设置父亲
     */
    private static void threadGroupDemo() {
        //线程可分组，线程组是树结构
        ThreadGroup threadGroup = new ThreadGroup("thread-group");
        ExtendsThread d3 = new ExtendsThread(threadGroup, "group-thread");
        d3.start();
    }

    /**
     * 线程的中断示例
     * interrupt的原理是，提供了一个中断标记的状态，
     * 在使用的时候，如果执行其他类似sleep的方法时，若判断标记为中断状态，则会抛出InterruptedException异常
     * 因此使用的时候需要通过interrupted()来判断是否中断
     */
    private static void threadInterrupt() {


        InterruptThread d1 = new InterruptThread("first-thread");
        InterruptThread d2 = new InterruptThread("second-thread");

        d1.start();
        d2.start();

        //中断线程，其实是修改其中断标记
        d1.interrupt();

    }

    /**
     * runnable线程示例
     */
    private static void runnableThreadDemo() {
        Thread thread = new Thread(new RunnableThread());
        thread.start();

    }

    /**
     * Thread匿名内部类实现线程
     */
    private static void anonymousThreadInnerClassThreadDemo() {
        new Thread() {
            @Override
            public void run() {
                System.out.println(" thread running");
            }
        }.start();
    }

    /**
     * Runnable匿名内部类实现线程
     */

    private static void anonymousRunnableInnerClassThreadDemo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(" thread running");
            }
        }).start();
    }

    /**
     * lambda表达式实现
     */
    private static void lambdaThreadDemo() {
        new Thread(() -> System.out.println(" thread running")).start();
    }

    /**
     * Callable线程示例
     */
    private static void callableThreadDemo() {
        //创建一个带返回值的任务
        CallableThread callableThread = new CallableThread();

        //FutureTask实现了RunnableFuture接口，RunnableFuture父类是Runnable接口，只是做了一层包装
        //RunnableFuture还实现了Future接口，对结果进行了包装
        //FutureTask的run方法有处理执行的结果，返回
        FutureTask<Integer> integerFutureTask = new FutureTask<>(callableThread);

        Thread thread = new Thread(integerFutureTask);
        thread.start();

        try {
            //获取线程执行的结果
            Integer result = integerFutureTask.get();
            System.out.println("result = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    private static void timerDemo() {

        Timer timer = new Timer();



    }

    public static void main(String[] args) {
//        threadNameDemo();
//        threadGroupDemo();
//        daemonThreadDemo();
//        threadInterrupt();
//        runnableThreadDemo();
//        lambdaThreadDemo();
//        anonymousThreadInnerClassThreadDemo();
//        anonymousRunnableInnerClassThreadDemo();

//        callableThreadDemo();


    }
}

/**
 * 通过继承Thread实现线程
 */
class ExtendsThread extends Thread {

    public ExtendsThread(String name) {
        super(name);
    }

    public ExtendsThread(ThreadGroup group, String name) {
        super(group, name);
    }

    @Override
    public void run() {
        System.out.println(getThreadGroup() + getName() + "线程任务 执行了...");
    }
}


class InterruptThread extends Thread {
    public InterruptThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        //如果不中断，则执行
        while (!interrupted()) {
            System.out.println(getThreadGroup() + getName() + "线程任务 执行了...");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 通过实现Runnable接口实现线程
 * Runnable其实只是一个任务接口
 * Thread类持有了一个target属性是Runnable类型的
 * Thread.run()方法，默认是执行target.run()的
 */
class RunnableThread implements Runnable {
    @Override
    public void run() {
        while (true)
            System.out.println("thread running");
    }
}

/**
 * 通过实现Callable<T>实现线程
 * Callable带返回值类型 T
 */
class CallableThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("callable running ...");
        Thread.sleep(3000);
        return 1;
    }
}

