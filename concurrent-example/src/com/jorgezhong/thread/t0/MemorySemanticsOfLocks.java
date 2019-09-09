package com.jorgezhong.thread.t0;

/**
 * 锁的内存语义
 *  - 锁的释放与获取所建立的 happens-before关系
 *  - 锁的释放和获取的内存语义
 */
public class MemorySemanticsOfLocks {



}

/**
 * happens-before关系
 *  - 程序顺序规则：1 hp 2; 2 hp 3; 4 hp 5; 5 hp 6
 *  - 监视器规则：3 hp 4
 *  - 传递性
 *
 * 锁的释放和获取的内存语义:
 *  - 锁处理让临界区互斥执行外：
 *      对于同一个共享变量，线程A得到了锁，将值拷贝到线程本地缓存，修改完之后，写入主存，释放锁，
 *      这时候其他线程在获取锁，需要重新从主存中拷贝值到线程本地缓冲区中
 *  - 还可以让释放锁的线程向获取同一个锁的线程发送消息
 *      对于同一把锁，当释放锁的时候，同时通知获取到该锁的线程，需要重新拷贝新的共享变量的值了
 */
class MemorySemanticsOfLocksDemo {

    private int value;

    public synchronized void a() {//1 获取锁
        value++;//2
    }//3 释放锁

    public synchronized void b() {//4 获取锁
        int a = value;//5
        //处理其他的操作
    }//6释放锁

}