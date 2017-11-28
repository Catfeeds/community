package com.tongwii.dto;

import com.tongwii.domain.MessageType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息Dto
 *
 * @author Zeral
 * @date 2017-09-25
 */
@Data
public class MessageDTO implements Serializable {

    private String id;
    private String title;
    private String content;
    private Date createTime;
    private String createUser;
    private MessageType messageType;
    private String messageTypeCode;
}
