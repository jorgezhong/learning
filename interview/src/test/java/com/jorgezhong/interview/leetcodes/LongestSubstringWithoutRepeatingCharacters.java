package com.jorgezhong.interview.leetcodes;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Project <learning>
 * Created by jorgezhong on 2019/10/14 21:30.
 */
public class LongestSubstringWithoutRepeatingCharacters {

    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 示例 1:
     * <p>
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * <p>
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * <p>
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     * <p>
     * 来源：力扣（LeetCode）
     */

    @Test
    public void test() {

        //map版本
        String str = "abcdesfaasdasdfasdfasdfasdfasdfaaaaqweqweqweqwefasdfd";
        int length = lengthOfLongestSubstring(str);
        System.out.println("length = " + length);

        //set版本
        int length0 = lengthOfLongestSubstring0(str);
        System.out.println("length0 = " + length0);


    }

    /**
     * 思路：滑动窗口，双指针
     * <p>
     * 逐步寻找子串，利用双指针[left,right]表示不重复子串，移动其中一个指针，不重复则移动right,重复了则移动left,每次移动都记录最不重复子串长度
     * <p>
     * <p>
     * 双指针遍历数组(str)：
     * 利用指针left表示：不重复子串的开始位置
     * 利用指针right表示：当前遍历位置
     * [left,right]:表示不重复子串
     * <p>
     * <p>
     * HashMap存储已遍历过的字符为key，其下标为value
     * <p>
     * 临时变量res:记录当前最大长度的不重复子串
     * <p>
     * 遍历 right++
     * 判断当前right指向的字符在HashMap中是否存在，存在则重复了，[left,right]之间的长度为当前不重复子串长度，与上一个不重复的子串长度比较取最大值。
     * HashMap.get(str.chatAt(right))为重复字符第一次出现的下标。将其下一个字符的下标设置为left的值。
     * <p>
     * 遍历字符和下标插入到HashMap中
     */
    private int lengthOfLongestSubstring(String str) {

        //边界
        if (str == null || str.length() ==0) return 0;

        //已知不重复子串最大长度
        int res = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int left = 0, right = 0; right < str.length(); right++) {

            //判断是否出现重复字符
            if (map.containsKey(str.charAt(right))) {
                //无重复子串的开始位置为第一次出现重复位置的下一个字符位置
                //存在情况：记录left值的时候跳过了长度比当前res值小的子串，此时left的值大于 map.get(str.charAt(right)) + 1的值
                left = Math.max(left, map.get(str.charAt(right)) + 1);
            }
            //取 “当前不重复子串最大长度” 与 “已知不重复子串最大值长度” 之间的最大值
            res = Math.max(res, right - left + 1);
            //当多次出现重复字符，同一个字符存储的是更大的下标
            map.put(str.charAt(right), right);
        }
        return res;
    }

    /**
     * HashSet版本思路：
     *  区别：移动left的同时删除掉left指向的元素，因为left是从0开始的，因此重复的元素也是逐个删除，最后set.size()就是不重复元素的长度
     * @param str
     * @return
     */
    private int lengthOfLongestSubstring0(String str) {

        //边界
        if (str == null || str.length() ==0) return 0;

        //已知不重复子串最大长度
        int res = 0;
        HashSet<Character> set = new HashSet<>();
        for (int left = 0, right = 0; right < str.length(); right++) {

            //判断是否出现重复字符
            if (set.contains(str.charAt(right))) {
                set.remove(str.charAt(left++));

            }else {
                set.add(str.charAt(right));
                res = Math.max(res, set.size());
            }

        }
        return res;
    }




}
