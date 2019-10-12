package com.jorgezhong;

import java.util.Arrays;

/**
 * Project <learning>
 * Created by jorgezhong on 2019/10/12 9:43.
 */

public class DemoTest {


    public int[] sortArrayByParity(int[] A) {
        swap(A, 0, A.length - 1);
        return A;
    }

    public static void main(String[] args) {

        int[] arr = new int[5000];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        //奇数在后
        new DemoTest().swap(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
    }

    private void swap(int[] arr, int start, int end) {
        if(end - start <= 0 ) return;
        if (arr[start] % 2 != 0 ) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end]=temp;

            swap(arr, start, end - 1);
        } else if (start < end) {
            swap(arr, start + 1, end);
        }
    }


}
