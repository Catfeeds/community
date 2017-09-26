package com.tongwii.domain;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 分区实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@EqualsAndHashCode
@Table(name = "area", schema = "cloud_community", catalog = "")
public class AreaEntity implements Serializable {
    private String id;
    private String name;
    private Date buildDate;
    private String chargeId;
    private String residenceId;
    private UserEntity userByChargeId;
    private Collection<FloorEntity> floorsById;
    private ResidenceEntity residenceByResidenceId;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "build_date")
    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    @Basic
    @Column(name = "charge_id")
    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    @Basic
    @Column(name = "residence_id")
    public String getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(String residenceId) {
        this.residenceId = residenceId;
    }

    @ManyToOne
    @JoinColumn(name = "charge_id", referencedColumnName = "id", insertable = false, updatable = false)
    public UserEntity getUserByChargeId() {
        return userByChargeId;
    }

    public void setUserByChargeId(UserEntity userByChargeId) {
        this.userByChargeId = userByChargeId;
    }

    @OneToMany(mappedBy = "areaByAreaId")
    public Collection<FloorEntity> getFloorsById() {
        return floorsById;
    }

    public void setFloorsById(Collection<FloorEntity> floorsById) {
        this.floorsById = floorsById;
    }

    @ManyToOne
    @JoinColumn(name = "residence_id", referencedColumnName = "id", insertable = false, updatable = false)
    public ResidenceEntity getResidenceByResidenceId() {
        return residenceByResidenceId;
    }

    public void setResidenceByResidenceId(ResidenceEntity residenceByResidenceId) {
        this.residenceByResidenceId = residenceByResidenceId;
    }
}
