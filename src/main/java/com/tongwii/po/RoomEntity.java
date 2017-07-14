package com.tongwii.po;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
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
    private String ownerId;
    private String unitId;
    private UserEntity userByOwnerId;
    private FloorEntity floorByUnitId;
    private Collection<UserRoomEntity> userRoomsById;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false, length = 32)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomEntity that = (RoomEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (roomCode != null ? !roomCode.equals(that.roomCode) : that.roomCode != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (huXing != null ? !huXing.equals(that.huXing) : that.huXing != null) return false;
        if (ownerId != null ? !ownerId.equals(that.ownerId) : that.ownerId != null) return false;
        if (unitId != null ? !unitId.equals(that.unitId) : that.unitId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (roomCode != null ? roomCode.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (huXing != null ? huXing.hashCode() : 0);
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (unitId != null ? unitId.hashCode() : 0);
        return result;
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
