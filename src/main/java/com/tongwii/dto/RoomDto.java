package com.tongwii.dto;

import lombok.Data;

/**
 * 房间信息Vo
 *
 * @author Zeral
 * @date 2017 -07-27
 */
@Data
public class RoomDto {
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 房间编号
     */
    private String roomCode;
    /**
     * 社区id
     */
    private String residenceId;
    /**
     * 社区名称
     */
    private String residenceName;
    /**
     * 单元编号
     */
    private String unitCode;
    /**
     * 楼宇房间
     */
    private String roomFloor;
    /**
     * 楼宇Id
     */
    private String floorId;
    /**
     * 业主姓名
     */
    private String chargeName;
    /**
     * 业主电话
     */
    private String chargePhone;
    /**
     * 用户和房间的关系，具体参考 UserRoom 常量定义
     *
     */
    private Byte type;
}
