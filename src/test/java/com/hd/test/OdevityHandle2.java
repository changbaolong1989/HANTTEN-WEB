package com.hd.test;

import java.math.BigDecimal;
import java.util.Scanner;

public class OdevityHandle2 {

    private static StringBuilder sb = new StringBuilder("1");

    public static void main(String[] args) {
        BigDecimal scale = new BigDecimal(1000000);
        Scanner in = new Scanner(System.in);
        System.out.println("请输入总人数：");
        long num = in.nextLong();
        long startTime = System.nanoTime();
        System.out.println("开始计算……………………");
        System.out.println("开始时间纳秒数：" + startTime);
        System.out.println("您应该站在第：" + handle(num) + "位");
        long endTime = System.nanoTime();
        System.out.println("结束时间纳秒数：" + endTime);
        BigDecimal naSecond = new BigDecimal(endTime - startTime);
        System.out.println("耗时：" + naSecond + "纳秒，约等于" + naSecond.divide(scale) + "毫秒");
    }

    private static long handle(long num) {
        long result = 1;
        int count = Long.toBinaryString(num).length() - 1;
        if (count != 0) {
            for (int i = 0; i < count; i++) {
                sb.append("0");
            }
            result = Long.valueOf(sb.toString(), 2);
        }
        return result;
    }


}
