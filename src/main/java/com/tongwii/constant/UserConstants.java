package com.tongwii.constant;

/**
 * 用户常量类
 *
 * Author Zeral
 * Date 2017-07-11
 */
public interface UserConstants {

	/**用户类型*/
	
	public final static Integer USERTYPE_STUDENT = 1;//学生
	
	public final static Integer USERTYPE_TEACHER = 2;//老师
	
	public final static Integer USERTYPE_PARENT = 3;//家长
	
	/**用户状态*/
	
	public final static Byte USER_STATUS_ACTIVE = 1;//正常使用
	
	public final static Byte USER_STATUS_LEAVE = -1;//注销

	/**性别状态*/
	public final static Integer UNKNOWN_SEX = 0;
	public final static Integer USER_SEX_MALE = 1;
	public final static Integer USER_SEX_FAMALE = 2;
}
