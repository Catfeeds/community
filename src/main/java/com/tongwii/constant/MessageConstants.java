package com.tongwii.constant;

/**
 * Created by admin on 2017/7/13.
 */
public interface MessageConstants {
    /**定义消息类型**/
    public final static Integer PUSH_MESSAGE = 1;//快递消息
    public final static Integer NEW_MESSAGE = 2;//新闻消息
    public final static Integer NOTIFY_MESSAGE = 3;//通知消息
    public final static Integer VOTE_MESSAGE = 4;//投票消息
    public final static Integer COMPLAINT_MESSAGE = 5;//投诉消息
    public final static Integer HURRY_MESSAGE = 6;//紧急消息
    public final static Integer NODE_MESSAGE = 7;//提示消息
    public final static Integer DYNAMIC_MESSAGE = 8;//动态消息
    public final static Integer ONLINE_MESSAGE = 9;//在线服务消息
    public final static Integer REPLAY_MESSAGE = 10;//回复消息
    /**定义消息处理进度*/
    public final static Integer UNPROCESS = 0;//未处理
    public final static Integer PROCESSED = 1;//已处理

}
