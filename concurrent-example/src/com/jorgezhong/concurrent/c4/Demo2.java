package com.jorgezhong.concurrent.c4;

import java.util.Random;

/**
 * 自旋：可以理解为CPU的时间片
 * <p>
 * 示例：自旋等待所有的线程执行完毕
 * <p>
 * Project <learning>
 * Created by jorgezhong on 2019/9/11 9:41.
 */
public class Demo2 {

    public static void main(String[] args) {

        Runnable runnable = () -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + ": 线程开始执行");

            try {
                Thread.sleep(new Random().nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ": 线程执行完毕");
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();

        //当前活跃线程只剩下main线程
        while (Thread.activeCount() != 1) {
            //自旋 等待，空转CPU
        }
        System.out.println("所有线程执行完毕了");

    }

}
