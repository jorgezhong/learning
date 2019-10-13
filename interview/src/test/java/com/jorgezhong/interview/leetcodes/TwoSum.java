package com.jorgezhong.interview.leetcodes;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Project <learning>
 * Created by jorgezhong on 2019/10/13 0:07.
 */
public class TwoSum {


    /**
     * 1. 两数之和
     * <p>
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     * <p>
     * 示例:
     * <p>
     * 给定 nums = [2, 7, 11, 15], target = 9
     * <p>
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     * <p>
     * 来源：力扣（LeetCode）
     */


    @Test
    public void test() {

        int[] nums = {2, 7, 11, 15};

        int[] res = twoSum(nums, 9);

        System.out.println(Arrays.toString(res));

    }

    /**
     * 思路：
     * 利用HashMap，将数组中的值存储再key，将下标存储再value中
     * 遍历数组，将数组的值和下标初始化到HashMap中
     * 判断 target - arr[i] 的值是否存在HashMap中，存在则返回当前和，HashMap满足条件值的下标。
     * <p>
     * 边界判断：arr != null && arr.length>=2
     *
     * 时间复杂度：O(n)   -- 对n做一次遍历
     * 空间复杂度：O(n)   -- 使用了HashMap
     *
     * @param arr
     * @param target
     * @return
     */
    private int[] twoSum(int[] arr, int target) {
        //初始化返回值
        int[] res = {-1,-1};

        //边界条件
        boolean boundary = arr != null && arr.length >= 2;
        if (!boundary) return res;

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            hashMap.put(arr[i], i);
            //关键条件
            int first = target - arr[i];
            if (hashMap.containsKey(first)) {
                res[0] = hashMap.get(first);
                res[i] = i;
            }
        }
        return res;

    }


}
