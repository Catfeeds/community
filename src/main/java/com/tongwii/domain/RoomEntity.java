package com.tongwii.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 房间实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Table(name = "room", schema = "cloud_community", catalog = "")
public class RoomEntity implements Serializable {
    private String id;
    private String roomCode;
    private Double area;
    private String huXing;
    private String clientCode;
    private String ownerId;
    private String unitId;
//    @Ignore
    private UserEntity userByOwnerId;
//    @Ignore
    private FloorEntity floorByUnitId;
//    @Ignore
    private Collection<UserRoomEntity> userRoomsById;

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "room_code")
    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    @Basic
    @Column(name = "area")
    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    @Basic
    @Column(name = "hu_xing")
    public String getHuXing() {
        return huXing;
    }

    public void setHuXing(String huXing) {
        this.huXing = huXing;
    }

    @Basic
    @Column(name = "client_code")
    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    @Basic
    @Column(name = "owner_id")
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Basic
    @Column(name = "unit_id")
    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable = false, updatable = false)
    public UserEntity getUserByOwnerId() {
        return userByOwnerId;
    }

    public void setUserByOwnerId(UserEntity userByOwnerId) {
        this.userByOwnerId = userByOwnerId;
    }

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    public FloorEntity getFloorByUnitId() {
        return floorByUnitId;
    }

    public void setFloorByUnitId(FloorEntity floorByUnitId) {
        this.floorByUnitId = floorByUnitId;
    }

    @OneToMany(mappedBy = "roomByRoomId")
    public Collection<UserRoomEntity> getUserRoomsById() {
        return userRoomsById;
    }

    public void setUserRoomsById(Collection<UserRoomEntity> userRoomsById) {
        this.userRoomsById = userRoomsById;
    }
}
