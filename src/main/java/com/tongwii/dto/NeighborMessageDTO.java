package com.tongwii.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tongwii.domain.MessageComment;
import com.tongwii.util.CustomDateSerializer;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

/**
 * Created by admin on 2017/10/24.
 */
@Data
public class NeighborMessageDTO {
    private String id;
    private String title;
    private String content;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createTime;
    private String createUser;
    private String createUserAvatar;
    private String messageTypeId;
    private Integer likeNum;
    private Integer commentNum;
    private Collection<MessageComment> messageCommentEntities;
}
