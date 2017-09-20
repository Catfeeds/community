package com.tongwii.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 社区实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Table(name = "residence", schema = "cloud_community", catalog = "")
public class ResidenceEntity implements Serializable {
    private String id;
    private String code;
    private String name;
    private String userId;
    private Integer floorCount;
    private String regionId;
    private String serverUrl;
    private Collection<AreaEntity> areaById;
    private Collection<MessageEntity> messageById;
    private UserEntity userByUserId;


    @Basic
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
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

    public void setCode(String code) {
        this.code = code;
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
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "floor_count")
    public Integer getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(Integer floorCount) {
        this.floorCount = floorCount;
    }

    @Basic
    @Column(name = "region_id")
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "server_url")
    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResidenceEntity that = (ResidenceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (floorCount != null ? !floorCount.equals(that.floorCount) : that.floorCount != null) return false;
        if (regionId != null ? !regionId.equals(that.regionId) : that.regionId != null) return false;
        if (serverUrl != null ? !serverUrl.equals(that.serverUrl) : that.serverUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (floorCount != null ? floorCount.hashCode() : 0);
        result = 31 * result + (regionId != null ? regionId.hashCode() : 0);
        result = 31 * result + (serverUrl != null ? serverUrl.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "residenceByResidenceId")
    public Collection<AreaEntity> getAreaById() {
        return areaById;
    }

    public void setAreaById(Collection<AreaEntity> floorsById) {
        this.areaById = floorsById;
    }

    @OneToMany(mappedBy = "residenceByResidenceId")
    public Collection<MessageEntity> getMessageById() {
        return messageById;
    }

    public void setMessageById(Collection<MessageEntity> messageById) {
        this.messageById = messageById;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }
}
