package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 联系人实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Getter
@Setter
@Table(name = "user_contact", schema = "cloud_community")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserContact implements Serializable {
    public static final String UNKNOWN_NAME = "A_Z";

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "user_id")
    private String userId;

    @Basic
    @Column(name = "friend_id")
    private String friendId;

    @Basic
    @Column(name = "des")
    private String des;     // 联系人备注

    @ManyToOne
    @JoinColumn(name = "friend_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User userByFriendId;
}
