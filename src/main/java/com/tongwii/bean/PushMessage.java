package com.tongwii.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 消息对象
 *
 * @author Zeral
 * @date 2017-10-23
 */
@Getter
@Setter
public class PushMessage {
    // 消息标题
    private String title;
    // 消息内容
    private String message;
    // 消息推送的用户id
    private List devicesId;
}
