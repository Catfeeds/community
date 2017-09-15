package com.tongwii.constant;

/**
 * 用户常量类
 *
 * Author Zeral
 * Date 2017-07-11
 */
public interface UserConstants {
	/**获取用户返回状态**/
	Integer USER_ENABLE = 1;				// 正常使用
	Integer USER_DISABLE = -1;		// 不可用

	/**性别状态*/
	Integer UNKNOWN_SEX = 0;
	Integer USER_SEX_MALE = 1;
	Integer USER_SEX_FAMALE = 2;
}
