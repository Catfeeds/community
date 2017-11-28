package com.tongwii.dto;


import com.tongwii.domain.MessageType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the Message entity.
 */
@Data
public class MessageAdminDTO implements Serializable {

    private String id;

    private String title;

    private String content;

    private Date createTime;

    private Integer processState;

    private Date repairStartTime;

    private Date repairEndTime;

    private MessageType messageType;

    private String createUser;

    private String fileId;

    private ResidenceDTO residence;
}
