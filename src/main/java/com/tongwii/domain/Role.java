package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Setter
@Getter
@Table(name = "role", schema = "cloud_community", catalog = "")
public class Role implements Serializable {
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

/*    @OneToMany(mappedBy = "roleByRoleId")
    private Collection<GroupRole> groupRolesById;
    @OneToMany(mappedBy = "roleByRoleId")
    private Collection<UserRole> userRolesById;*/
}
