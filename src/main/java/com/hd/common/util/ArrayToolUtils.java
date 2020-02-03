package com.hd.common.util;
/**
 * 数组工具类
 * @author JLF
 */
public final class ArrayToolUtils {

    /** 私有构造函数 */
    private ArrayToolUtils(){
    }

    /**
     * 判断数组是否为空，不支持基本数据类型数组的判断
     * 
     * @param paramArr
     *          数组
     * @return boolean
     *          是否为空
     */
    public static boolean isNull(Object[] paramArr) {
        boolean resultFlg = true;
        if (null != paramArr && paramArr.length > 0) {
            resultFlg = false;
        }
        return resultFlg;
    }
}
