package com.tongwii.dto;

import com.tongwii.domain.MessageType;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by admin on 2017/10/24.
 */
@Data
public class NeighborMessageDTO implements Serializable {
    private String id;
    private String title;
    private String content;
    private Date createTime;
    private String createUser;
    private String createUserAvatar;
    private MessageType messageType;
    private int likeNum;
    private int commentNum;
    private Collection<MessageCommentDTO> messageComments;
}
