package com.jorgezhong.thread.t1;

import java.util.concurrent.TimeUnit;

public class RunnableNotify implements Runnable {
    private Object monitor;

    /**
     * notify/wait需要指定监视器（lock）,等待和唤醒需要使用同一个监视器才生效
     * @param monitor
     */
    public RunnableNotify(Object monitor) {
        this.monitor = monitor;
    }




    @Override
    public void run() {
        synchronized (monitor) {
            try {
                //sleep：超市等待状态，与wait不同，过时会自动进入就绪状态
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "唤醒rwait!" + monitor);
            monitor.notifyAll();
            System.out.println(Thread.currentThread() + "唤醒结束!" + monitor);
        }
    }
}
