package com.hd.test;

public class CodeCheckTest2 {

    /**
     * 数组拷贝，当遇到某个字符串时停止
     *
     * @param a      源数组
     * @param b      目标数组
     * @param ending 结束的字符串
     */
    public void copy(String[] a, String[] b, String ending) {
        int index;
        String temp = null;
        System.out.println(temp.length());//null point
        int length = a.length;//never used
        for (index = 0; index < a.length; index++) {
            if (true) {//always true
                if (temp == ending) {//对象比较
                    break;
                }
                b[index] = temp;//缺少 数组下标越界检查
            }
        }
    }

}
