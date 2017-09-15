package com.tongwii.vo;

/**
 * 房间信息Vo
 *
 * @author Zeral
 * @date 2017 -07-27
 */
public class RoomVO {
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 房间编号
     */
    private String roomCode;
    /**
     * 房间单元信息
     */
    private String roomFloor;
    /**
     * 社区id
     */
    private String residenceId;
    /**
     * 社区名称
     */
    private String residenceName;
    /**
     * 分区id
     */
    private String areaId;
    /**
     * 分区名称
     */
    private String areaName;
    /**
     * 业主姓名
     */
    private String chargeName;
    /**
     * 业主电话
     */
    private String chargePhone;
    /**
     * 用户和房间的关系，具体参考 UserRoomEntity 常量定义
     *
     */
    private Byte type;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(String residenceId) {
        this.residenceId = residenceId;
    }

    public String getResidenceName() {
        return residenceName;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(String roomFloor) {
        this.roomFloor = roomFloor;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getChargePhone() {
        return chargePhone;
    }

    public void setChargePhone(String chargePhone) {
        this.chargePhone = chargePhone;
    }
}
