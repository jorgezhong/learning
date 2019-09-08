package com.jorgezhong.thread.t1;

public class RunnableWait implements Runnable {

    public static Object monitor;

    public RunnableWait(Object monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        synchronized (monitor) {
            System.out.println(Thread.currentThread() + "开始等待" + monitor);
            try {
                //wait，等待状态，与sleep不同，必须notify/notifyAll唤醒之后才会进入就绪状态
                monitor.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "等待结束！" + monitor);
        }
    }
}
