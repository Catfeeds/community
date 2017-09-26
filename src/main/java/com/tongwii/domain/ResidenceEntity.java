package com.tongwii.domain;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
@Table(name = "residence", schema = "cloud_community", catalog = "")
public class ResidenceEntity implements Serializable {
    private String id;
    private String name;
    private String code;
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
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
