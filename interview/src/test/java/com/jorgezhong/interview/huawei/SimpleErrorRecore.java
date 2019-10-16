package com.jorgezhong.interview.huawei;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Project <learning>
 * Created by jorgezhong on 2019/10/15 14:08.
 */
public class SimpleErrorRecore {

    /**
     * 开发一个简单错误记录功能小模块，能够记录出错的代码所在的文件名称和行号。
     * 处理:
     * 1.记录最多8条错误记录，对相同的错误记录(即文件名称和行号完全匹配)只记录一条，错误计数增加；(文件所在的目录不同，文件名和行号相同也要合并)
     * 2.超过16个字符的文件名称，只记录文件的最后有效16个字符；(如果文件名不同，而只是文件名的后16个字符和行号相同，也不要合并)
     * 3.输入的文件可能带路径，记录文件名称不能带路径
     * <p>
     * 输入描述:
     * 一行或多行字符串。每行包括带路径文件名称，行号，以空格隔开。
     * <p>
     * 文件路径为windows格式
     * <p>
     * 如：E:\V1R2\product\fpgadrive.c 1325
     * <p>
     * <p>
     * 输出描述:
     * 将所有的记录统计并将结果输出，格式：文件名 代码行数 数目，一个空格隔开，如: fpgadrive.c 1325 1
     * <p>
     * 结果根据数目从多到少排序，数目相同的情况下，按照输入第一次出现顺序排序。
     * <p>
     * 如果超过8条记录，则只输出前8条记录.
     * <p>
     * 如果文件名的长度超过16个字符，则只输出后16个字符
     * 示例1
     * 输入
     * E:\V1R2\product\fpgadrive.c 1325
     * 输出
     * fpgadrive.c 1325 1
     */

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //初始化path、filename、key、map
        String path, filename, key;
        Map<String, Integer> map = new HashMap<>();

        while (scanner.hasNext()) {
            path = scanner.next();

            int index = path.lastIndexOf("\\");
            filename = (index < 0) ? path : path.substring(index + 1);

            int errorLineNum = scanner.nextInt();
            key = filename + " " + errorLineNum;

            map.computeIfPresent(key, (k, v) -> v + 1);
            map.putIfAbsent(key, 1);
            sortAndPrint(map);
        }
        scanner.close();

    }

    private static void sortAndPrint(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = map.entrySet().stream().sorted((o1, o2) -> {
            int des = o2.getValue() - o1.getValue();
            int desc = o1.getValue() - o2.getValue();
            return des == 0 ? desc : des;
        }).collect(Collectors.toList());

        for (int i = 0; i < 8 && i < list.size(); i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            String[] str = entry.getKey().split(" ");
            String filenameShortCut = (str[0].length() < 16) ? str[0] : str[0].substring(str[0].length() - 16);
            String errorLineNum = str[1];
            System.out.println(filenameShortCut + " " + errorLineNum + " " + entry.getValue());
        }
    }

}
