package com.jorgezhong.thread.t0;

/**
 * 重排序
 *  - 编译器和处理器为了提高程序执行的效率，对代码的指令进行重新排序，简单讲就是代码的顺序不一定是执行的顺序
 *
 * 数据依赖性（as-if-serial)：单线程情况下，无论如何进行重排序，保证执行结果是正常的
 *
 * 指令重排序分类
 *  - 编译器重排序和处理器重排序
 *
 * 指令重排序带来的影响：
 *  - as-if-serial：只是提升了单线程的新能
 *  - 对多线程环境影响较大
 *
 * 解决多线程下指令重排序问题
 *  - 竞争与同步
 */
public class Reorder {
}

class ReorderDemo{
    private int a;
    private int b;
    private int c;

    /**
     * 数据依赖性
     */
    public void a() {

        //写后读
        //读后写
        //写后写

        a = 1; //写操作
        b = 2;

        c = a; //写后读操作：对a来说是读操作，对c来说是写操作，先写后读
        b = c + a;

        System.out.println("b = " + b);
    }

    public static void main(String[] args) {
        new ReorderDemo().a();
    }
}

/**
 * 重排序在多线程中带来的问题
 * writer和reader分别是两个线程调用，由于指令重排，打印的b不一定是2 可能是1
 *
 * 备注：这里和线程安全问题无关，由指令重排导致
 */
class ReorderDemo1 {
    private int a;
    private boolean flag;

    public void writer() {
        /**
         * a 和 flag之间没有数据依赖性,因此cpu（处理器）会对这两行代码进行指令重排序
         */
        a = 1;
        flag = true;
    }

    public void reader() {
        if (flag) {
            /**
             * 由于 a和flag没有依赖,flag为true的情况下，a还未赋值，另一个线程就执行到这一行代码，得到的b可能是1
             */
            int b = a + 1;
            System.out.println("b = " + b);
        }
    }
}