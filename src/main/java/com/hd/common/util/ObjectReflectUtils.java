package com.hd.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 对象反射工具类
 * 一位不愿意透露姓名的热心观众
 *
 * @author cbl
 */
public class ObjectReflectUtils {

    /**
     * log对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ObjectReflectUtils.class);

    private static final String REGEX_YYYY_MM_DD = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";//yyyy-MM-dd
    private static final String REGEX_HH_MM_SS = "\\s([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
    private static final String REGEX_YYYY_MM_MM_HH_MM_SS = REGEX_YYYY_MM_DD + REGEX_HH_MM_SS;//yyyy-MM-dd HH:mm:ss
    private static final String REGEX_GMT = "(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\s(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s(0?[1-9]|[12][0-9]|3[01])" + REGEX_HH_MM_SS + "\\sCST\\s((19|20)[0-9]{2})";//Wed Sep 16 19:02:36 CST 2012
    private static final String REGEX_UTC = REGEX_YYYY_MM_DD + "T" + "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]" + "." + "\\d+" + "Z";//2019-09-09T16:00:00.000Z
    private static final String REGEX_BROWSER = REGEX_YYYY_MM_DD + "T" + "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]" + "." + "\\d+" + "\\+" + "\\d{4}";//2019-09-16T16:00:00.000+0000

    private static SimpleDateFormat sdf = new SimpleDateFormat();
    private static Pattern p_yyyyMMdd = Pattern.compile(REGEX_YYYY_MM_DD);
    private static Pattern p_yyyyMMddHHmmss = Pattern.compile(REGEX_YYYY_MM_MM_HH_MM_SS);
    private static Pattern p_gmt = Pattern.compile(REGEX_GMT);
    private static Pattern p_utc = Pattern.compile(REGEX_UTC);
    private static Pattern p_browser = Pattern.compile(REGEX_BROWSER);

    //private static final String SUPER_REFLECT_FIELD = "createDate,createUserId,updateUserId,updateDate,applyUserId,delegateTaskKey,isCanRecall,verifyState,approveTime,approveUserId,approveUserName,submitNode,isDelFlg,currentState,delegateTaskId";

    public static Object convertObj(Object clz1, Class<?> type) {
        Object result = null;
        try {
            result = type.newInstance();
            Method[] methods = clz1.getClass().getMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    String methodName = method.getName();
                    String fieldName = methodName.substring(methodName.indexOf("get") + 3);
                    fieldName = fieldName.toLowerCase().charAt(0) + fieldName.substring(1);
                    Object value = method.invoke(clz1, (Object[]) null); // 执行方法
                    if (value != null) {
                        Class<?> clazz = type;
                        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                            try {
                                Field filed = clazz.getDeclaredField(fieldName);
                                String fieldType = filed.getType().getSimpleName();
                                if ("String".equals(fieldType)) {
                                    if (filed.getAnnotation(DateTimeFormat.class) != null && value instanceof Date) {
                                        value = DateToolUtils.convertDateFormat((Date) value, DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO);
                                    } else {
                                        value = value.toString();
                                    }
                                } else if ("Date".equals(fieldType)) {
                                    value = parseDate(value.toString());
                                } else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
                                    value = Integer.parseInt(value.toString());
                                } else if ("Long".equalsIgnoreCase(fieldType)) {
                                    value = Long.parseLong(value.toString());
                                } else if ("Double".equalsIgnoreCase(fieldType)) {
                                    value = Double.parseDouble(value.toString());
                                } else if ("Boolean".equalsIgnoreCase(fieldType)) {
                                    value = Boolean.parseBoolean(value.toString());
                                } else {
                                    logger.error("not supper type" + fieldType);
                                }
                                filed.setAccessible(true);
                                filed.set(result, value);
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("对象转换异常");
            e.printStackTrace();
        }
        return result;
    }

    private static boolean isContainFiled(Class<?> clz, String filedName) {
        boolean b = false;
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(filedName)) {
                b = true;
                break;
            }
        }
        return b;
    }

    private static void invokeSuper(Object beanObj, Object voObj, String fieldName, Object value) throws Exception {
        Field filed = beanObj.getClass().getDeclaredField(fieldName);//Bean的属性
        String fieldType = filed.getType().getSimpleName();//Bean属性类型
        String fieldSetName = parSetName(filed.getName());//set方法名
        Method fieldSetMet = voObj.getClass().getMethod(fieldSetName, String.class);
        invoke(fieldType, fieldSetMet, voObj, value);
    }

/*    private static boolean isContainSuperFiled(String filedName) {
        return SUPER_REFLECT_FIELD.contains(filedName);
    }*/

    private static void invoke(String fieldType, Method set, Object o, Object value) throws Exception {
        if ("String".equals(fieldType)) {
            set.invoke(o, StringToolUtils.convertNullObjectToString(value));
        } else if ("Date".equals(fieldType)) {
            set.invoke(o, DateToolUtils.convertDateFormat((Date) value, DateToolUtils.YYYY_MM_DD_HH_MM_SS_ISO));
        } else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
            set.invoke(o, Integer.toString((int) value));
        } else if ("Long".equalsIgnoreCase(fieldType)) {
            set.invoke(o, Long.toString((long) value));
        } else if ("Double".equalsIgnoreCase(fieldType)) {
            set.invoke(o, Double.toString((Double) value));
        } else if ("Boolean".equalsIgnoreCase(fieldType)) {
            set.invoke(o, Boolean.toString((Boolean) value));
        } else {
            logger.error("not supper type" + fieldType);
        }
    }


    /**
     * 格式化string为Date
     * <p>
     * yyyy-MM-dd	                1969-12-31
     * yyyy-MM-dd	                1970-01-01
     * yyyy-MM-dd HH:mm	            1969-12-31 16:00
     * yyyy-MM-dd HH:mm	            1970-01-01 00:00
     * yyyy-MM-dd HH:mmZ	        1969-12-31 16:00-0800
     * yyyy-MM-dd HH:mmZ	        1970-01-01 00:00+0000
     * yyyy-MM-dd HH:mm:ss.SSSZ	    1969-12-31 16:00:00.000-0800
     * yyyy-MM-dd HH:mm:ss.SSSZ	    1970-01-01 00:00:00.000+0000
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ	1969-12-31T16:00:00.000-0800
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ	1970-01-01T00:00:00.000+0000
     *
     * @param datestr 日期
     * @return date
     */
    private static Date parseDate(String datestr) {
        if (null == datestr || "".equals(datestr)) {
            return null;
        }
        try {
            if (p_yyyyMMdd.matcher(datestr).matches()) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            } else if (p_yyyyMMddHHmmss.matcher(datestr).matches()) {
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else if (p_gmt.matcher(datestr).matches()) {
                sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US);
            } else if (p_utc.matcher(datestr).matches()) {
                sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            } else if (p_browser.matcher(datestr).matches()) {
                sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            }
            return sdf.parse(datestr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 拼接set方法
     *
     * @param fieldName 属性名
     * @return String
     */
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
