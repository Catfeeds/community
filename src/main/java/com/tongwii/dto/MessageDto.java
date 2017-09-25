package com.tongwii.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tongwii.util.CustomDateSerializer;
import lombok.Data;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-25
 */
@Data
public class MessageDto {

    private String id;
    private String title;
    private String content;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createTime;
    private String createUser;
}
