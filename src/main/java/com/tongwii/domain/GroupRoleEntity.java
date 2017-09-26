package com.tongwii.domain;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 分组-角色关系实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@EqualsAndHashCode
@Table(name = "group_role", schema = "cloud_community", catalog = "")
public class GroupRoleEntity implements Serializable {
    private String id;
    private String groupId;
    private String roleId;
    private String des;
    private SubGroupEntity subgroupByGroupId;
    private RoleEntity roleByRoleId;

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
    @Column(name = "group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "role_id")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "des")
    public String getDes() {
        return des;
    }

    public void setDes(String dec) {
        this.des = dec;
    }

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", insertable = false, updatable = false)
    public SubGroupEntity getSubgroupByGroupId() {
        return subgroupByGroupId;
    }

    public void setSubgroupByGroupId(SubGroupEntity subgroupByGroupId) {
        this.subgroupByGroupId = subgroupByGroupId;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    public RoleEntity getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(RoleEntity roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }
}
