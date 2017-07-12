package com.tongwii.po;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import org.hibernate.annotations.GenericGenerator;

/**
 * Author: Zeral
 * Date: 2017/7/11
 */
@Entity
@Table(name = "area", schema = "cloud_community", catalog = "")
public class AreaEntity implements Serializable {
    private String id;
    private String name;
    private Date buildDate;
    private String chargeId;
    private UserEntity userByChargeId;
    private Collection<FloorEntity> floorsById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaEntity that = (AreaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (buildDate != null ? !buildDate.equals(that.buildDate) : that.buildDate != null) return false;
        if (chargeId != null ? !chargeId.equals(that.chargeId) : that.chargeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (buildDate != null ? buildDate.hashCode() : 0);
        result = 31 * result + (chargeId != null ? chargeId.hashCode() : 0);
        return result;
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
}
