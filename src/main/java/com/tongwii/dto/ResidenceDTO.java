package com.tongwii.dto;

import lombok.Data;

/**
 * 社区Dto
 *
 * @author Zeral
 * @date 2017-11-24
 */
@Data
public class ResidenceDTO {
    private String id;
    // 社区名称
    private String name;
    // 负责人
    private UserDTO chargeUser;
    // 楼盘总数
    private Integer floorCount;
    // 区域
    private String region;
    // 区域code
    private String regionCode;
    // 详细地址
    private String address;
}
