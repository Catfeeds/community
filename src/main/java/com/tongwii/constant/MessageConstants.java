package com.tongwii.constant;

/**
 * Created by admin on 2017/7/13.
 */
public final class MessageConstants {
    /**定义消息类型Code**/
    public final static String PUSH_MESSAGE = "PUSH_MESSAGE";//推送消息
    public final static String NEW_MESSAGE = "NEW_MESSAGE";//新闻消息
    public final static String NOTIFY_MESSAGE = "NOTIFY_MESSAGE";//通知消息
    public final static String VOTE_MESSAGE = "VOTE_MESSAGE";//投票消息
    public final static String COMPLAINT_MESSAGE = "COMPLAINT_MESSAGE";//投诉消息
    public final static String HURRY_MESSAGE = "HURRY_MESSAGE";//紧急消息
    public final static String NODE_MESSAGE = "NODE_MESSAGE";//提示消息
    public final static String DYNAMIC_MESSAGE = "DYNAMIC_MESSAGE";//动态消息
    public final static String ONLINE_MESSAGE = "ONLINE_MESSAGE";//在线服务消息
    public final static String REPLAY_MESSAGE = "REPLAY_MESSAGE";//回复消息

    /**定义消息处理进度*/
    public final static Integer UN_PROCESS = 0;//未处理
    public final static Integer PROCESSED = 1;//已处理

    /**定义动态消息的操作类型*/
    public final static Integer IS_LIKE = 1; // 点赞
    public final static Integer COMMENT = 0; // 评论

    private MessageConstants() {
    }

    public class PUSH_ALL_TOPIC {
    }
}
