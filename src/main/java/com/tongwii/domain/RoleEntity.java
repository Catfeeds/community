package com.tongwii.domain;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 角色实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@EqualsAndHashCode
@Table(name = "role", schema = "cloud_community", catalog = "")
public class RoleEntity implements Serializable {
    private String id;
    private String name;
    private String code;
    private String des;
    private Collection<GroupRoleEntity> groupRolesById;
    private Collection<UserRoleEntity> userRolesById;

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
    @Column(name = "des")
    public String getDes() {
        return des;
    }

    public void setDes(String desc) {
        this.des = desc;
    }

    @OneToMany(mappedBy = "roleByRoleId")
    public Collection<GroupRoleEntity> getGroupRolesById() {
        return groupRolesById;
    }

    public void setGroupRolesById(Collection<GroupRoleEntity> groupRolesById) {
        this.groupRolesById = groupRolesById;
    }

    @OneToMany(mappedBy = "roleByRoleId")
    public Collection<UserRoleEntity> getUserRolesById() {
        return userRolesById;
    }

    public void setUserRolesById(Collection<UserRoleEntity> userRolesById) {
        this.userRolesById = userRolesById;
    }
}
