package com.tongwii.constant;

/**
 * Created by admin on 2017/7/13.
 */
public final class MessageConstants {
    /**定义消息类型**/
    public final static String PUSH_MESSAGE = "1";//推送消息
    public final static String NEW_MESSAGE = "2";//新闻消息
    public final static String NOTIFY_MESSAGE = "3";//通知消息
    public final static String VOTE_MESSAGE = "4";//投票消息
    public final static String COMPLAINT_MESSAGE = "5";//投诉消息
    public final static String HURRY_MESSAGE = "6";//紧急消息
    public final static String NODE_MESSAGE = "7";//提示消息
    public final static String DYNAMIC_MESSAGE = "8";//动态消息
    public final static String ONLINE_MESSAGE = "9";//在线服务消息
    public final static String REPLAY_MESSAGE = "10";//回复消息
    /**定义消息处理进度*/
    public final static Integer UNPROCESS = 0;//未处理
    public final static Integer PROCESSED = 1;//已处理

    /**定义动态消息的操作类型*/
    public final static Integer IS_LIKE = 1; // 点赞
    public final static Integer COMMENT = 0; // 评论

    public static final String PUSH_ALL_TOPIC = "All";
    public static final String PUSH_SELECTED_TOPIC = "selectUsers";
    private MessageConstants() {
    }
}
