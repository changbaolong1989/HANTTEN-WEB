package com.hd.common.util;

import java.util.List;

/**
 * 集合常用工具
 *
 * Created by JLF on 2017/5/7.
 */
public final class ListToolUtils {

    /** 私有构造函数 */
    private ListToolUtils() {
    }
    /**
     * 判断paramList为空返回true，不为空返回false
     *
     * @param paramList 参数List
     * @return true : paramList size 为0 或 对象为空 false : paramList不为空并且有值
     */
    public static boolean isEmpty(List<?> paramList) {
        boolean resultBoolean = true;
        if (paramList != null && paramList.size() > 0) {
            resultBoolean = false;
        }
        return resultBoolean;
    }
}
