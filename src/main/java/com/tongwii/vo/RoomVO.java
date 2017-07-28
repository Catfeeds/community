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
     * 业主姓名
     */
    private String chargeName;
    /**
     * 业主电话
     */
    private String chargePhone;

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
