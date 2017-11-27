package com.tongwii.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tongwii.util.CustomDateSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户DTO
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Data
public class UserDTO {
    private String id;
    private String account;
    private String nickName;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date birthday;
    private String signature;
    private Integer sex;
    private String name;
    private String idCard;
    private String phone;
    private String langKey;
    private String avatarFileSrc;
    private String clientId;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date addTime;
    private boolean activated;
    private Set<String> devices;
    private List<RoomDTO> rooms;
    private Set<String> roles;
}
