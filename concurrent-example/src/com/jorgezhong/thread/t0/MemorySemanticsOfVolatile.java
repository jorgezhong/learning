package com.jorgezhong.thread.t0;

/**
 * volatile的内存语义
 * - volatile修饰的变量在内存之中，线程之间是可见的
 * - Volatile读写所建立的happen-before关系
 * - Volatile读写的内存语义
 */
public class MemorySemanticsOfVolatile {
}

/**
 * 类比锁的内存语义
 * 锁： 获取 和 释放
 * volatile: 读 和 写
 *
 * happens-before关系
 *  程序顺序规则：1 hp 2; 2 hp 3; 3 hp 4; 4 hp 5
 *  volatile规则：2 hp 3
 *
 * 被volatile修饰的变量内存语义：读写
 *  - 写：将线程本地缓存中的变量写入主存中
 *  - 读：线程本地缓存中的值置为无效，再从主存中进行取值，拷贝到线程本地缓存中
 *
 *
 */
class MemorySemanticsOfVolatileDemo {

    private volatile boolean flag;
    private volatile int a;

    public void writer() {
        //这两个数据之间没有数据依赖性，处理器对这两行代码进行指令重排序
        a = 10;//1 写a
        flag = true;//2 写flag 当写一个volatile变量时，JAVA内存模型会把当前线程对应的本地内存中的共享变量写入主存中
    }

    public void reader() {
        if (flag) {//3 读flag，当读一个volatile变量时，JAVA内存模型会把当前线程对应的本地内存中的共享变量置为无效，然后从主存中读取共享变量
            int b = a + 1;//4 读a 写b
            System.out.println("b = " + b);//5 读b

        }
    }

}