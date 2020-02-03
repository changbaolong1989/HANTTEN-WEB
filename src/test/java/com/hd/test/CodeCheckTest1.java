package com.hd.test;

import java.io.*;

public class CodeCheckTest1 {

    /**
     * 文件拷贝
     *
     * @param is 输入流
     * @param os 输出流
     * @throws IOException 输入/输出异常
     */
    public static boolean copy(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = is.read(buffer)) >= 0) {//null point
            os.write(buffer, 0, count);
        }
        //close resource
        return true;
    }

}
