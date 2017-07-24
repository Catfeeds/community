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
	
//	public final static Integer USER_STATUS_ACTIVE = 1;//在校
	
//	public final static Integer USER_STATUS_LEAVE = 2;//离校

	public final static Integer USER_LIVE = 3;//用戶处于居住状态

	public final static Integer USER_NOT_LIVE = 4;//用户处于不居住状态

	public final static Integer USER_DEAD = -1;//用户注销状态

	/**获取用户返回状态**/
	public final static Integer USER_EXIST = 0;//用户存在
	public final static Integer USER_NOT_EXIST = -1;//用户不存在
	public final static Byte USER_STATUS_LEAVE = -1;//注销

	/**性别状态*/
	public final static Integer UNKNOWN_SEX = 0;
	public final static Integer USER_SEX_MALE = 1;
	public final static Integer USER_SEX_FAMALE = 2;
}
