package com.jorgezhong.interview.leetcodes;

import org.junit.Test;

/**
 * Project <learning>
 * Created by jorgezhong on 2019/10/13 11:56.
 */
public class AddTwoNumber {


    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     *
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     *
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     *
     * 示例：
     *
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     *
     * 来源：力扣（LeetCode）
     */
    @Test
    public void test() {

        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode listNode = addTwoNumber(l1, l2);

        System.out.println(listNode.val);
        System.out.println(listNode.next.val);
        System.out.println(listNode.next.next.val);

    }

    /**
     * 思路：
     *  使用一个临时节点存储结果链表，使用临时变量替代l1 和 l2进行计算
     *  使用临时变量 sum 存储相加
     *  使用 sum % 10 判断是否进位, 其值也是下一位
     *      若进位，使用 sum / 10 仅为后的当前位
     *
     *  边界条件：l1 和 l2 不为空
     *
     *
     * 补充知识：取模和取余
     *      通常取模运算也叫取余运算，它们返回结果都是余数，符号相同时，两者不会冲突。
     *      - 取余，遵循尽可能让商向0靠近的原则
     *
     *      - 取模，遵循尽可能让商向负无穷靠近的原则
     *
     * - mod : 取除数的符号
     *  1、能整除时，其值为 0
     *  2、不能整除时，其值=除数×(整商+1)-被除数
     *
     * - rem : 取被除数的符号
     */
    private ListNode addTwoNumber(ListNode l1 , ListNode l2) {

        ListNode dummy = new ListNode(0);
        ListNode p1 = l1, p2 = l2;
        int sum = 0;
        ListNode cur = dummy;  //?
        while (p1 != null || p2 != null) {

            if (p1 !=null) {
                sum += p1.val;
                p1 = p1.next;
            }

            if (p2 != null) {
                sum += p2.val;
                p2= p2.next;
            }
            //取余数（个位）
            cur.next = new ListNode(sum % 10);
            //取整商（十位），下一位计算相加使用
            sum /= 10;
            cur = cur.next;
        }

        if (sum == 1) {
            cur.next = new ListNode(1);
        }
        return dummy.next;
    }


    private class ListNode {

        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
