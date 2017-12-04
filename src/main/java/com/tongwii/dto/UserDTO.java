package com.tongwii.dto;

import lombok.Data;

import java.io.Serializable;
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
public class UserDTO implements Serializable {
    private String id;
    private String account;
    private String nickName;
    private Date birthday;
    private String signature;
    private int sex;
    private String name;
    private String idCard;
    private String phone;
    private String langKey;
    private String avatarFileSrc;
    private String clientId;
    private Date addTime;
    private boolean activated;
    private Set<String> devices;
    private List<RoomDTO> rooms;
    private Set<String> roles;
}
