package com.hd.test;

import java.io.*;

public class CodeCheckTest3 {

    /**
     * 读取文件
     * @param file  文件
     */
    public static void readFile(File file) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            os = new FileOutputStream("E:\\SealApply3.png");
            CodeCheckTest1.copy(is, os);//未使用方法返回值
            is.close();
            os.close();
        } catch (IOException e) {
            //可能造成I/O流未关闭
            e.printStackTrace();
        } finally {
            //空的try/catch/finally块
        }
    }

    public static void main(String[] args) {
        File file = new File("E:\\SealApply.png");
        readFile(file);
    }

}
