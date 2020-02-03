package com.hd.common.util;

import sun.misc.BASE64Decoder;

import java.io.IOException;

public class Base64ToolUtil {

    /**
     * 从base64中获取字节数组
     * @param base64Str
     * @return
     */
    public static byte[] getByteArrayFromBase64Str(String base64Str) {
        byte[] b = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            b = decoder.decodeBuffer(base64Str);
            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }
}
