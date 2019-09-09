package com.jorgezhong.thread.t2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaAsyncCal {


    /**
     * 求和
     * @param values
     * @return
     */
    public int add(List<Integer> values) {
        //使用并行流
        Stream<Integer> integerStream = values.parallelStream();

        IntStream intStream = integerStream.mapToInt(value -> value);

        return intStream.sum();

    }


    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(10, 20, 30, 40);

        int add = new LambdaAsyncCal().add(integers);

        System.out.println("add = " + add);
    }

}
