package com.tongwii.dto;

import lombok.Data;

import java.util.Set;

/**
 * 推送消息 Dto
 *
 * @author Zeral
 * @date 2017-11-22
 */
@Data
public class PushMessageDto {
    // 消息标题
    private String title;
    // 消息内容
    private String message;
    // 消息类型Code
    private String messageTypeCode;
    // 消息推送的用户id
    private Set<String> usersId;
    // 消息推送的设备id
    private Set<String> devicesId;
}
