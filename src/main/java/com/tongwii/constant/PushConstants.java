package com.tongwii.constant;

/**
 * Created by admin on 2017/7/13.
 */
public final class PushConstants {
    /**推送消息状态**/
    public final static Integer PUSH_SUCCESS = 1;//推送成功

    public final static Integer PUSH_ERROR = -1;//错误推送

    public final static Integer PUSH_FIALED = -1;// 推送失败

    /**推送消息主题**/
    public static final String PUSH_ALL_TOPIC = "All";
    public static final String PUSH_SELECTED_TOPIC = "selectUsers";

    private PushConstants() {
    }
}
