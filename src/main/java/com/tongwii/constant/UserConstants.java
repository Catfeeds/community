package com.tongwii.constant;

/**
 * 用户常量类
 *
 * Author Zeral
 * Date 2017-07-11
 */
public final class UserConstants {
	/**获取用户返回状态**/
    public static final Integer USER_ENABLE = 1;		// 正常使用
    public static final Integer USER_DISABLE = -1;		// 不可用

	/**性别状态*/
    public static final Integer UNKNOWN_SEX = 0;
    public static final Integer USER_SEX_MALE = 1;
    public static final Integer USER_SEX_FAMALE = 2;

    /**用户类型**/
    public static final Integer HUZHU = 1;
    public static final Integer ZUKE = 2;
    public static final Integer MEMBER = 3;

    /**密码长度约束**/
    public static final Integer PASSWORD_MIN_LENGTH = 6;
    public static final Integer PASSWORD_MAX_LENGTH = 30;
}
