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
@Table(name = "subgroup", schema = "cloud_community", catalog = "")
public class SubGroupEntity implements Serializable {
    private String id;
    private String name;
    private String parentId;
    private String desc;
    private Collection<GroupRoleEntity> groupRolesById;
    private SubGroupEntity subgroupByParentId;
    private Collection<SubGroupEntity> subgroupsById;
    private Collection<UserGroupEntity> userGroupsById;

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
    @Column(name = "parent_id")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubGroupEntity that = (SubGroupEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "subgroupByGroupId")
    public Collection<GroupRoleEntity> getGroupRolesById() {
        return groupRolesById;
    }

    public void setGroupRolesById(Collection<GroupRoleEntity> groupRolesById) {
        this.groupRolesById = groupRolesById;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    public SubGroupEntity getSubgroupByParentId() {
        return subgroupByParentId;
    }

    public void setSubgroupByParentId(SubGroupEntity subgroupByParentId) {
        this.subgroupByParentId = subgroupByParentId;
    }

    @OneToMany(mappedBy = "subgroupByParentId")
    public Collection<SubGroupEntity> getSubgroupsById() {
        return subgroupsById;
    }

    public void setSubgroupsById(Collection<SubGroupEntity> subgroupsById) {
        this.subgroupsById = subgroupsById;
    }

    @OneToMany(mappedBy = "subgroupByGroupId")
    public Collection<UserGroupEntity> getUserGroupsById() {
        return userGroupsById;
    }

    public void setUserGroupsById(Collection<UserGroupEntity> userGroupsById) {
        this.userGroupsById = userGroupsById;
    }
}
