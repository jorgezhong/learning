package com.jorgezhong.concurrent.c5;

/**
 * volatile可见性示例
 *
 * 两个线程同时使用一个变量，另一个线程通过自旋判断，共享变量是否改变，改变了则执行。
 * 因此对于这两个线程使用同意一把锁，共享变量是可见的。
 *
 * Project <learning>
 * Created by jorgezhong on 2019/9/11 10:44.
 */
public class Demo2 {

    volatile boolean run = false;

    public static void main(String[] args) {

        Demo2 demo2 = new Demo2();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("执行了第" + i + "次");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            demo2.run = true;
        }).start();


        new Thread(() -> {
            while (!demo2.run){
                //自旋 线程2不执行
            }
            System.out.println("线程2执行了");
        }).start();


    }


}
