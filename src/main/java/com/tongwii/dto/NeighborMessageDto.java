package com.tongwii.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tongwii.domain.MessageCommentEntity;
import com.tongwii.util.CustomDateSerializer;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by admin on 2017/10/24.
 */
@Data
public class NeighborMessageDto {
    private String id;
    private String title;
    private String content;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createTime;
    private String createUser;
    private String messageTypeId;
    private Integer likeNum;
    private Integer commentNum;
    private Collection<MessageCommentEntity> messageCommentEntities;
}
