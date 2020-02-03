package com.hd.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OdevityHandle {

    private static StringBuilder sb = new StringBuilder("1");
    private static BigDecimal scale = new BigDecimal(1000000);
    private static final BigInteger ZERO = new BigInteger("0");

    public static void main(String[] args) {
        BigInteger num;
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("请输入总人数：");
            num = in.nextBigInteger();
            if (num.compareTo(ZERO) <= 0) {
                System.out.println(num);
            } else {
                long startTime = System.nanoTime();
                System.out.println("开始计算……………………");
                System.out.println("开始时间纳秒数：" + startTime);
                System.out.println("您应该站在第：" + handle(num.toString()) + "位");
                long endTime = System.nanoTime();
                System.out.println("结束时间纳秒数：" + endTime);
                BigDecimal naSecond = new BigDecimal(endTime - startTime);
                System.out.println("耗时：" + naSecond + "纳秒，约等于" + naSecond.divide(scale) + "毫秒");
            }
        } catch (InputMismatchException e) {
            System.out.println("我要控制我自己");
        }
    }

    private static BigInteger handle(String num) {
        BigInteger result = new BigInteger("1");
        long count = new BigInteger(num, 10).toString(2).length() - 1;
        if (count != 0) {
            for (int i = 0; i < count; i++) {
                sb.append("0");
            }
            result = new BigInteger(new BigInteger(sb.toString(), 2).toString(10));
        }
        return result;
    }

}
