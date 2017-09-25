package com.tongwii.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tongwii.util.CustomDateSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户VO
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Data
public class UserDto {
    private String id;
    private String account;
    private String nickName;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date birthday;
    private String signature;
    private Integer sex;
    private String avatarFileSrc;
    private String name;
    private String idCard;
    private String phone;
    private String clientId;
    private Date addTime;
    private Integer state;
    private List<RoomDto> rooms;
    private List<String> roles;
}
