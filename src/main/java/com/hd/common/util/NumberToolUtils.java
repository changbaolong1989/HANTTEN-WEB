package com.hd.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NumberToolUtils {

    public final static Integer DEFAULT_INT_ZERO = 0;

    public final static Integer DEFAULT_INT_ONE = 1;

    public final static Integer DEFAULT_INT_FIVE = 5;

    public final static Long DEFAULT_LONG_ZERO = 0L;

    public final static Double DEFAULT_DOUBLE_ZERO = 0.0;

    public final static Double DOUBLE_HUNDRED = 100.0;

    public final static Double DOUBLE_THOUSAND = 1000.0;

    public final static BigDecimal DECIMAL_THOUSAND = new BigDecimal(1000.0D);

    public final static int INT_ZERO = 0;

    /**
     * 浮点数 保留小数位数
     */
    public final static int INT_SCALE_FLOAT = 2;

    /**
     * 整数 保留小数位数
     */
    public final static int INT_SCALE_INTEGER = 0;

    private NumberToolUtils() {
    }

    /**
     * null转换
     *
     * @param convertObject 格式变换Integer对象
     * @param defaultInt    默认值
     * @return 0/默认值
     */
    public static Integer convertNullToInteger(Object convertObject, Integer... defaultInt) {

        Integer renturnInt = null;
        try {
            renturnInt = new Integer(String.valueOf(convertObject));
        } catch (Exception e) {
            if (defaultInt != null && defaultInt.length > 0 && defaultInt[0] != null) {
                renturnInt = defaultInt[0];
            } else {
                renturnInt = new Integer(DEFAULT_INT_ZERO);
            }

        }
        return renturnInt;

    }

    /**
     * null转换
     *
     * @param convertObject 格式变换Long对象
     * @param defaultLong   默认值
     * @return 0/默认值
     */
    public static Long convertNullToLong(Object convertObject, Long... defaultLong) {

        Long renturnLong = null;
        try {
            renturnLong = new Long(String.valueOf(convertObject));
        } catch (Exception e) {
            if (defaultLong != null && defaultLong.length > 0 && defaultLong[0] != null) {
                renturnLong = defaultLong[0];
            } else {
                renturnLong = new Long(DEFAULT_INT_ZERO);
            }

        }
        return renturnLong;

    }

    /**
     * null转换
     *
     * @param convertObject     格式变换BigDecimal对象
     * @param defaultBigDecimal 默认值
     * @return 0/默认值
     */
    public static BigDecimal convertNullToBigDecimal(Object convertObject, BigDecimal... defaultBigDecimal) {

        BigDecimal renturnBigDecimal = null;
        try {
            renturnBigDecimal = new BigDecimal(String.valueOf(convertObject));
        } catch (Exception e) {
            if (defaultBigDecimal != null && defaultBigDecimal.length > 0 && defaultBigDecimal[0] != null) {
                renturnBigDecimal = defaultBigDecimal[0];
            } else {
                renturnBigDecimal = new BigDecimal(DEFAULT_INT_ZERO);
            }

        }
        return renturnBigDecimal;

    }

    /**
     * null转换
     *
     * @param convertObject 格式变换Double对象
     * @param defaultDouble 默认值
     * @return 0/默认值
     */
    public static Double convertNullToDouble(Object convertObject, Double... defaultDouble) {

        Double renturnDouble = null;
        try {
            renturnDouble = new Double(String.valueOf(convertObject));
        } catch (Exception e) {
            if (defaultDouble != null && defaultDouble.length > 0 && defaultDouble[0] != null) {
                renturnDouble = defaultDouble[0];
            } else {
                renturnDouble = new Double(DEFAULT_DOUBLE_ZERO);
            }

        }
        return renturnDouble;
    }

    /**
     * 将paramNumObj 转化为保留paramScaleNum位小数的格式化字符串（00,000,000格式，四舍五入）
     *
     * @param paramNumObj   参数 数字
     * @param paramScaleNum 保留小数位数
     * @return 四舍五入，格式化后的数字字符串
     */
    public static String convertNumberToFormatStringWithHalfUp(Object paramNumObj, int paramScaleNum) {
        // 排除paramNumObj为空的情况并（为空时置0）并将其转化为double型，
        BigDecimal tempBigDecimal = convertNullToBigDecimal(paramNumObj);

        DecimalFormat formatter = new DecimalFormat();
        // 设置舍入模式：四舍五入
        formatter.setRoundingMode(RoundingMode.HALF_UP);

        StringBuilder pattern = new StringBuilder();
        // 拼接format表达式
        pattern.append(",##0");
        // 按照保留小数点位数拼接表达式
        for (int i = 0; i < paramScaleNum; i++) {
            if (i == 0) {
                pattern.append(".");
            }
            pattern.append(0);
        }
        // 写入表达式
        formatter.applyPattern(pattern.toString());
        // 返回处理过的字符串
        return formatter.format(tempBigDecimal);
    }

    /**
     * 将paramNumObj 转化为保留paramScaleNum位小数的字符串（四舍五入）
     *
     * @param paramNumObj   要转换的数值 参数
     * @param paramScaleNum 保留小数位数
     * @return paramNumObj 四舍五入保留 paramScaleNum位小数的字符串
     */
    public static String convertNumberToStringWithHalfUp(Object paramNumObj, int paramScaleNum) {
        // 排除paramNumObj为空的情况并（为空时置0）并将其转化为double型，
        BigDecimal tempBigDecimal = convertNullToBigDecimal(paramNumObj);

        DecimalFormat formatter = new DecimalFormat();
        // 设置舍入模式：四舍五入
        formatter.setRoundingMode(RoundingMode.HALF_UP);

        StringBuffer pattern = new StringBuffer();
        // 拼接format表达式
        pattern.append("#0");
        // 按照保留小数点位数拼接表达式
        for (int i = 0; i < paramScaleNum; i++) {
            if (i == 0) {
                pattern.append(".");
            }
            pattern.append(0);
        }
        // 写入表达式
        formatter.applyPattern(pattern.toString());
        // 返回处理过的字符串
        return formatter.format(tempBigDecimal);
    }

    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    /**
     * 正整数
     * @param orginal
     * @return
     */
    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    /**
     * 股整数
     * @param orginal
     * @return
     */
    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }


    /**
     * 整数
     * @param orginal
     * @return
     */
    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    /**
     * 小数
     * @param orginal
     * @return
     */
    public static boolean isDecimal(String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    /**
     * 是否是实数
     *
     * @param orginal
     * @return
     */
    public static boolean isRealNumber(String orginal) {
        return isWholeNumber(orginal) || isDecimal(orginal);
    }

}