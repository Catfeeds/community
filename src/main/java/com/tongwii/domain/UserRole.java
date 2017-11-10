package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户角色关系实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Setter
@Getter
@Table(name = "user_role", schema = "cloud_community", catalog = "")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "user_id")
    private String userId;

    @Basic
    @Column(name = "role_id")
    private String roleId;

    @Basic
    @Column(name = "des")
    private String des;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User userByUserId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Role roleByRoleId;
}
