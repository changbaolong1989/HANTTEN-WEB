package com.hd.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author JLF
 */
public final class DateToolUtils {

    /** 私有构造函数 */
    private DateToolUtils() {
    }
    /** 日期格式：yyyy-MM-dd HH:mm:ss */
    public static final String YYYY_MM_DD_HH_MM_SS_ISO = "yyyy-MM-dd HH:mm:ss";
    /** 日期格式：yyyy-MM-dd HH:mm:ss */
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /** 日期格式：yyyy-MM-dd */
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    /** 日期格式：yyyy-MM */
    public static final String yyyy_MM = "yyyy-MM";
    /** 日期格式：yyyy */
    public static final String yyyy = "yyyy";
    /** 日期格式：yyyy年MM月 */
    public static final String YYYY_MM_CHINESE = "yyyy年MM月";
    /** 日期格式：yyyy年MM月dd日 */
    public static final String YYYY_MM_DD_CHINESE = "yyyy年MM月dd日";
    /** 日期格式：yyyy.MM.dd */
    public static final String YYYYMMDD_SPOT = "yyyy.MM.dd";

    /** 页面传来的时间区间，开始时间和结束时间通过-连接 */
    public static final String DATE_SEPARATOR = " ~ ";

    /** 8小时毫秒数（UTC 时间 、北京时间 偏移量） */
    public static final long CCT_UTC_TIMEMILLIS_OFFSET = 1000 * 60 * 60 * 8;

    /** 7天毫秒数 */
    public static final long SENVEN_DAY_BEFORE = 1000 * 60 * 60 * 24 * 7;



    /**
     * 日期格式化
     *
     * @param convertObject
     *            格式变换Date对象
     * @param formatStr
     *            变换格式
     * @return String
     *            转换后的日期格式
     */
    public static String convertDateFormat(Date convertObject, String formatStr) {
        try {
            SimpleDateFormat simpleDateFormatObject = new SimpleDateFormat(formatStr);
            return simpleDateFormatObject.format(new Date(convertObject.getTime()));
        } catch (Exception e) {
            return "";
        }

    }


    /**
     * 依据 参数日期，得到参数日期对应的时间戳（参数日期为空，得到的时间戳则为0）
     *
     * @param paramDate 参数日期
     * @return Long型时间戳
     */
    public static Long convertDateToTimeMillisLong(Date paramDate){
        // 结果时间戳 （默认值为0）
        Long resultTimeMillisLong = NumberToolUtils.DEFAULT_LONG_ZERO;

        // 如果参数日期不为空
        if (null != paramDate){
            // 为结果时间戳赋值
            resultTimeMillisLong = paramDate.getTime();
        }

        return resultTimeMillisLong;
    }

    /**
     * 依据 参数日期，得到参数日期对应的时间戳字符串（参数日期为空，得到的时间戳则为0）
     *
     * @param paramDate 参数日期
     * @return String型时间戳
     */
    public static String convertDateToTimeMillisStr(Date paramDate){
        // 结果时间戳 （默认值为0）
        String resultTimeMillisStr = StringToolUtils.STRING_ZERO;

        // 如果参数日期不为空
        if (null != paramDate){
            // 为结果时间戳赋值
            resultTimeMillisStr = StringToolUtils.convertNullObjectToString(paramDate.getTime());
        }

        return resultTimeMillisStr;
    }

    /**
     * 依据 时间戳字符串 转化得到 日期类型
     *
     * @param paramTimeMillisStr 参数时间戳字符串
     * @return Date
     */
    public static Date convertTimeMillisStrToDate(String paramTimeMillisStr){

        // 结果日期
        Date resultDate = null;

        // 如果参数日期不为空
        if (!StringToolUtils.isNull(paramTimeMillisStr)){

            // 时间戳字符串 -> Long
            Long tmpTimeMillisLong = NumberToolUtils.convertNullToLong(paramTimeMillisStr);

            // 为结果时间戳赋值
            resultDate = new Date(tmpTimeMillisLong);
        }

        return resultDate;
    }

    /**
     * String格式转换成Date
     *
     * @param convertStr
     *            String时间对象
     * @param formatStr
     *            变换格式
     * @return
     */
    public static Date convertDateFormat(String convertStr, String formatStr) {

        SimpleDateFormat sdf = null;
        try {
            sdf = new SimpleDateFormat(formatStr);
            return sdf.parse(convertStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 依据 时间戳 转换为日期
     *
     * @param paramTimeMillis 时间戳
     * @return Date 日期
     */
    public static Date convertTimeMillisLongToDate(Long paramTimeMillis){
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_ISO);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(paramTimeMillis);
        return cal.getTime();
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {

        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

}