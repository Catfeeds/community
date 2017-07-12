package com.tongwii.constant;

/**
 * 返回结果常量类
 * Author: Zeral
 * Date: 2017/7/11
 */
public interface ResultConstants {
    /**
     * 处理结果状态：正常
     */
    public static final int SUCCESS = 1;
    /**
     * 处理结果状态：异常
     */
    public static final int ERROR = 0;

    /**
     * 处理结果状态：请求不合法
     */
    public static final int ILLEGAL = -1;

    /**
     * 处理结果状态：SQL处理异常
     */
    public static final int SQL_EXCEPTION = 300;
}
