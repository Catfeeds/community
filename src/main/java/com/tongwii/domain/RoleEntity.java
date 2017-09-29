package com.tongwii.domain;

import lombok.Data;
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
@Data
@Table(name = "role", schema = "cloud_community", catalog = "")
public class RoleEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "des")
    private String des;
    @OneToMany(mappedBy = "roleByRoleId")
    private Collection<GroupRoleEntity> groupRolesById;
    @OneToMany(mappedBy = "roleByRoleId")
    private Collection<UserRoleEntity> userRolesById;
}
