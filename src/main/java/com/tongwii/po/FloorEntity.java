package com.tongwii.po;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FloorEntity that = (FloorEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parentCode != null ? !parentCode.equals(that.parentCode) : that.parentCode != null) return false;
        if (principalId != null ? !principalId.equals(that.principalId) : that.principalId != null) return false;
        if (areaId != null ? !areaId.equals(that.areaId) : that.areaId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentCode != null ? parentCode.hashCode() : 0);
        result = 31 * result + (principalId != null ? principalId.hashCode() : 0);
        result = 31 * result + (areaId != null ? areaId.hashCode() : 0);
        return result;
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
