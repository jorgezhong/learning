package com.jorgezhong.concurrent.volatile_;

/**
 * volatile称之为轻量级锁，被volatile修饰的变量，在线程之间是可见的
 *
 * 可见：一个线程修改了这个变量的值，在另一个线程中能读取到这个修改后的值
 *
 * synchronized除了线程之间互斥以外，还有一个非常大的功能就是保证变量的可见性
 *
 *
 * - volatile实现原理：lock指令的生效
 *  加了volatile的变量回使用lock指令，之后降低了CPU缓存的使用率，同时降低重排序
 *  多处理器的系统上
 *      - 将当前处理器的缓存行（CPU缓存的最小单位）的内容写回到系统内存（写回主存）
 *      - 写回内存的操作会使其他CPU缓存了该内存地址的数据失效（通知其他线程）
 *
 *
 *  volatile特性： 不保证原子性操作（重排序还是会发生），只保证可见，因此volatile并不能取代                      synchronized。并不需要保证原子性的场景可用volatile
 *
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



