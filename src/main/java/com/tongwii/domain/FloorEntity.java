package com.tongwii.domain;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 楼层实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@EqualsAndHashCode
@Table(name = "floor", schema = "cloud_community", catalog = "")
public class FloorEntity implements Serializable {

    /**
     * The constant DONG. 常量-栋
     */
    public static final String DONG = "dong";
    /**
     * The constant UNIT. 常量-单元
     */
    public static final String UNIT = "unit";

    private String id;
    private String code;
    private String name;
    private String parentCode;
    private String principalId;
    private String areaId;
    private UserEntity userByPrincipalId;
    private AreaEntity areaByAreaId;
    private Collection<RoomEntity> roomsById;

    @Basic
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
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String dongCode) {
        this.code = dongCode;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "parent_code")
    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String unitCode) {
        this.parentCode = unitCode;
    }

    @Basic
    @Column(name = "principal_id")
    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }


    @Basic
    @Column(name = "area_id")
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @ManyToOne
    @JoinColumn(name = "principal_id", referencedColumnName = "id", insertable = false, updatable = false)
    public UserEntity getUserByPrincipalId() {
        return userByPrincipalId;
    }

    public void setUserByPrincipalId(UserEntity userByPrincipalId) {
        this.userByPrincipalId = userByPrincipalId;
    }

    @ManyToOne
    @JoinColumn(name = "area_id", referencedColumnName = "id", insertable = false, updatable = false)
    public AreaEntity getAreaByAreaId() {
        return areaByAreaId;
    }

    public void setAreaByAreaId(AreaEntity areaByAreaId) {
        this.areaByAreaId = areaByAreaId;
    }

    @OneToMany(mappedBy = "floorByUnitId")
    public Collection<RoomEntity> getRoomsById() {
        return roomsById;
    }

    public void setRoomsById(Collection<RoomEntity> roomsById) {
        this.roomsById = roomsById;
    }
}
