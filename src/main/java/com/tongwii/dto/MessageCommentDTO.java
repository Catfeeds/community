package com.tongwii.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the MessageComment entity.
 */
@Data
public class MessageCommentDTO implements Serializable {

    private String id;

    private Boolean isLike;

    private String comment;

    private Date commentDate;

    private Integer type;

    private String commentator;

    private String messageId;

}
