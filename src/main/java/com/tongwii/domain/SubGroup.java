package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 群组实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Setter
@Getter
@Table(name = "subgroup", schema = "cloud_community")
public class SubGroup implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "parent_id")
    private String parentId;

    @Basic
    @Column(name = "des")
    private String des;

    @OneToMany(mappedBy = "subgroupByGroupId")
    private Collection<GroupRole> groupRolesById;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SubGroup subgroupByParentId;

    @OneToMany(mappedBy = "subgroupByParentId")
    private Collection<SubGroup> subgroupsById;

    @OneToMany(mappedBy = "subgroupByGroupId")
    private Collection<UserGroup> userGroupsById;
}
