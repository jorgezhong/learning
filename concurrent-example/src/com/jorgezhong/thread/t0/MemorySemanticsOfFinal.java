package com.jorgezhong.thread.t0;

/**
 * final的内存语义
 * - 写final域的重排序规则：禁止把final域的写 重排序到构造方法之外
 * 1.JAVA的内存模型禁止编译器把final域的写重排序到构造方法之外
 * 2.编译器会在final域写之后，在构造方法执行完毕之前，插入一个内存屏障StoreStore,保证处理器把final域的写操作在构造方法中执行。
 * - 处理器内存屏障：
 * LoadLoad: load 1 LoadLoad load2 （确保load1指令优先于load2指令）
 * StoreStore：store1 StoreStore store2 （确保store1数据对其他处理器的可见优先于store2）
 * LoadStore：同理
 * StoreLoad：同理
 * <p>
 * - 读final域的重排序规则：在一个线程中，初次读对象引用和初次读该对象所包含的final域，Java内存模型禁止处理器重排序这两个操作
 * - final域为静态类型：static类型必须在static代码块或者变量后初始化
 * - final域为抽象类型：在构造方法内对一个final引用对象的成员域的写入，与随后在构造方法外把这个被构造对象的引用，赋值给一个引用变量，这两个操作之间不能重排序
 */
public class MemorySemanticsOfFinal {
}


/**
 * 写线程调用w(): 构造方法执行 -> final域写操作 -> 构造方法执行完毕  普通变量a可能被重排到构造方法外面
 * 读线程调用r()：读对象引用 -> 读对象的普通对象域 -> 读对象的final域（该对象构造方法必定执行完毕）
 */
class MemorySemanticsOfFinalDemo {
    private int a;

    private final int b;
    private static final int c;

    //实例初始化过程中执行
    public MemorySemanticsOfFinalDemo() {//1
        //对于a的写，可能被重排序到构造方法之外
        a = 20;//2
        //b被final修饰，因此禁止被重排序到构造方法之外
        b = 10;//3
    }//4

    //实例初始化过程中执行，代码块比构造方法优先执行
    {
//        b = 10;

    }

    //在类加载的过程中执行，实例化之前
    //b并非静态的，因此不能在此赋值
    static {
//        b = 10;
        c = 1;
    }


    private MemorySemanticsOfFinalDemo demo;

    /**
     * 写
     */
    public void w() {//5
        demo = new MemorySemanticsOfFinalDemo();//6
    }//7

    /**
     * 读
     */
    public void r() {

        MemorySemanticsOfFinalDemo demo = this.demo;//7
        int temp1 = demo.a;//8
        int temp2 = demo.b;//9
    }

}

/**
 * final域为抽象类型
 *
 * 写线程调用w(): 构造方法执行 -> 写final域的引用  StoreStore -> 构造方法执行完毕
 * 读线程调用r(): 读对象引用
 *
 * 写final域的引用 和 读对象引用 不能被重排序
 */
class MemorySemanticsOfFinalDemo2 {

    private final FinalObject obj;

    public MemorySemanticsOfFinalDemo2() {
        obj = new FinalObject();
        //对final域引用对象成员的写入
        obj.a = 10;
    }

    private MemorySemanticsOfFinalDemo2 demo2;

    public void w() {
        demo2 = new MemorySemanticsOfFinalDemo2();
    }


    public void r() {
        //把final域的引用付给另一个引用变量
        MemorySemanticsOfFinalDemo2 demo2 = this.demo2;

        if (demo2 != null) {
            FinalObject obj = demo2.obj;
        }

    }

    class FinalObject{
        int a;
    }
}