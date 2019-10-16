package com.jorgezhong.interview.huawei;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project <learning>
 * Created by jorgezhong on 2019/10/15 11:38.
 */
public class HightestScore {

    /**
     * 老师想知道从某某同学当中，分数最高的是多少，现在请你编程模拟老师的询问。当然，老师有时候需要更新某位同学的成绩.
     * 输入描述:
     * <p>
     * <p>
     * 输入包括多组测试数据。
     * 每组输入第一行是两个正整数N和M（0 < N <= 30000,0 < M < 5000）,分别代表学生的数目和操作的数目。
     * 学生ID编号从1编到N。
     * 第二行包含N个整数，代表这N个学生的初始成绩，其中第i个数代表ID为i的学生的成绩
     * 接下来又M行，每一行有一个字符C（只取‘Q’或‘U’），和两个正整数A,B,当C为'Q'的时候, 表示这是一条询问操作，他询问ID从A到B（包括A,B）的学生当中，成绩最高的是多少
     * 当C为‘U’的时候，表示这是一条更新操作，要求把ID为A的学生的成绩更改为B。
     * <p>
     * <p>
     * 输出描述:
     * <p>
     * <p>
     * 对于每一次询问操作，在一行里面输出最高成绩.
     * <p>
     * <p>
     * 输入例子:
     * <p>
     * <p>
     * 5 7
     * 1 2 3 4 5
     * Q 1 5
     * U 3 6
     * Q 3 4
     * Q 4 5
     * U 4 5
     * U 2 9
     * Q 1 5
     * <p>
     * <p>
     * 输出例子:
     * <p>
     * <p>
     * 5
     * 6
     * 5
     * 9
     */

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        do {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            ArrayList<Integer> score = new ArrayList<>();
            ArrayList<Integer> result = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                score.add(scanner.nextInt());
            }
            char a;
            int b, c;
            for (int i = 0; i < m; i++) {

                a = scanner.next().charAt(0);
                b = scanner.nextInt();
                c = scanner.nextInt();

                if ('Q' == a) {
                    if (b >= c) {
                        //交换
                        int t = b;
                        b = c;
                        c = t;
                    }
                    //b-c中选择最大的数
                    int max = score.get(b - 1);
                    for (int j = b; j < c; j++) {
                        Integer cur = score.get(j);
                        max = cur > max ? cur : max;
                    }
                    result.add(max);
                }
                if ('U' == a) {
                    score.set(b - 1, c);
                }
            }

            for (int i = 0; i < result.size(); i++) {
                System.out.println(result.get(i));
            }

        } while (scanner.hasNext());
        scanner.close();

    }

}





