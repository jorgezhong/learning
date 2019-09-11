package com.jorgezhong.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * java.util.concurrent.atomic 中类的分类
 * - 原子更新基本类型
 * - 原子更新数组
 * - 原子更新抽象类型
 * - 原子更新字段（类中的字段）
 * <p>
 *
 * atomic类的底层其实是使用的 unsafe类 利用的还是自旋CAS的原理
 * 以下这段代码的解读
 * <p>
 *     public final int getAndUpdate(IntUnaryOperator updateFunction) {
 *         int prev, next;
 *         do {
 *             prev = get();
 *             next = updateFunction.applyAsInt(prev);
 *         } while (!compareAndSet(prev, next));
 *         return prev;
 *     }
 * </p>
 * int current = get();           //获取当前值
 * int next = current + 1;        //计算期望值
 * boolean flag cas(current,next);//CAS比较当前值是否被其他线程修改
 * if(flag == true) goto line 1  //根据CAS的结果，如果当前值被其他线程修改了，则跳到第一行重新获取当前值
 * else return current           //知道修改期望值的时候，发现当前值没有被修改，修改当前值为期望值成功，返回当前值
 *
 *
 * Project <learning>
 * Created by jorgezhong on 2019/9/11 11:56.
 */
public class Demo {
}


class Sequecnce {

    //基础类
    private AtomicInteger value = new AtomicInteger(0);

    private int[] s = {2, 1, 4, 6};

    //数组
    AtomicIntegerArray array = new AtomicIntegerArray(s);

    //对实例的get/set进行原子操作，而不是属性
    AtomicReference<User> user = new AtomicReference<>();

    //类的Integer类型的属性 进行update的原子操作
    AtomicIntegerFieldUpdater<User> userAge = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");


    public int getNext2() {
        User user = new User();
        return userAge.getAndIncrement(user);
    }

    public int getNext1() {

        // + 10
        return array.getAndIncrement(1);
    }

    public int getNext() {
        //自增 原子++
        return value.getAndIncrement();
    }

    public static void main(String[] args) {
        Sequecnce sequecnce = new Sequecnce();
        Runnable runnable = () -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":" + sequecnce.getNext());
                sleep(100);
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }

    private static void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class User {

    private String name;
    public volatile int age;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }
}