package com.tongwii.po;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Author: Zeral
 * Date: 2017/7/11
 */
@Entity
@Table(name = "floor", schema = "cloud_community", catalog = "")
public class FloorEntity implements Serializable {
    private String id;
    private String dongCode;
    private String name;
    private String unitCode;
    private String principalId;
    private String residenceId;
    private String areaId;
    private UserEntity userByPrincipalId;
    private ResidenceEntity residenceByResidenceId;
    private AreaEntity areaByAreaId;
    private Collection<RoomEntity> roomsById;

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
    @Column(name = "dong_code")
    public String getDongCode() {
        return dongCode;
    }

    public void setDongCode(String dongCode) {
        this.dongCode = dongCode;
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
    @Column(name = "unit_code")
    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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
    @Column(name = "residence_id")
    public String getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(String residenceId) {
        this.residenceId = residenceId;
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
        if (dongCode != null ? !dongCode.equals(that.dongCode) : that.dongCode != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (unitCode != null ? !unitCode.equals(that.unitCode) : that.unitCode != null) return false;
        if (principalId != null ? !principalId.equals(that.principalId) : that.principalId != null) return false;
        if (residenceId != null ? !residenceId.equals(that.residenceId) : that.residenceId != null) return false;
        if (areaId != null ? !areaId.equals(that.areaId) : that.areaId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dongCode != null ? dongCode.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (unitCode != null ? unitCode.hashCode() : 0);
        result = 31 * result + (principalId != null ? principalId.hashCode() : 0);
        result = 31 * result + (residenceId != null ? residenceId.hashCode() : 0);
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
    @JoinColumn(name = "residence_id", referencedColumnName = "id", insertable = false, updatable = false)
    public ResidenceEntity getResidenceByResidenceId() {
        return residenceByResidenceId;
    }

    public void setResidenceByResidenceId(ResidenceEntity residenceByResidenceId) {
        this.residenceByResidenceId = residenceByResidenceId;
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
