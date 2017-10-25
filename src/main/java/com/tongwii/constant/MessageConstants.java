package com.tongwii.constant;

/**
 * Created by admin on 2017/7/13.
 */
public interface MessageConstants {
    /**定义消息类型**/
    Integer PUSH_MESSAGE = 1;//快递消息
    Integer NEW_MESSAGE = 2;//新闻消息
    Integer NOTIFY_MESSAGE = 3;//通知消息
    Integer VOTE_MESSAGE = 4;//投票消息
    Integer COMPLAINT_MESSAGE = 5;//投诉消息
    Integer HURRY_MESSAGE = 6;//紧急消息
    Integer NODE_MESSAGE = 7;//提示消息
    Integer DYNAMIC_MESSAGE = 8;//动态消息
    Integer ONLINE_MESSAGE = 9;//在线服务消息
    Integer REPLAY_MESSAGE = 10;//回复消息
    /**定义消息处理进度*/
    Integer UNPROCESS = 0;//未处理
    Integer PROCESSED = 1;//已处理

    /**定义动态消息的操作类型*/
    Integer ISLIKE = 1; // 点赞
    Integer COMMENT = 0; // 评论

}
