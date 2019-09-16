package com.jorgezhong.interview;

import org.junit.Test;

/**
 * 面试题
 * Project <learning>
 * Created by jorgezhong on 2019/9/16 9:52.
 */
public class Question {

    /**
     * 类型：自增变量,
     * 面试题：i、j、k分别输出多少？
     *
     * 关键点：局部变量表，操作数栈
     *
     * i = i++ 执行的指令
     *  - 1.将局部变量表的变量i放到操作数栈（入栈），局部变量表中i的值自增1。此时局部变量表中 i 的值为2
     *  - 2.将操作数栈的值赋值给i。此时局部变量表中的 i 的值变成了1
     * 因此，i的计算结果被丢弃了 i的值仍然为1
     *
     * int j = i++ 执行的指令
     *  - 1.将局部变量表的变量i放到操作数栈（入栈），局部变量表中i的值自增1。此时局部变量表中 i 的值为2
     *  - 将操作数栈中的值 1 复制给 j。此时局部变量表中 j 的值为 1
     * 因此，j的值为 1
     *
     *
     * int k = i + ++i * i++;
     *  - 先遇到 i ，i值先压入栈。此时操作数栈为2， 局部变量表中 i 的值为2
     *  - 执行 ++i：局部变量表中i自增，此时局部变量表中 i 的值为3，将执行结果压入栈，也就是把i的值压入栈，此时栈的值有两个[2,3]
     *
     *  - 再遇到 i：i值压入栈，此时栈有三个值 [2,3,3]
     *  - 执行 i++：i值自增，局部变量表中的 i 自增，此时局部变量表 i 的值为4
     *
     *  - 执行乘法：弹出操作栈顶两个数相乘，没有被赋值的变量，作为临时计算结果再压入栈，此时栈中变成两个值[2,9]
     *  - 执行加法：弹出操作栈顶两个数相加，计算结果赋值给 k
     *
     * 因此，此时k的值为11，i的值为4, j的值为1
     *
     */
    @Test
    public void selfIncreasingVariable() {

        int i = 1;
        i = i++;
        int j = i++;
        int k = i + ++i * i++;

        System.out.println("i = " + i);
        System.out.println("j = " + j);
        System.out.println("k = " + k);


    }


}
