package com.jorgezhong.concurrent.c5;

/**
 * volatile称之为轻量级锁，被volatile修饰的变量，在线程之间是可见的
 *
 * 可见：一个线程修改了这个变量的值，在另一个线程中能读取到这个修改后的值
 *
 * synchronized除了线程之间互斥以外，还有一个非常大的功能就是保证变量的可见性
 *
 * Project <learning>
 * Created by jorgezhong on 2019/9/11 10:27.
 */
public  class Demo {

//    private int a = 1;
    private volatile int a = 1;

    public int getA() {
        return a;
    }

    public void setA(int a) {

        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.a = a;
    }

    /**
     * 示例：当一个线程修改了值，同时另一个线程并不能拿到修改后的值，因此不可见
     * 解救：使用volatile修饰保证可见性
     * @param args
     */
    public static void main(String[] args) {
        //保证可见性的前提是多个线程拿到的是同一把锁
        Demo demo = new Demo();
        new Thread(() -> demo.setA(10)).start();
        //该线程拿到的a 可能是1而不是10
        new Thread(() -> System.out.println(demo.getA())).start();


        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(demo.getA());

    }
}



