package com.hd.common.util;

/**
 * 字符串工具类
 *
 * @author JLF
 * Created in 2018/5/12 16:34
 */
public final class StringToolUtils {

    /**
     * 字符串常量：0
     */
    public static final String STRING_ZERO = "0";

    /**
     * 字符串常量：1
     */
    public static final String STRING_ONE = "1";

    /**
     * 空字符串
     */
    public static final String STRING_EMPTY = "";

    /**
     * 默认字符集
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /***/
    private StringToolUtils() {
    }

    /**
     * 判断字符串是否为null/空字符串
     * 
     * @param checkStr
     *            验证String对象
     * 
     * @return True ：空 False ：非空
     */
    public static boolean isNull(String checkStr) {

        boolean returnBoolean = false;
        if (checkStr == null || "".equals(checkStr)) {
            returnBoolean = true;
        }
        return returnBoolean;

    }

    /**
     * null值转换
     * 
     * @param convertObj
     *            格式变换String对象
     * @param defaultStr
     *            转换后默认值
     * 
     * @return
     */
    public static String convertNullObjectToString(Object convertObj, String... defaultStr) {

        String renturnStr = "";
        if (convertObj != null) {
            renturnStr = String.valueOf(convertObj);
        } else if (defaultStr != null && defaultStr.length > 0 && defaultStr[0] != null && !"".equals(defaultStr[0])) {
            renturnStr = defaultStr[0];
        }
        return renturnStr;
    }

    /**
     * null值转换
     *
     * @param convertCharArr
     *            格式变换char数组
     * @param defaultStr
     *            转换后默认值
     *
     * @return
     */
    public static String convertNullCharArrToString(char[] convertCharArr, String... defaultStr) {

        String renturnStr = "";
        if (convertCharArr != null && convertCharArr.length > 0) {
            renturnStr = String.valueOf(convertCharArr);
        } else if (defaultStr != null && defaultStr.length > 0 && defaultStr[0] != null && !"".equals(defaultStr[0])) {
            renturnStr = defaultStr[0];
        }
        return renturnStr;
    }

    /**
     * null值转换
     *
     * @param convertByteArr
     *            byte
     * @param defaultStr
     *            转换后默认值
     *
     * @return
     */
    public static String convertNullByteArrToString(byte[] convertByteArr, String... defaultStr) {

        String renturnStr = null;
        try {
            if (convertByteArr != null && convertByteArr.length > 0) {
                renturnStr = new String(convertByteArr,StringToolUtils.DEFAULT_CHARSET);
            } else if (defaultStr != null && defaultStr.length > 0 && defaultStr[0] != null && !"".equals(defaultStr[0])) {
                renturnStr = defaultStr[0];
            }
        }catch (Exception e){
            renturnStr = "";
        }
        return renturnStr;
    }

    /**
     * 将参数字符串中的oldChar替换为newChar
     * 
     * @param paramStr 要操作的字符串
     * @param oldChar 要替换掉的字符
     * @param newChar 将 paramStr 中的 oldChar 替换为 newChar
     * @return
     */
    public static String doReplaceString(String paramStr, CharSequence oldChar, CharSequence newChar) {
        String resultStr = null;
        if (!isNull(paramStr)) {
            resultStr = paramStr.replace(oldChar, newChar);
        }
        return resultStr;
    }

    /**
     * 将字符串解析为字符串数组。通过paramSeparator分割
     * 
     * @param paramStr 参数字符串
     * @param paramSeparator 参数分隔符
     * @return
     */
    public static String[] doSplitStrToStrArray(String paramStr, String paramSeparator) {
        String[] resultStrArr = null;
        if (!isNull(paramStr)) {
            resultStrArr = paramStr.split(paramSeparator);
        }
        return resultStrArr;
    }

    /**
     * 将字符串解析为字符串数组。通过paramSeparator分割
     *
     * @param paramStr
     * @return
     */
    public static String[] toSplitStrToStrArray(String paramStr, String paramSeparator) {
        String[] resultStrArr = null;
        if (!isNull(paramStr)) {
            resultStrArr = paramStr.split(paramSeparator);
        }
        return resultStrArr;
    }

}