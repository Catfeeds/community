package com.tongwii.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 日期工具类
 *
 */
public class DateUtil {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String CN_DATE_FORMAT = "yyyy年MM月dd日";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String SE_DATE_FORMAT="yyyy/MM/dd";

    /**
     * 按指定格式将字符串转换为日期
     *
     * @param dateStr
     *            日期串
     * @param pattern
     *            格式
     * @return 日期
     *             异常
     */
    public static Date str2Date(String dateStr, String pattern) {
        if (null == dateStr) {
            return null;
        }
        if (null == pattern) {
            pattern = DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date addDate(Date d, long day) {
        long time = d.getTime();
        day = day * 24 * 60 * 60 * 1000;
        time += day;
        return new Date(time);
    }


    /**
     * 将日期格式化为字符串
     *
     * @param date
     *            日期
     * @param pattern
     *            格式
     * @return 格式化后的日期串
     */
    public static String date2Str(Date date, String pattern) {
        if (null == date) {
            return null;
        }
        if (null == pattern) {
            pattern = DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        return format.format(date);
    }


    /**
     * 取得当前时间戳
     *
     * @return 当前时间戳
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    /**
     * 取得当前日期
     *
     * @return 当前日期
     */
    public static String thisDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(calendar
                .getTime());
    }


    /**
     * 取得当前时间
     *
     * @return 当前时间
     */
    public static String thisTime() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(calendar
                .getTime());
    }

    /**
     * 取得当前完整日期时间
     *
     * @return 当前完整日期时间
     */
    public static String thisDateTime() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).format(calendar
                .getTime());
    }


    /**
     * 日期差天数(按照时间比较,如果不足一天会自动补足)
     *
     * @param date1
     *            开始日期
     * @param date2
     *            结束日期
     * @return 两日期差天数
     */
    public static int diff(Date date1, Date date2){
        long day = 24L * 60L * 60L * 1000L;
        String str1 = DateUtil.date2Str(date1, "yyyy-MM-dd");
        date1 = DateUtil.str2Date(str1, "yyyy-MM-dd");
        String str2 = DateUtil.date2Str(date2, "yyyy-MM-dd");
        date2 = DateUtil.str2Date(str2, "yyyy-MM-dd");
        return (int) (((date2.getTime() - date1.getTime()) / day));
        // return (int) Math.ceil((((date2.getTime() - date1.getTime()) / (24 *
        // 60 * 60 * 1000d))));
    }

    private static double getDifferMonths(Calendar c1, Calendar c2)
            throws Exception {
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int differM = month1 - month2 - 1;
        c2.set(Calendar.MONTH, month2 + differM);
        int differD = DateUtil.diff(c2.getTime(), c1.getTime());
        if (month1 == month2) {
            differM = 0;
        }
        return differM + differD / 30.0;
    }

    private static double getDifferYearMonths(Calendar c1, Calendar c2,
            int years) throws Exception {
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int differM = month1 - month2 - 1;
        c2.set(Calendar.YEAR, year2 + years);
        c2.set(Calendar.MONTH, month2 + differM);
        int differD = Math.abs(DateUtil.diff(c2.getTime(), c1.getTime()));
        return years * 12 + differM + differD / 30.0;
    }

    /**
     * 日期差天数(和当前时间比)
     *
     * @param date
     *            比较日期
     * @return 和当前日期差天数
     * @throws Exception
     */
    public static int diff(Date date) throws Exception {
        return diff(new Date(), date);
    }

    /**
     * 日期差毫秒数(和当前时间比)
     *
     * @param date
     *            比较日期
     * @return 和当前日期差天数
     * @throws Exception
     */
    public static long diffMilli(Date date) {
    	return diffMilli(new Date(), date);
    }


    /**
     * 日期差天毫秒数(按照两个时间比较)
     *
     * @param date1
     *            开始日期
     * @param date2
     *            结束日期
     * @return 两日期差毫秒数
     * @throws Exception
     */
    public static long diffMilli(Date date1, Date date2){
        return date1.getTime() - date2.getTime();
    }

    /**
     * 按固定格式比较两个日期
     *
     * @param date1
     *            日期
     * @param date2
     *            日期2
     * @param pattern
     *            格式 默认：yyyy-MM-dd
     * @return 比较结果
     */
    public static int compareDate(Date date1, Date date2, String pattern) {
        String d1 = date2Str(date1, pattern);
        String d2 = date2Str(date2, pattern);
        return d1.compareTo(d2);
    }


    /**
     * 判断是否是闰年
     *
     * @param date
     *            日期
     * @return boolean
     */
    public static boolean isLeapyear(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return gregorianCalendar.isLeapYear(gregorianCalendar
                .get(Calendar.YEAR));
    }
}
